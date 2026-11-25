package tn.esprit.tp_autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Paiement;

@Repository
public interface IPaiementRepository extends JpaRepository<Paiement, Long> {

    // ✅ CORRIGÉ : idContrat au lieu de id
    List<Paiement> findByContrat_IdContrat(Long contratId);

    List<Paiement> findByDatePaiementBetween(LocalDate debut, LocalDate fin);
}