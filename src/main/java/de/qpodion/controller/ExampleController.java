package de.qpodion.controller;

import de.qpodion.dto.ExampleDTO;
import de.qpodion.service.ExampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping
    public ExampleDTO getExample() {
        return exampleService.getExample();
    }
}
