package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.AnaliticosDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Analitico;
import com.finance.barra.model.QAnalitico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AnaliticosDto.RepresentationBuilder analiticosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AnaliticosDto create(@RequestBody AnaliticosDto analitico) {
        Analitico entity = analiticosRB.from(analitico);
        return analiticosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AnaliticosDto update(@PathVariable("id") Long id, @RequestBody AnaliticosDto analitico) {
        Analitico entity = analiticosRB.from(analitico);
        return analiticosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Analitico finded = this.repository.findOne(Analitico.class, QAnalitico.analitico.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AnaliticosDto> findAll() {
        return this.repository.findAll(Analitico.class)
                .stream()
                .map(analiticosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public AnaliticosDto findOne(@PathVariable("id") Long id) {
        return analiticosRB.of(this.repository.findOne(Analitico.class, QAnalitico.analitico.id.eq(id)));
    }

    @GetMapping("/getBySintetico/{idSintetico}")
    @Transactional(readOnly = true)
    public List<AnaliticosDto> findBySintetico(@PathVariable("idSintetico") Long idSintetico) {
        return this.repository.findAll(Analitico.class, QAnalitico.analitico.sinteticos.any().id.eq(idSintetico))
                .stream()
                .map(analiticosRB::of)
                .collect(Collectors.toList());
    }

}
