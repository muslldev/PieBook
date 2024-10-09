package com.mpbtwozeroone.piebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("omsu")
public class HelloWorldController {
    @GetMapping(value = "helloworld", produces = "text/html")
    public String helloWorld() {
        return
                "<!DOCTYPE html>" +
                        "<html>" +
                        "	<head><title>Hello world</title></head>" +
                        "	<body><h1>Hello world!</h1></body>" +
                        "</html>"
                ;
    }
}
