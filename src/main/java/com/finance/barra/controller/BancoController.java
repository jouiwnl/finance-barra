package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.BancosDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Banco;
import com.finance.barra.model.QBanco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/bancos")
@RestController
public class BancoController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private BancosDto.RepresentationBuilder bancosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public BancosDto create(@RequestBody BancosDto banco) {
        Banco entity = bancosRB.from(banco);
        return bancosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public BancosDto update(@PathVariable("id") Long id, @RequestBody BancosDto banco) {
        Banco entity = bancosRB.from(banco);
        return bancosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Banco finded = this.repository.findOne(Banco.class, QBanco.banco.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<BancosDto> findAll() {
        return this.repository.findAll(Banco.class)
                .stream()
                .map(bancosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public BancosDto findOne(@PathVariable("id") Long id) {
        return bancosRB.of(this.repository.findOne(Banco.class, QBanco.banco.id.eq(id)));
    }

    @GetMapping("/find-select")
    @Transactional(readOnly = true)
    public List<BancosDto> findSelect() {
        return this.repository.findAll(Banco.class)
                .stream()
                .map(bancosRB::select)
                .collect(Collectors.toList());
    }

}
