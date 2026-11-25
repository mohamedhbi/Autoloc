package tn.esprit.tp_autoloc.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;
import tn.esprit.tp_autoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {

    // CRUD existants
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);

    // Affectation
    void affecterVehiculeAAgence(Long vehiculeId, Long agenceId);
    void desaffecterVehiculeDeAgence(Long vehiculeId);

    // DTO
    VehiculeDTO creerDto(VehiculeDTO dto);
    VehiculeDTO obtenirParId(Long id);
    List<VehiculeDTO> listerTous();

    // ===== Atelier 8 : Query Methods =====
    List<VehiculeDTO> findByAgenceVille(String ville);

    Page<VehiculeDTO> findByStatut(StatutVehicule statut, Pageable pageable);

    List<Vehicule> rechercherVehiculesDisponibles(String ville, CategorieVehicule categorie,
                                                  LocalDate dateDebut, LocalDate dateFin);
}