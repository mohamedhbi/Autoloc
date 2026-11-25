package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Contrat;
import tn.esprit.tp_autoloc.web.dto.ChiffreAffaireAgenceDTO;
import tn.esprit.tp_autoloc.web.dto.ContratDTO;

public interface IContratService {

    Contrat create(Contrat contrat);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat contrat);
    void deleteById(Long id);

    ContratDTO creerDto(ContratDTO dto);

    List<ChiffreAffaireAgenceDTO> chiffreAffaireParAgence();
}