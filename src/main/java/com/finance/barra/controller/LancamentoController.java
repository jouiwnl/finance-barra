package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.LancamentoDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Lancamento;
import com.finance.barra.model.QLancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private LancamentoDto.RepresentationBuilder lancamentosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentoDto create(@RequestBody LancamentoDto lancamento) {
        Lancamento entity = lancamentosRB.from(lancamento);
        return lancamentosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentoDto update(@PathVariable("id") Long id, @RequestBody LancamentoDto lancamento) {
        Lancamento entity = lancamentosRB.from(lancamento);
        return lancamentosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Lancamento finded = this.repository.findOne(Lancamento.class, QLancamento.lancamento.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<LancamentoDto> findAll() {
        return this.repository.findAll(Lancamento.class)
                .stream()
                .map(lancamentosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public LancamentoDto findOne(@PathVariable("id") Long id) {
        return lancamentosRB.of(this.repository.findOne(Lancamento.class, QLancamento.lancamento.id.eq(id)));
    }

}
