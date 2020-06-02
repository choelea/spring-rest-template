package tech.icoding.springrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.icoding.springrest.data.MsgData;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping
    public MsgData get() {
        return new MsgData("Just an demo");
    }
}
