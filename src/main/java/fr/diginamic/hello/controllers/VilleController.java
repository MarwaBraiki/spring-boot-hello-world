package fr.diginamic.hello.controllers;

import fr.diginamic.hello.exceptions.ResourceNotFoundException;
import fr.diginamic.hello.controllers.genericmodels.ApiResponse;

import fr.diginamic.hello.model.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    private final VilleService villeService;

    @Autowired
    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllVilles() {
        List<Ville> villes = villeService.findAll();
        if (villes == null || villes.isEmpty()) {
            ApiResponse response = new ApiResponse(false, "No villes found", null);
            return ResponseEntity.noContent().build();
        }
        ApiResponse response = new ApiResponse(true, "Villes retrieved successfully", villes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVilleById(@PathVariable Long id) {
        Ville ville = villeService.findById(id);
        if (ville == null) {
            ApiResponse response = new ApiResponse(false, "City not found", null);
            return ResponseEntity.notFound().build();
        }
        ApiResponse response = new ApiResponse(true, "City retrieved successfully", ville);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createVille(@RequestBody Ville ville) {
        Ville createdVille = villeService.create(ville);
        ApiResponse response = new ApiResponse(true, "City created successfully", createdVille);
        return ResponseEntity.status(201).body(response); // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateVille(@PathVariable Long id, @RequestBody Ville villeModifie) {
        Ville updatedVille = villeService.update(id, villeModifie);
        if (updatedVille == null) {
            ApiResponse response = new ApiResponse(false, "City not found", null);
            return ResponseEntity.notFound().build();
        }
        ApiResponse response = new ApiResponse(true, "City updated successfully", updatedVille);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVille(@PathVariable Long id) {
        try {
            villeService.delete(id);
            ApiResponse response = new ApiResponse(true, "City deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.notFound().build();
        }
    }
}
