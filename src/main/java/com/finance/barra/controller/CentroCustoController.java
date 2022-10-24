package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.CentrosCustosDto;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.CentrosCusto;
import com.finance.barra.model.QCentrosCusto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private CentrosCustosDto.RepresentationBuilder centrosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public CentrosCustosDto create(@RequestBody CentrosCustosDto centrosCusto) {
        CentrosCusto entity = centrosRB.from(centrosCusto);
        return centrosRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public CentrosCustosDto update(@PathVariable("id") Long id, @RequestBody CentrosCustosDto centrosCusto) {
        CentrosCusto entity = centrosRB.from(centrosCusto);
        return centrosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        CentrosCusto finded = this.repository.findOne(CentrosCusto.class, QCentrosCusto.centrosCusto.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
             throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<CentrosCustosDto> findAll() {
        return this.repository.findAll(CentrosCusto.class)
                .stream()
                .map(centrosRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public CentrosCustosDto findOne(@PathVariable("id") Long id) {
        return centrosRB.of(this.repository.findOne(CentrosCusto.class, QCentrosCusto.centrosCusto.id.eq(id)));
    }

}
