package com.example.toodle_server_springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @GetMapping("/today")
    public ResponseEntity<?> showToday() {
        return ResponseEntity.ok("SUCCESS");
    }

}
