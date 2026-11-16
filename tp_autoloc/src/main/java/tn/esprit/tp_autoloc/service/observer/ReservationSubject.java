package tn.esprit.tp_autoloc.service.observer;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import tn.esprit.tp_autoloc.domain.Reservation;

@Component
public class ReservationSubject {

    private final List<IReservationObserver> observers;

    public ReservationSubject(List<IReservationObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    public void notifierConfirmation(Reservation reservation) {
        observers.forEach(o -> o.onReservationConfirmee(reservation));
    }

    public void notifierAnnulation(Reservation reservation) {
        observers.forEach(o -> o.onReservationAnnulee(reservation));
    }
}