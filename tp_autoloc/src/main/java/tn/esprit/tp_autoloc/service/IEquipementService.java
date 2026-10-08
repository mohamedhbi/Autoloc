package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Equipement;

public interface IEquipementService {

    Equipement create(Equipement equipement);          // refuse un id non null

    Equipement findById(Long id);        // ResourceNotFoundException si absent

    List<Equipement> findAll();

    Equipement update(Long id, Equipement equipement); // charge l'existant, recopie, save

    void deleteById(Long id);     // ResourceNotFoundException si absent
}
