package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.FuncionarioDto;
import com.finance.barra.model.Funcionario;
import com.finance.barra.model.QFuncionario;
import com.finance.barra.service.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/funcionarios")
@CrossOrigin("*")
@RestController
public class FuncionarioController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public FuncionarioDto create(@RequestBody Funcionario funcionario) {
        funcionario.setSenha(encoder.encode(funcionario.getSenha()));
        return FuncionarioDto.of(this.repository.save(funcionario));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public FuncionarioDto update(@PathVariable("id") Long id, @RequestBody Funcionario funcionario) {
        Funcionario finded = this.repository.findOne(Funcionario.class, QFuncionario.funcionario.id.eq(id));
        funcionario.setSenha(finded.getSenha());
        return FuncionarioDto.of(this.repository.save(funcionario));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Funcionario finded = this.repository.findOne(Funcionario.class, QFuncionario.funcionario.id.eq(id));
        this.repository.remove(finded);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @Transactional(readOnly = true)
    public List<FuncionarioDto> findAll() {
        return this.repository.findAll(Funcionario.class)
                .stream()
                .map(FuncionarioDto::of)
                .sorted(Comparator.comparing(FuncionarioDto::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public FuncionarioDto findOne(@PathVariable("id") Long id) {
        return FuncionarioDto.of(this.repository.findOne(Funcionario.class, QFuncionario.funcionario.id.eq(id)));
    }

    @GetMapping("/current")
    @Transactional(readOnly = true)
    public FuncionarioDto findCurrent() {
        Funcionario finded = this.repository.findOne(Funcionario.class, QFuncionario.funcionario.usuario.eq(UserSS.authenticated()));
        return FuncionarioDto.of(finded);
    }

}
