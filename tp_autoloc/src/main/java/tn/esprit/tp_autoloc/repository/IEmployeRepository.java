package tn.esprit.tp_autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Employe;

@Repository
public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}