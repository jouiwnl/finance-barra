package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.SinteticosDto;
import com.finance.barra.model.Sintetico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/sinteticos")
@RestController
public class SinteticoController {

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public Sintetico create(@RequestBody Sintetico sintetico) {
        return this.repository.save(sintetico);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<SinteticosDto> findAll() {
        return this.repository.findAll(Sintetico.class)
                .stream()
                .map(SinteticosDto::of)
                .collect(Collectors.toList());
    }
}