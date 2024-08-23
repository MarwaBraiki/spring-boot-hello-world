package fr.diginamic.hello.persistence;


import fr.diginamic.hello.model.Ville;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IVilleRepository extends JpaRepository <Ville, Long>{


}
