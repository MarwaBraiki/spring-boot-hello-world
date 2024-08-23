package fr.diginamic.hello.services;

import fr.diginamic.hello.persistence.IVilleRepository;
import fr.diginamic.hello.model.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final IVilleRepository villeDAO;

    @Autowired
    public VilleService(IVilleRepository villeDAO) {
        this.villeDAO = villeDAO;
    }

    // Retrieve all cities
    public List<Ville> findAll() {
        return villeDAO.findAll();
    }

    // Find a city by ID
    public Ville findById(Long id) {
        return villeDAO.findById(id).orElse(null); // Return null if not found
    }

    // Create a new city
    public Ville create(Ville ville) {
        return villeDAO.save(ville);
    }

    // Update an existing city
    public Ville update(Long id, Ville villeModifie) {
        // Check if the city exists
        if (villeDAO.existsById(id)) {
            villeModifie.setId(id); // Set the ID of the city to be updated
            return villeDAO.save(villeModifie);
        }
        return null; // Return null if city with given ID does not exist
    }

    // Delete a city by ID
    public void delete(Long id) {
        if (villeDAO.existsById(id)) {
            villeDAO.deleteById(id);
        }
    }
}
