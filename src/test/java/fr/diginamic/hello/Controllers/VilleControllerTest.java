package fr.diginamic.hello.Controllers;

import fr.diginamic.hello.controllers.VilleController;
import fr.diginamic.hello.controllers.genericmodels.ApiResponse;
import fr.diginamic.hello.exceptions.ResourceNotFoundException;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.model.Ville;
import fr.diginamic.hello.services.VilleService;
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

class VilleControllerTest {

    @Mock
    private VilleService villeService;

    @InjectMocks
    private VilleController villeController;

    private Departement departement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departement = new Departement(); // Assuming a simple constructor or set required fields
        departement.setId(1L);
        departement.setNom("Department1");
    }

    @Test
    void getAllVilles_shouldReturnNoContent_whenNoVillesFound() {
        when(villeService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponse> responseEntity = villeController.getAllVilles();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(villeService, times(1)).findAll();
    }

    @Test
    void getAllVilles_shouldReturnOk_whenVillesAreFound() {
        Ville ville1 = new Ville(1L, "City1", 5000, departement);
        Ville ville2 = new Ville(2L, "City2", 8000, departement);
        List<Ville> villes = Arrays.asList(ville1, ville2);
        when(villeService.findAll()).thenReturn(villes);

        ResponseEntity<ApiResponse> responseEntity = villeController.getAllVilles();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<Ville>) responseEntity.getBody().getData()).size());
        verify(villeService, times(1)).findAll();
    }

    @Test
    void getVilleById_shouldReturnNotFound_whenVilleDoesNotExist() {
        when(villeService.findById(anyLong())).thenReturn(null);

        ResponseEntity<ApiResponse> responseEntity = villeController.getVilleById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(villeService, times(1)).findById(anyLong());
    }

    @Test
    void getVilleById_shouldReturnOk_whenVilleExists() {
        Ville ville = new Ville(1L, "City1", 5000, departement);
        when(villeService.findById(anyLong())).thenReturn(ville);

        ResponseEntity<ApiResponse> responseEntity = villeController.getVilleById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ville, responseEntity.getBody().getData());
        verify(villeService, times(1)).findById(anyLong());
    }

    @Test
    void createVille_shouldReturnCreated_whenVilleIsCreated() {
        Ville ville = new Ville("NewCity", 10000, departement);
        Ville createdVille = new Ville(1L, "NewCity", 10000, departement);
        when(villeService.create(any(Ville.class))).thenReturn(createdVille);

        ResponseEntity<ApiResponse> responseEntity = villeController.createVille(ville);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdVille, responseEntity.getBody().getData());
        verify(villeService, times(1)).create(any(Ville.class));
    }

    @Test
    void updateVille_shouldReturnNotFound_whenVilleDoesNotExist() {
        Ville villeModifie = new Ville(1L, "UpdatedCity", 12000, departement);
        when(villeService.update(anyLong(), any(Ville.class))).thenReturn(null);

        ResponseEntity<ApiResponse> responseEntity = villeController.updateVille(1L, villeModifie);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(villeService, times(1)).update(anyLong(), any(Ville.class));
    }

    @Test
    void updateVille_shouldReturnOk_whenVilleIsUpdated() {
        Ville villeModifie = new Ville(1L, "UpdatedCity", 12000, departement);
        when(villeService.update(anyLong(), any(Ville.class))).thenReturn(villeModifie);

        ResponseEntity<ApiResponse> responseEntity = villeController.updateVille(1L, villeModifie);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(villeModifie, responseEntity.getBody().getData());
        verify(villeService, times(1)).update(anyLong(), any(Ville.class));
    }

    @Test
    void deleteVille_shouldReturnNotFound_whenVilleDoesNotExist() {
        doThrow(new ResourceNotFoundException("City not found")).when(villeService).delete(anyLong());

        ResponseEntity<ApiResponse> responseEntity = villeController.deleteVille(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(villeService, times(1)).delete(anyLong());
    }

    @Test
    void deleteVille_shouldReturnOk_whenVilleIsDeleted() {
        doNothing().when(villeService).delete(anyLong());

        ResponseEntity<ApiResponse> responseEntity = villeController.deleteVille(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(villeService, times(1)).delete(anyLong());
    }
}
