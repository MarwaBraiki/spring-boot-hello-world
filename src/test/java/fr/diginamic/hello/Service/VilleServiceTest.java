package fr.diginamic.hello.Service;

import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.model.Ville;
import fr.diginamic.hello.persistence.IVilleRepository;
import fr.diginamic.hello.services.VilleService;
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

class VilleServiceTest {

    @Mock
    private IVilleRepository villeDAO;

    @InjectMocks
    private VilleService villeService;

    private Ville ville;
    private Departement departement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Test Departement");

        ville = new Ville();
        ville.setId(1L);
        ville.setNom("Test Ville");
        ville.setPopulation(50000);
        ville.setDepartement(departement);
    }

    @Test
    void findAll_shouldReturnListOfVilles_whenVillesExist() {
        Ville ville1 = new Ville();
        ville1.setId(2L);
        ville1.setNom("Ville 1");
        ville1.setPopulation(100000);
        ville1.setDepartement(departement);

        Ville ville2 = new Ville();
        ville2.setId(3L);
        ville2.setNom("Ville 2");
        ville2.setPopulation(150000);
        ville2.setDepartement(departement);

        List<Ville> villes = Arrays.asList(ville1, ville2);
        when(villeDAO.findAll()).thenReturn(villes);

        List<Ville> result = villeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Ville 1", result.get(0).getNom());
        assertEquals("Ville 2", result.get(1).getNom());
        verify(villeDAO, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnVille_whenVilleExists() {
        when(villeDAO.findById(anyLong())).thenReturn(Optional.of(ville));

        Ville result = villeService.findById(1L);

        assertNotNull(result);
        assertEquals("Test Ville", result.getNom());
        verify(villeDAO, times(1)).findById(anyLong());
    }

    @Test
    void findById_shouldReturnNull_whenVilleDoesNotExist() {
        when(villeDAO.findById(anyLong())).thenReturn(Optional.empty());

        Ville result = villeService.findById(1L);

        assertNull(result);
        verify(villeDAO, times(1)).findById(anyLong());
    }

    @Test
    void create_shouldReturnCreatedVille_whenVilleIsValid() {
        Ville newVille = new Ville();
        newVille.setNom("New Ville");
        newVille.setPopulation(80000);
        newVille.setDepartement(departement);

        when(villeDAO.save(any(Ville.class))).thenReturn(ville);

        Ville result = villeService.create(newVille);

        assertNotNull(result);
        assertEquals("Test Ville", result.getNom());  // Here, the mock is returning `ville`, so the name is "Test Ville".
        verify(villeDAO, times(1)).save(any(Ville.class));
    }

    @Test
    void update_shouldReturnUpdatedVille_whenVilleExists() {
        Ville updatedVille = new Ville();
        updatedVille.setNom("Updated Ville");
        updatedVille.setPopulation(90000);
        updatedVille.setDepartement(departement);

        when(villeDAO.existsById(anyLong())).thenReturn(true);
        when(villeDAO.save(any(Ville.class))).thenReturn(updatedVille);

        Ville result = villeService.update(1L, updatedVille);

        assertNotNull(result);
        assertEquals("Updated Ville", result.getNom());
        verify(villeDAO, times(1)).existsById(anyLong());
        verify(villeDAO, times(1)).save(any(Ville.class));
    }

    @Test
    void update_shouldReturnNull_whenVilleDoesNotExist() {
        Ville updatedVille = new Ville();
        updatedVille.setNom("Updated Ville");
        updatedVille.setPopulation(90000);
        updatedVille.setDepartement(departement);

        when(villeDAO.existsById(anyLong())).thenReturn(false);

        Ville result = villeService.update(1L, updatedVille);

        assertNull(result);
        verify(villeDAO, times(1)).existsById(anyLong());
        verify(villeDAO, times(0)).save(any(Ville.class));
    }

    @Test
    void delete_shouldDeleteVille_whenVilleExists() {
        when(villeDAO.existsById(anyLong())).thenReturn(true);
        doNothing().when(villeDAO).deleteById(anyLong());

        villeService.delete(1L);

        verify(villeDAO, times(1)).existsById(anyLong());
        verify(villeDAO, times(1)).deleteById(anyLong());
    }

    @Test
    void delete_shouldDoNothing_whenVilleDoesNotExist() {
        when(villeDAO.existsById(anyLong())).thenReturn(false);

        villeService.delete(1L);

        verify(villeDAO, times(1)).existsById(anyLong());
        verify(villeDAO, times(0)).deleteById(anyLong());
    }
}
