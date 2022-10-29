package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.LancamentosEnglobadosDto;
import com.finance.barra.enums.StatusLancamentoEnglobado;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Lancamento;
import com.finance.barra.model.LancamentoEnglobado;
import com.finance.barra.model.QLancamento;
import com.finance.barra.model.QLancamentoEnglobado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/lancamentos-englobados")
@RestController
public class LancamentoEnglobadoController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private LancamentosEnglobadosDto.RepresentationBuilder lancamentoEnglobadosRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentosEnglobadosDto create(@RequestBody LancamentosEnglobadosDto lancamentoEnglobado) {
        LancamentoEnglobado entity = lancamentoEnglobadosRB.from(lancamentoEnglobado);
        return lancamentoEnglobadosRB.list(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentosEnglobadosDto update(@PathVariable("id") Long id, @RequestBody LancamentosEnglobadosDto lancamentoEnglobado) {
        LancamentoEnglobado entity = lancamentoEnglobadosRB.from(lancamentoEnglobado);
        return lancamentoEnglobadosRB.of(this.repository.save(entity));
    }

    @PostMapping("{id}/homologar")
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentosEnglobadosDto homologar(@PathVariable("id") Long id) {
        LancamentoEnglobado entity = repository.findOne(LancamentoEnglobado.class, QLancamentoEnglobado.lancamentoEnglobado.id.eq(id));
        entity.setStatus(StatusLancamentoEnglobado.HOMOLOGADO);

        return lancamentoEnglobadosRB.of(this.repository.save(entity));
    }

    @PostMapping("{id}/desfazer-homologacao")
    @Transactional(propagation = Propagation.REQUIRED)
    public LancamentosEnglobadosDto defazerHomologacao(@PathVariable("id") Long id) {
        LancamentoEnglobado entity = repository.findOne(LancamentoEnglobado.class, QLancamentoEnglobado.lancamentoEnglobado.id.eq(id));
        entity.setStatus(StatusLancamentoEnglobado.EM_PROCESSAMENTO);

        return lancamentoEnglobadosRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        LancamentoEnglobado finded = this.repository.findOne(LancamentoEnglobado.class, QLancamentoEnglobado.lancamentoEnglobado.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<LancamentosEnglobadosDto> findAll() {
        return this.repository.findAll(LancamentoEnglobado.class)
                .stream()
                .map(lancamentoEnglobadosRB::of)
                .sorted(Comparator.comparing(LancamentosEnglobadosDto::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public LancamentosEnglobadosDto findOne(@PathVariable("id") Long id) {
        return lancamentoEnglobadosRB.of(this.repository.findOne(LancamentoEnglobado.class, QLancamentoEnglobado.lancamentoEnglobado.id.eq(id)));
    }

}
