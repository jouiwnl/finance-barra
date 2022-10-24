package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.PlanosContasDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.PlanosContas;
import com.finance.barra.model.QPlanosContas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private PlanosContasDto.RepresentationBuilder planosContasRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public PlanosContasDto create(@RequestBody PlanosContasDto planosContas) {
        PlanosContas entity = planosContasRB.from(planosContas);
        return planosContasRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public PlanosContasDto update(@PathVariable("id") Long id, @RequestBody PlanosContasDto planosContas) {
        PlanosContas entity = planosContasRB.from(planosContas);
        return planosContasRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        PlanosContas finded = this.repository.findOne(PlanosContas.class, QPlanosContas.planosContas.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<PlanosContasDto> findAll() {
        return this.repository.findAll(PlanosContas.class)
                .stream()
                .map(planosContasRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public PlanosContasDto findOne(@PathVariable("id") Long id) {
        return planosContasRB.of(this.repository.findOne(PlanosContas.class, QPlanosContas.planosContas.id.eq(id)));
    }

}
