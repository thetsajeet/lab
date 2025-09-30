package com.thetsajeet.di.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev", "default"})
@Service
public class DevService implements MyService {
    @Override
    public String serve() {
        return "Development Service";
    }
}
