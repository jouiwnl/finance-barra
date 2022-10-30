package com.finance.barra.controller;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.dto.UsuarioDto;
import com.finance.barra.model.QUsuario;
import com.finance.barra.model.Usuario;
import com.finance.barra.service.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/usuarios")
@CrossOrigin("*")
@RestController
public class UsuarioController {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDto create(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return UsuarioDto.of(this.repository.save(usuario));
    }

    @PutMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDto update(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Usuario finded = this.repository.findOne(Usuario.class, QUsuario.usuario.id.eq(id));
        usuario.setSenha(finded.getSenha());
        return UsuarioDto.of(this.repository.save(usuario));
    }

    @DeleteMapping("{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Usuario finded = this.repository.findOne(Usuario.class, QUsuario.usuario.id.eq(id));
        this.repository.remove(finded);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UsuarioDto> findAll() {
        return this.repository.findAll(Usuario.class)
                .stream()
                .map(UsuarioDto::of)
                .sorted(Comparator.comparing(UsuarioDto::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDto findOne(@PathVariable("id") Long id) {
        return UsuarioDto.of(this.repository.findOne(Usuario.class, QUsuario.usuario.id.eq(id)));
    }

    @GetMapping("/current")
    @Transactional(readOnly = true)
    public UsuarioDto findCurrent() {
        Usuario finded = this.repository.findOne(Usuario.class, QUsuario.usuario.user.eq(UserSS.authenticated()));
        return UsuarioDto.of(finded);
    }

}
