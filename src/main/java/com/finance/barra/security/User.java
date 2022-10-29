package com.finance.barra.security;

import com.finance.barra.enums.TipoCargo;
import com.finance.barra.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public User(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUser();
        this.password = usuario.getSenha();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(usuario.getTipo().getDesc()));
    }

    public boolean hasRole(TipoCargo tipoCargo) {
        return this.getAuthorities().contains(new SimpleGrantedAuthority(tipoCargo.getDesc()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
