package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {

    // Méthodes existantes (entités)
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);

    // Affectation / Désaffectation (Atelier 7)
    void affecterVehiculeAAgence(Long vehiculeId, Long agenceId);
    void desaffecterVehiculeDeAgence(Long vehiculeId);

    // Nouvelles méthodes DTO (Atelier 7)
    VehiculeDTO creerDto(VehiculeDTO dto);
    VehiculeDTO obtenirParId(Long id);
    List<VehiculeDTO> listerTous();
}