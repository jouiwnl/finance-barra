package com.finance.barra.service;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.QUsuario;
import com.finance.barra.model.Usuario;
import com.finance.barra.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private BasicRepository repository;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return Optional.ofNullable(repository.findOne(Usuario.class, QUsuario.usuario.user.eq(user)))
                .map(User::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
