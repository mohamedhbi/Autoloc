package tn.esprit.tp_autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp_autoloc.domain.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}