
package com.example.demo.ui;

import com.example.demo.domain.Utilizator;

public interface UI {
    /**
     * starts the user interface
     */
    void start();

    Utilizator readUser();

    String readEmail();
}