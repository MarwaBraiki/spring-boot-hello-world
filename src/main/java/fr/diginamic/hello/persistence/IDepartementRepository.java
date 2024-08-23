package fr.diginamic.hello.persistence;

import fr.diginamic.hello.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartementRepository extends JpaRepository<Departement, Long> {
    // Custom query methods can be defined here if needed
}