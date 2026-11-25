package tn.esprit.tp_autoloc.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;

@Repository
public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // ===== Exercice 1 : Query Methods =====
    List<Vehicule> findByAgenceVille(String ville);

    // ✅ CORRIGÉ : IdAgence au lieu de Id
    List<Vehicule> findByStatutAndAgence_IdAgence(StatutVehicule statut, Long agenceId);

    Page<Vehicule> findByStatut(StatutVehicule statut, Pageable pageable);

    List<Vehicule> findByAgence_VilleAndCategorie(String ville, CategorieVehicule categorie);

    // ✅ CORRIGÉ : IdVehicule au lieu de Id
    boolean existsByIdVehiculeAndStatut(Long vehiculeId, StatutVehicule statut);

    long countByStatut(StatutVehicule statut);

    List<Vehicule> findByTarifJournalierLessThan(BigDecimal tarif);

    List<Vehicule> findByTarifJournalierGreaterThan(BigDecimal tarif);

    List<Vehicule> findAllByOrderByTarifJournalierAsc();

    // ===== Exercice 7 : JPQL personnalisé =====
    @Query("""
        SELECT v FROM Vehicule v
        WHERE v.agence.ville = :ville
          AND v.categorie = :categorie
          AND v.statut = tn.esprit.tp_autoloc.domain.enums.StatutVehicule.DISPONIBLE
          AND v.idVehicule NOT IN (
              SELECT r.vehicule.idVehicule FROM Reservation r
              WHERE r.statut IN (tn.esprit.tp_autoloc.domain.enums.StatutReservation.EN_ATTENTE,
                                 tn.esprit.tp_autoloc.domain.enums.StatutReservation.CONFIRMEE)
                AND r.dateDebut <= :dateFin
                AND r.dateFin >= :dateDebut
          )
    """)
    List<Vehicule> rechercherVehiculesDisponibles(
            @Param("ville") String ville,
            @Param("categorie") CategorieVehicule categorie,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);

    @Query("""
        SELECT DISTINCT v FROM Vehicule v
        JOIN v.reservations r
        WHERE r.dateFin <= :aujourdhui
          AND r.statut = tn.esprit.tp_autoloc.domain.enums.StatutReservation.CONFIRMEE
    """)
    List<Vehicule> trouverVehiculesAReleaser(@Param("aujourdhui") LocalDate aujourdhui);
}