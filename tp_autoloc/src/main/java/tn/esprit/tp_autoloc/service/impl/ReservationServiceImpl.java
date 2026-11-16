package tn.esprit.tp_autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.enums.StatutReservation;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IReservationRepository;
import tn.esprit.tp_autoloc.service.IReservationService;
import tn.esprit.tp_autoloc.service.observer.ReservationSubject;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final ReservationSubject reservationSubject;

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException("Un nouveau reservation ne doit pas avoir d'identifiant");
        }
        valider(reservation);
        if (reservation.getStatut() == null) {
            reservation.setStatut(StatutReservation.CONFIRMEE);
        }
        Reservation saved = reservationRepository.save(reservation);
        reservationSubject.notifierConfirmation(saved);
        return saved;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        valider(reservation);
        Reservation existant = findById(id);
        existant.setDateDebut(reservation.getDateDebut());
        existant.setDateFin(reservation.getDateFin());
        existant.setStatut(reservation.getStatut());
        return reservationRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public Reservation annulerReservation(Long id) {
        Reservation reservation = findById(id);
        reservation.setStatut(StatutReservation.ANNULEE);
        Reservation saved = reservationRepository.save(reservation);
        reservationSubject.notifierAnnulation(saved);
        return saved;
    }

    private void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}