package com.implementLife.BankMock.config.security;

import com.implementLife.BankMock.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TempInMemoryUsers implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(TempInMemoryUsers.class);
    private Map<String, ClientSec> accounts = new HashMap<>();

    @PostConstruct
    private void initAccounts() {
        LOG.info("Creating temp users:");
        create("123");
        create("321");
    }
    private void create(String pass) {
        Client client = new Client();
        ClientSec clientSec = new ClientSec(client);
        client.setId(UUID.randomUUID());
        client.setPass(pass);
        clientSec.setRoles(Collections.singleton(Role.USER));

        accounts.put(clientSec.getUsername(), clientSec);

        LOG.info("\t" + clientSec.getUsername() + " : " + clientSec.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accounts.get(username);
    }
}
