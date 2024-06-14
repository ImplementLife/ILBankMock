package com.impllife.bankmock.services;

import com.impllife.bankmock.data.entity.Client;
import com.impllife.bankmock.data.entity.security.ClientSec;
import com.impllife.bankmock.data.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class SecurityClientInfo implements UserDetailsService {
    @Autowired
    private ClientRepo clientRepo;

    public ClientSec loadClient(String username) {
        Client client = clientRepo.findByPhoneNumber(username)
            .orElseThrow(() -> new UsernameNotFoundException(format("Client with name [%s] doe's not exist", username)));
        return new ClientSec(client);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadClient(username);
    }
}
