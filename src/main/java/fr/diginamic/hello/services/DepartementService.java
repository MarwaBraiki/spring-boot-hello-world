package fr.diginamic.hello.services;

import fr.diginamic.hello.persistence.IDepartementRepository;
import fr.diginamic.hello.model.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    private final IDepartementRepository departementDAO;

    @Autowired
    public DepartementService(IDepartementRepository IDepartementRepository) {
        this.departementDAO = IDepartementRepository;
    }

    public List<Departement> getAllDepartements() {
        return departementDAO.findAll();
    }

    public Departement getDepartementById(Long id) {
        // findById returns an Optional
        Optional<Departement> departementOptional = departementDAO.findById(id);
        return departementOptional.orElse(null); // Return null if not found
    }

    public Departement createDepartement(Departement departement) {
        // Save returns the persisted entity
        return departementDAO.save(departement);
    }

    public Departement updateDepartement(Long id, Departement departementModifie) {
        // Check if the entity exists
        Optional<Departement> departementOptional = departementDAO.findById(id);
        if (departementOptional.isPresent()) {
            Departement departement = departementOptional.get();
            departement.setNom(departementModifie.getNom());
            // Save returns the updated entity
            return departementDAO.save(departement);
        }
        return null;
    }

    public void deleteDepartement(Long id) {
        // Use deleteById to remove the entity by its ID
        departementDAO.deleteById(id);
    }
}
