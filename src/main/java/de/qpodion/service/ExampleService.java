package de.qpodion.service;

import de.qpodion.dto.ExampleDTO;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    public ExampleDTO getExample() {
        ExampleDTO example = new ExampleDTO();
        example.setExample("Example message");
        return example;
    }
}
