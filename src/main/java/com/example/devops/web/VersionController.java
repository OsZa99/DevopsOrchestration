package com.example.devops.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("api/version")
public class VersionController {
    @Autowired
    private Environment environment;

    @GetMapping
    public Map<String, String> getVersionInfo() {
        Map<String, String> response = new HashMap<>();

        // Récupère les variables d'environnement
        String appVersion = environment.getProperty("APP_VERSION");
        String appUser = environment.getProperty("APP_USER");

        // Ajoute les informations dans la réponse
        response.put("message", "This is version " + appVersion + " of the application.");
        response.put("version", appVersion);
        response.put("message2", "This is user " + appUser + " of the application.");
        response.put("user", appUser);

        return response;
    }

}

