package fr.diginamic.hello.controllers;

import fr.diginamic.hello.configurations.Config;
import fr.diginamic.hello.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST qui gère les requêtes liées aux salutations.
 */
@RestController
@RequestMapping("/hello")
public class HelloControleur {

    private final HelloService helloService;
    private final Config config;

    /**
     * Constructeur avec injection de dépendances pour HelloService et Config.
     *
     * @param helloService Le service de salutation injecté par Spring.
     * @param config La configuration injectée par Spring.
     */
    @Autowired
    public HelloControleur(HelloService helloService, Config config) {
        this.helloService = helloService;
        this.config = config;
    }

    /**
     * Gère les requêtes GET vers l'endpoint "/hello".
     *
     * @return Un message de salutation.
     */
    @GetMapping
    public String direBonjour() {
        config.sayHello(); // Appel de la méthode sayHello de la classe Config
        return helloService.salutations();
    }
}
