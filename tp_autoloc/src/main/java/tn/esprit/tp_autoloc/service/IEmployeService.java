package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Employe;
import tn.esprit.tp_autoloc.web.dto.EmployeDTO;

public interface IEmployeService {

    Employe create(Employe employe);          // refuse un id non null

    Employe findById(Long id);        // ResourceNotFoundException si absent

    List<Employe> findAll();

    Employe update(Long id, Employe employe); // charge l'existant, recopie, save


    EmployeDTO creerDto(EmployeDTO dto);

    void deleteById(Long id);     // ResourceNotFoundException si absent

    void affecterEmployeAAgence(Long employeId, Long agenceId);
    void desaffecterEmployeDeAgence(Long employeId);

}
