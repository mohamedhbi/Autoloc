package tn.esprit.tp_autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Maintenance;

@Repository
public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
}