package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.PessoasDto;
import com.finance.barra.enums.TipoPessoa;
import com.finance.barra.exceptions.ControllerException;
import com.finance.barra.model.Pessoa;
import com.finance.barra.model.QPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequestMapping("/pessoas")
@RestController
public class PessoaController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private PessoasDto.RepresentationBuilder pessoasRB;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public PessoasDto create(@RequestBody PessoasDto pessoa) {
        Pessoa entity = pessoasRB.from(pessoa);
        return pessoasRB.of(this.repository.save(entity));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public PessoasDto update(@PathVariable("id") Long id, @RequestBody PessoasDto pessoa) {
        Pessoa entity = pessoasRB.from(pessoa);
        return pessoasRB.of(this.repository.save(entity));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Pessoa finded = this.repository.findOne(Pessoa.class, QPessoa.pessoa.id.eq(id));

        try {
            this.repository.remove(finded);
        } catch (Exception e) {
            throw new ControllerException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<PessoasDto> findAll() {
        return this.repository.findAll(Pessoa.class)
                .stream()
                .map(pessoasRB::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public PessoasDto findOne(@PathVariable("id") Long id) {
        return pessoasRB.of(this.repository.findOne(Pessoa.class, QPessoa.pessoa.id.eq(id)));
    }

    @GetMapping("/findByTipo/{tipo}")
    @Transactional(readOnly = true)
    public List<PessoasDto> findOne(@PathVariable("tipo") String tipo) {
        return this.repository.findAll(Pessoa.class, QPessoa.pessoa.tipoPessoa.eq(TipoPessoa.valueOf(tipo)))
                .stream()
                .map(pessoasRB::of)
                .collect(Collectors.toList());
    }

}
