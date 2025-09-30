package com.thetsajeet.di.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class ProdService implements MyService {
    @Override
    public String serve() {
        return "Production Service";
    }
}
