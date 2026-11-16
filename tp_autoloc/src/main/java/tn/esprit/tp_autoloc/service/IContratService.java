package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Contrat;
import tn.esprit.tp_autoloc.web.dto.ContratDTO;

public interface IContratService {

    // Méthodes existantes (entités)
    Contrat create(Contrat contrat);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat contrat);
    void deleteById(Long id);

    // NOUVELLE méthode DTO (Atelier 7)
    ContratDTO creerDto(ContratDTO dto);
}