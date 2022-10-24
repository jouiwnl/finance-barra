package com.finance.barra.config;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.enums.TipoCargo;
import com.finance.barra.model.Funcionario;
import com.finance.barra.model.QFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AdminConfig {

    @Autowired
    private BasicRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Value("${com.finance.barra.adminUsername}")
    private String adminUsername;

    @Value("${com.finance.barra.adminSecret}")
    private String adminSecret;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void verifyRootUser() {
        Funcionario funcionario =
                repository.findOne(Funcionario.class, QFuncionario.funcionario.usuario.eq(adminUsername));

        if (funcionario != null) {
            return;
        }

        Funcionario admin = Funcionario.builder()
                .nomeCompleto("Usuário administrador")
                .usuario(adminUsername)
                .senha(encoder.encode(adminSecret))
                .tipo(TipoCargo.ADM)
                .build();

        repository.save(admin);
    }

}
