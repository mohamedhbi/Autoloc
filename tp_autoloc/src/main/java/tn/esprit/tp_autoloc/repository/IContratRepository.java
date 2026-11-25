package tn.esprit.tp_autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Contrat;
import tn.esprit.tp_autoloc.web.dto.ChiffreAffaireAgenceDTO;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {

    // ===== Exercice 9.1 : Chiffre d'affaires par agence =====
    @Query("""
        SELECT new tn.esprit.tp_autoloc.web.dto.ChiffreAffaireAgenceDTO(
            a.idAgence, a.nom, COALESCE(SUM(c.montantTotal), 0)
        )
        FROM Contrat c
        JOIN c.reservation r
        JOIN r.vehicule v
        JOIN v.agence a
        WHERE c.valide = true
        GROUP BY a.idAgence, a.nom
    """)
    List<ChiffreAffaireAgenceDTO> chiffreAffaireParAgence();

    // ===== Exercice 9.2 : Contrats à échéance proche =====
    @Query("""
        SELECT c FROM Contrat c
        WHERE c.dateSignature BETWEEN :aujourdhui AND :dateLimite
    """)
    List<Contrat> trouverContratsAEcheanceProche(
            @Param("aujourdhui") LocalDate aujourdhui,
            @Param("dateLimite") LocalDate dateLimite);
}