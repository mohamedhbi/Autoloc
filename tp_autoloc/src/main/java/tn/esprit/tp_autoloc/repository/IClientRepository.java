package tn.esprit.tp_autoloc.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Client;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Optional<Client> findByNumPermis(String numPermis);

    List<Client> findByNomContaining(String nom);

    boolean existsByEmail(String email);
}