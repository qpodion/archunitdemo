package de.qpodion.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {
    public String getHelloWorld() {
        return "Hello World";
    }
}
