package tn.esprit.tp_autoloc.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Agence;

@Repository
public interface IAgenceRepository extends JpaRepository<Agence, Long> {

    Optional<Agence> findByVille(String ville);

    Optional<Agence> findByNomContaining(String nom);
}