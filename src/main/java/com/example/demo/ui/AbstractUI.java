package com.example.demo.ui;

import com.example.demo.service.Service;

import java.util.UUID;

public abstract class AbstractUI implements UI{
    Service<UUID> srv;

    public AbstractUI(Service<UUID>  srv) {
        this.srv = srv;
    }
}