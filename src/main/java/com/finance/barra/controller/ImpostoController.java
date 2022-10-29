package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.BancosDto;
import com.finance.barra.dto.ImpostosDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Banco;
import com.finance.barra.model.Imposto;
import com.finance.barra.model.QBanco;
import com.finance.barra.model.QImposto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/impostos")
@RestController
public class ImpostoController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private ImpostosDto.RepresentationBuilder impostosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ImpostosDto create(@RequestBody ImpostosDto imposto) {
        Imposto entity = impostosRB.from(imposto);
        return impostosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ImpostosDto update(@PathVariable("id") Long id, @RequestBody ImpostosDto imposto) {
        Imposto entity = impostosRB.from(imposto);
        return impostosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Imposto finded = this.repository.findOne(Imposto.class, QImposto.imposto.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<ImpostosDto> findAll() {
        return this.repository.findAll(Imposto.class)
                .stream()
                .map(impostosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ImpostosDto findOne(@PathVariable("id") Long id) {
        return impostosRB.of(this.repository.findOne(Imposto.class, QImposto.imposto.id.eq(id)));
    }

}
