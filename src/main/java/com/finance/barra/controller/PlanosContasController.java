package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.PlanosContasDto;
import com.finance.barra.model.PlanosContas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/planos-contas")
@RestController
public class PlanosContasController {

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public PlanosContas create(@RequestBody PlanosContas planosContas) {
        return this.repository.save(planosContas);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<PlanosContasDto> findAll() {
        return this.repository.findAll(PlanosContas.class)
                .stream()
                .map(PlanosContasDto::of)
                .collect(Collectors.toList());
    }

}
