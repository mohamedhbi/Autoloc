package tn.esprit.tp_autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Employe;
import tn.esprit.tp_autoloc.domain.enums.RoleEmploye;

@Repository
public interface IEmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByAgenceNom(String nomAgence);

    // ✅ CORRIGÉ : idAgence au lieu de id
    List<Employe> findByAgence_IdAgenceAndRole(Long agenceId, RoleEmploye role);

    List<Employe> findByNomContaining(String nom);
}