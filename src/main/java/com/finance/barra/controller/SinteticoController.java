package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.SinteticosDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.QSintetico;
import com.finance.barra.model.Sintetico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private SinteticosDto.RepresentationBuilder sinteticosRB;


    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SinteticosDto create(@RequestBody SinteticosDto sintetico) {
        Sintetico entity = sinteticosRB.from(sintetico);
        return sinteticosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SinteticosDto update(@PathVariable("id") Long id, @RequestBody SinteticosDto sintetico) {
        Sintetico entity = sinteticosRB.from(sintetico);
        return sinteticosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Sintetico finded = this.repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<SinteticosDto> findAll() {
        return this.repository.findAll(Sintetico.class)
                .stream()
                .map(sinteticosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public SinteticosDto findOne(@PathVariable("id") Long id) {
        return sinteticosRB.of(this.repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(id)));
    }
}