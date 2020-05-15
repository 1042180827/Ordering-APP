package com.stone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ControlllerDemo {

    @RequestMapping("/print")
    public String print() {
        return "hello springBoot!";
    }
}
