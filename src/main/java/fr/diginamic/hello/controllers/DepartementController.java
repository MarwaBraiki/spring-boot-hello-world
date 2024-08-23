package fr.diginamic.hello.controllers;

import fr.diginamic.hello.exceptions.ResourceNotFoundException;
import fr.diginamic.hello.controllers.genericmodels.ApiResponse;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.services.DepartementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
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

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportDepartementsToCsv() {
        List<Departement> departements = departementService.getAllDepartements();
        if (departements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        StringWriter writer = new StringWriter();
        writer.write("Department Code,Department Name\n");

        for (Departement departement : departements) {
            writer.write(departement.getCode() + "," + departement.getNom() + "\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=departements.csv");

        return new ResponseEntity<>(writer.toString(), headers, HttpStatus.OK);
    }
}
