package tn.esprit.tp_autoloc.service.strategy;

import java.math.BigDecimal;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.Vehicule;

public interface ITarificationStrategy {
    BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation);
    String getCode();
}