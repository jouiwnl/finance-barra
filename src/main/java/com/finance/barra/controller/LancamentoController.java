package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.LancamentoDto;
import com.finance.barra.model.Lancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/lancamentos")
@RestController
public class LancamentoController {

    @Autowired
    private BasicRepository repository;

    private LancamentoDto.RepresentationBuilder lancamentosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public Lancamento create(@RequestBody Lancamento lancamento) {
        return this.repository.save(lancamento);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<LancamentoDto> findAll() {
        return this.repository.findAll(Lancamento.class)
                .stream()
                .map(lancamentosRB::of)
                .collect(Collectors.toList());
    }

}
