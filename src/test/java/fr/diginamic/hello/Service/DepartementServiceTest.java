package fr.diginamic.hello.Service;


import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.persistence.IDepartementRepository;
import fr.diginamic.hello.services.DepartementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DepartementServiceTest {

    @Mock
    private IDepartementRepository departementDAO;

    @InjectMocks
    private DepartementService departementService;

    private Departement departement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Departement Test");
    }

    @Test
    void getAllDepartements_shouldReturnListOfDepartements_whenDepartementsExist() {
        Departement departement1 = new Departement();
        departement1.setId(2L);
        departement1.setNom("Departement 1");

        Departement departement2 = new Departement();
        departement2.setId(3L);
        departement2.setNom("Departement 2");

        List<Departement> departements = Arrays.asList(departement1, departement2);
        when(departementDAO.findAll()).thenReturn(departements);

        List<Departement> result = departementService.getAllDepartements();

        assertEquals(2, result.size());
        assertEquals("Departement 1", result.get(0).getNom());
        assertEquals("Departement 2", result.get(1).getNom());
        verify(departementDAO, times(1)).findAll();
    }

    @Test
    void getDepartementById_shouldReturnDepartement_whenDepartementExists() {
        when(departementDAO.findById(anyLong())).thenReturn(Optional.of(departement));

        Departement result = departementService.getDepartementById(1L);

        assertNotNull(result);
        assertEquals("Departement Test", result.getNom());
        verify(departementDAO, times(1)).findById(anyLong());
    }

    @Test
    void getDepartementById_shouldReturnNull_whenDepartementDoesNotExist() {
        when(departementDAO.findById(anyLong())).thenReturn(Optional.empty());

        Departement result = departementService.getDepartementById(1L);

        assertNull(result);
        verify(departementDAO, times(1)).findById(anyLong());
    }

    @Test
    void createDepartement_shouldReturnCreatedDepartement_whenDepartementIsValid() {
        Departement newDepartement = new Departement();
        newDepartement.setNom("New Departement");

        when(departementDAO.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.createDepartement(newDepartement);

        assertNotNull(result);
        assertEquals(departement.getNom(), result.getNom());
        verify(departementDAO, times(1)).save(any(Departement.class));
    }

    @Test
    void updateDepartement_shouldReturnUpdatedDepartement_whenDepartementExists() {
        Departement updatedDepartement = new Departement();
        updatedDepartement.setNom("Updated Departement");

        when(departementDAO.findById(anyLong())).thenReturn(Optional.of(departement));
        when(departementDAO.save(any(Departement.class))).thenReturn(updatedDepartement);

        Departement result = departementService.updateDepartement(1L, updatedDepartement);

        assertNotNull(result);
        assertEquals("Updated Departement", result.getNom());
        verify(departementDAO, times(1)).findById(anyLong());
        verify(departementDAO, times(1)).save(any(Departement.class));
    }

    @Test
    void updateDepartement_shouldReturnNull_whenDepartementDoesNotExist() {
        Departement updatedDepartement = new Departement();
        updatedDepartement.setNom("Updated Departement");

        when(departementDAO.findById(anyLong())).thenReturn(Optional.empty());

        Departement result = departementService.updateDepartement(1L, updatedDepartement);

        assertNull(result);
        verify(departementDAO, times(1)).findById(anyLong());
        verify(departementDAO, times(0)).save(any(Departement.class));
    }

    @Test
    void deleteDepartement_shouldDeleteDepartement_whenDepartementExists() {
        doNothing().when(departementDAO).deleteById(anyLong());

        departementService.deleteDepartement(1L);

        verify(departementDAO, times(1)).deleteById(anyLong());
    }
}
