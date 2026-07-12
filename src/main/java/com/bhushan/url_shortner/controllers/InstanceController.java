package com.bhushan.url_shortner.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/debug")
public class InstanceController {
	@Value("${server.port}")
    private String port;

    @GetMapping("/instance")
    public String instance() {
        return "Served by instance on port " + port;
    }
}
