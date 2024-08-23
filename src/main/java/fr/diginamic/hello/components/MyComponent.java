package fr.diginamic.hello.components;

import org.springframework.stereotype.Component;

/**
 * Un composant Spring personnalisé.
 * Cette classe est un exemple de composant Spring qui sera géré par le conteneur Spring.
 */
@Component
public class MyComponent {

    /**
     * Constructeur de la classe MyComponent.
     * Ce constructeur est appelé lors de l'initialisation du composant par le conteneur Spring.
     */
    public MyComponent() {
        System.out.println("Constructeur MyComponent");
    }
}
