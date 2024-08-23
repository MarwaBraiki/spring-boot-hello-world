package fr.diginamic.hello.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class Departement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name of the department cannot be blank")
    @Size(min = 2, max = 50, message = "The name of the department must be between 2 and 50 characters")
    private String nom;

    @NotBlank(message = "The code of the department cannot be blank")
    @Size(min = 2, max = 3, message = "The code of the department must be between 2 and 50 characters")
    private String code;
    // Getters et Setters

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
