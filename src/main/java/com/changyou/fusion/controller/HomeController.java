package com.changyou.fusion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Random;

/**
 * HomeController
 * <p>
 * Created by zhanglei_js on 2018/3/15.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "ws/{user}", method = RequestMethod.GET)
    public String index(@PathVariable String user, Model model) {
        model.addAttribute("user", user);
        // 随机数(1-39)
        int num = new Random().nextInt(38) + 1;
        model.addAttribute("head", "1000" + (num < 10 ? "0" : "") + num + "01_0.png");
        return "index";
    }
}
