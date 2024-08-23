package fr.diginamic.hello.Controllers;

import fr.diginamic.hello.controllers.DepartementController;
import fr.diginamic.hello.controllers.genericmodels.ApiResponse;
import fr.diginamic.hello.exceptions.ResourceNotFoundException;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.services.DepartementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DepartementControllerTest {

    @Mock
    private DepartementService departementService;

    @InjectMocks
    private DepartementController departementController;

    private Departement departement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Departement Test");
    }

    @Test
    void getAllDepartements_shouldReturnOk_whenDepartementsAreFound() {
        Departement departement1 = new Departement();
        departement1.setId(2L);
        departement1.setNom("Departement 1");

        Departement departement2 = new Departement();
        departement2.setId(3L);
        departement2.setNom("Departement 2");

        List<Departement> departements = Arrays.asList(departement1, departement2);
        when(departementService.getAllDepartements()).thenReturn(departements);

        ResponseEntity<ApiResponse> responseEntity = departementController.getAllDepartements();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<Departement>) responseEntity.getBody().getData()).size());
        verify(departementService, times(1)).getAllDepartements();
    }

    @Test
    void getAllDepartements_shouldReturnOk_whenNoDepartementsFound() {
        when(departementService.getAllDepartements()).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponse> responseEntity = departementController.getAllDepartements();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, ((List<Departement>) responseEntity.getBody().getData()).size());
        verify(departementService, times(1)).getAllDepartements();
    }

    @Test
    void getDepartementById_shouldReturnOk_whenDepartementExists() {
        when(departementService.getDepartementById(anyLong())).thenReturn(departement);

        ResponseEntity<ApiResponse> responseEntity = departementController.getDepartementById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(departement, responseEntity.getBody().getData());
        verify(departementService, times(1)).getDepartementById(anyLong());
    }

    @Test
    void getDepartementById_shouldReturnNotFound_whenDepartementDoesNotExist() {
        when(departementService.getDepartementById(anyLong())).thenReturn(null);

        try {
            departementController.getDepartementById(1L);
        } catch (ResourceNotFoundException e) {
            assertEquals("Departement not found with id 1", e.getMessage());
        }

        verify(departementService, times(1)).getDepartementById(anyLong());
    }

    @Test
    void createDepartement_shouldReturnOk_whenDepartementIsCreated() {
        Departement newDepartement = new Departement();
        newDepartement.setNom("New Departement");

        when(departementService.createDepartement(any(Departement.class))).thenReturn(departement);

        ResponseEntity<ApiResponse> responseEntity = departementController.createDepartement(newDepartement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(departement, responseEntity.getBody().getData());
        verify(departementService, times(1)).createDepartement(any(Departement.class));
    }

    @Test
    void updateDepartement_shouldReturnOk_whenDepartementIsUpdated() {
        Departement updatedDepartement = new Departement();
        updatedDepartement.setNom("Updated Departement");

        when(departementService.updateDepartement(anyLong(), any(Departement.class))).thenReturn(updatedDepartement);

        ResponseEntity<ApiResponse> responseEntity = departementController.updateDepartement(1L, updatedDepartement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedDepartement, responseEntity.getBody().getData());
        verify(departementService, times(1)).updateDepartement(anyLong(), any(Departement.class));
    }

    @Test
    void updateDepartement_shouldReturnNotFound_whenDepartementDoesNotExist() {
        when(departementService.updateDepartement(anyLong(), any(Departement.class))).thenReturn(null);

        try {
            departementController.updateDepartement(1L, departement);
        } catch (ResourceNotFoundException e) {
            assertEquals("Departement not found with id 1", e.getMessage());
        }

        verify(departementService, times(1)).updateDepartement(anyLong(), any(Departement.class));
    }

    @Test
    void deleteDepartement_shouldReturnOk_whenDepartementIsDeleted() {
        doNothing().when(departementService).deleteDepartement(anyLong());

        ResponseEntity<ApiResponse> responseEntity = departementController.deleteDepartement(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(departementService, times(1)).deleteDepartement(anyLong());
    }

    @Test
    void deleteDepartement_shouldReturnNotFound_whenDepartementDoesNotExist() {
        doThrow(new ResourceNotFoundException("Departement not found with id 1")).when(departementService).deleteDepartement(anyLong());

        try {
            departementController.deleteDepartement(1L);
        } catch (ResourceNotFoundException e) {
            assertEquals("Departement not found with id 1", e.getMessage());
        }

        verify(departementService, times(1)).deleteDepartement(anyLong());
    }
}
