package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.core.DynamicDto;
import com.finance.barra.dto.AnaliticosDto;
import com.finance.barra.model.Analitico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/analiticos")
@RestController
public class AnaliticoController {

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public Analitico create(@RequestBody Analitico analitico) {
        return this.repository.save(analitico);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AnaliticosDto> findAll() {
        return this.repository.findAll(Analitico.class)
                .stream()
                .map(AnaliticosDto::of)
                .collect(Collectors.toList());
    }
}
