package com.changyou.fusion.config;

import com.changyou.fusion.handler.WebSocketHandler;
import com.changyou.fusion.handler.WebSocketHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketConfiguration
 * <p>
 * Created by zhanglei_js on 2018/3/16.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    private final WebSocketHandler handler;

    private final WebSocketHandshakeInterceptor interceptor;

    @Autowired
    public WebSocketConfiguration(WebSocketHandler handler, WebSocketHandshakeInterceptor interceptor) {
        this.handler = handler;
        this.interceptor = interceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/message").setAllowedOrigins("*").addInterceptors(interceptor);
    }
}
