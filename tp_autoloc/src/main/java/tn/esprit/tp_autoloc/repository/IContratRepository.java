package tn.esprit.tp_autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.tp_autoloc.domain.Contrat;

public interface IContratRepository extends CrudRepository<Contrat, Long> {
}