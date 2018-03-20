package com.changyou.fusion.handler;

import com.changyou.fusion.protocol.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketHandler
 * <p>
 * Created by zhanglei_js on 2018/3/16.
 */
@Service
public class WebSocketHandler extends AbstractWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // TODO Dispatcher 处理文本消息
    }


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        // TODO 反序列化
        // TODO Dispatcher 处理二进制消息

        Core.ChatRequest request = Core.ChatRequest.parseFrom(message.getPayload());
        logger.info("[{}] send message to [{}] : {}", request.getFrom(), request.getTo(), request.getContent());

        sessions.forEach((k, v) -> {
            try {
                Core.ChatResponse response = Core.ChatResponse.newBuilder().setFrom(request.getFrom()).setContent(request.getContent())
                        .setMine(k.equals(session.getId())).setIcon(request.getIcon()).build();
                v.sendMessage(new BinaryMessage(response.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 建立链接后 -> 增加至集合
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 出现异常时 -> 关闭链接，并从集合中删除
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 断开链接后 -> 从集合中删除
        sessions.remove(session.getId());
    }
}
