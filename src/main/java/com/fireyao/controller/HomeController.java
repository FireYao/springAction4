package com.fireyao.controller;

import com.fireyao.domain.Item;
import com.fireyao.bean.Knights;
import com.fireyao.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lly on 2017/8/31
 */
@Controller
public class HomeController {


    @Autowired
    private Knights knight;

    @Autowired(required = false)
    private ItemRepository itemRepository;

    @GetMapping("/home")
    public String home() {

        return "home";
    }

    @GetMapping("/hello")
    public String hello(ModelMap map) {
        map.addAttribute("hello", "hello world");

        return "hello";
    }

    @GetMapping("/param")
    @ResponseBody
    public Object params() {
        List<Item> all = itemRepository.findAll();
        return all;
    }


}
