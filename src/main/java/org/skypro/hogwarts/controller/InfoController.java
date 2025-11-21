package org.skypro.hogwarts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public Map<String, Integer> getServerPort() {
        Map<String, Integer> o = new HashMap<>(1);
        o.put("serverPort", this.serverPort);

        return o;
    }
}
