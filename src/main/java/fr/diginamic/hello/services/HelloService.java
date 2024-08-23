package fr.diginamic.hello.services;

import org.springframework.stereotype.Service;

/**
 * Cette classe représente un service qui fournit des messages de salutation.
 */
@Service
public class HelloService {

    /**
     * Retourne un message de salutation.
     *
     * @return Un message de salutation.
     */
    public String salutations() {
        return "Je suis la classe de service et je vous dis Bonjour";
    }
}
