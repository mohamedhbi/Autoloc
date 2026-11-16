package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Reservation;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);

    Reservation annulerReservation(Long id);
}