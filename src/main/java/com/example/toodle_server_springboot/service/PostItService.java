package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.repository.PostItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostItService {

    private final PostItRepository postItRepository;
}
