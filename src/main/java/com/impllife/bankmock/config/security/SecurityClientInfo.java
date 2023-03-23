package com.impllife.bankmock.config.security;

import com.impllife.bankmock.data.entity.security.ClientSec;
import com.impllife.bankmock.services.interfaces.ClientRepo;
import com.impllife.bankmock.data.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityClientInfo implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClientInfo.class);

    @Autowired
    private ClientRepo clientRepo;

    public ClientSec loadClient(String username) {
        Client client = clientRepo.findByPhone(username);
        if (client == null) throw new UsernameNotFoundException("Client with name '$s' doe's not exist");
        return new ClientSec(client);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadClient(username);
    }
}
