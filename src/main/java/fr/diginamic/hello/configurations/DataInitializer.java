package fr.diginamic.hello.configurations;

import fr.diginamic.hello.persistence.IDepartementRepository;
import fr.diginamic.hello.persistence.IVilleRepository;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.model.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final IVilleRepository IVilleRepository;
    private final IDepartementRepository IDepartementRepository;

    @Autowired
    public DataInitializer(IVilleRepository IVilleRepository, IDepartementRepository IDepartementRepository) {
        this.IVilleRepository = IVilleRepository;
        this.IDepartementRepository = IDepartementRepository;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (IVilleRepository.findAll().isEmpty() && IDepartementRepository.findAll().isEmpty()) {
                // Création des départements
                Departement dept1 = new Departement();
                dept1.setNom("Haute-Garonne");
                IDepartementRepository.save(dept1);
                System.out.println("Département créé: Haute-Garonne");

                Departement dept2 = new Departement();
                dept2.setNom("Finistère");
                IDepartementRepository.save(dept2);
                System.out.println("Département créé: Finistère");

                Departement dept3 = new Departement();
                dept3.setNom("Tunisie");
                IDepartementRepository.save(dept3);
                System.out.println("Département créé: Tunisie");

                // Création des villes
                Ville ville1 = new Ville();
                ville1.setNom("Toulouse");
                ville1.setPopulation(500000);
                ville1.setDepartement(dept1);
                IVilleRepository.save(ville1);
                System.out.println("Ville créée: Toulouse");

                Ville ville2 = new Ville();
                ville2.setNom("Brest");
                ville2.setPopulation(140000);
                ville2.setDepartement(dept2);
                IVilleRepository.save(ville2);
                System.out.println("Ville créée: Brest");

                Ville ville3 = new Ville();
                ville3.setNom("Tunis");
                ville3.setPopulation(1200000);
                ville3.setDepartement(dept3);
                IVilleRepository.save(ville3);
                System.out.println("Ville créée: Tunis");

                System.out.println("Données initiales ajoutées !");
            } else {
                System.out.println("Les données existent déjà !");
            }
        };
    }
}
