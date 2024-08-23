package fr.diginamic.hello.controllers;

import fr.diginamic.hello.exceptions.ResourceNotFoundException;
import fr.diginamic.hello.controllers.genericmodels.ApiResponse;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.services.DepartementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;

    @Autowired
    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        ApiResponse response = new ApiResponse(true, "Departements retrieved successfully", departements);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDepartementById(@PathVariable Long id) {
        Departement departement = departementService.getDepartementById(id);
        if (departement == null) {
            throw new ResourceNotFoundException("Departement not found with id " + id);
        }
        ApiResponse response = new ApiResponse(true, "Departement retrieved successfully", departement);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDepartement(@RequestBody Departement departement) {
        Departement createdDepartement = departementService.createDepartement(departement);
        ApiResponse response = new ApiResponse(true, "Departement created successfully", createdDepartement);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDepartement(@PathVariable Long id, @RequestBody Departement departementModifie) {
        Departement updatedDepartement = departementService.updateDepartement(id, departementModifie);
        if (updatedDepartement == null) {
            throw new ResourceNotFoundException("Departement not found with id " + id);
        }
        ApiResponse response = new ApiResponse(true, "Departement updated successfully", updatedDepartement);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        ApiResponse response = new ApiResponse(true, "Departement deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
