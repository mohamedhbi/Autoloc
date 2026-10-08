package tn.esprit.tp_autoloc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tp_autoloc.domain.Contrat;
public interface IVehiculeRepository extends JpaRepository<Contrat, Long>{
}
