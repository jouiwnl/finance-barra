package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.CentrosCustosDto;
import com.finance.barra.model.CentrosCusto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/centros-custos")
@RestController
public class CentroCustoController {

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public CentrosCusto create(@RequestBody CentrosCusto centrosCusto) {
        return this.repository.save(centrosCusto);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<CentrosCustosDto> findAll() {
        return this.repository.findAll(CentrosCusto.class)
                .stream()
                .map(CentrosCustosDto::of)
                .collect(Collectors.toList());
    }
}
