package tn.esprit.tp_autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.enums.StatutReservation;
import tn.esprit.tp_autoloc.web.dto.TopClientDTO;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    // ===== Exercice 3 =====
    List<Reservation> findByClientIdClient(Long idClient);

    List<Reservation> findByVehicule_IdVehiculeAndStatutNot(Long vehiculeId, StatutReservation statut);

    List<Reservation> findByStatut(StatutReservation statut);

    List<Reservation> findByDateDebutBetween(LocalDate debut, LocalDate fin);

    List<Reservation> findByDateFinAfter(LocalDate date);

    // ✅ CORRIGÉ : VehiculeAgenceIdAgence au lieu de VehiculeAgenceId
    List<Reservation> findByStatutAndVehiculeAgenceIdAgence(StatutReservation statut, Long agenceId);

    boolean existsByVehicule_IdVehiculeAndStatut(Long vehiculeId, StatutReservation statut);

    // ===== Exercice 8 : Top 5 clients =====
    @Query("""
        SELECT new tn.esprit.tp_autoloc.web.dto.TopClientDTO(
            c.idClient, c.nom, c.prenom, COUNT(r.idReservation)
        )
        FROM Reservation r
        JOIN r.client c
        GROUP BY c.idClient, c.nom, c.prenom
        ORDER BY COUNT(r.idReservation) DESC
    """)
    List<TopClientDTO> topClients(Pageable pageable);
}