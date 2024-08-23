package fr.diginamic.hello.configurations;

import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration Spring.
 * Cette classe est utilisée pour définir des beans et autres configurations spécifiques de l'application.
 */
@Configuration
public class Config {

    /**
     * Constructeur de la classe Config.
     * Ce constructeur est appelé lorsque la classe est initialisée par Spring.
     */
    public Config() {
        System.out.println("Constructeur de Config");
    }

    /**
     * Méthode qui affiche un message de salutation dans la console.
     * Cette méthode peut être utilisée pour des tests simples ou des actions spécifiques au démarrage.
     */
    public void sayHello() {
        System.out.println("Hello World");
    }
}
