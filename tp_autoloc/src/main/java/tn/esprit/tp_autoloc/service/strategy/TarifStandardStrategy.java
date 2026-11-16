package tn.esprit.tp_autoloc.service.strategy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.Vehicule;

@Component
public class TarifStandardStrategy implements ITarificationStrategy {

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (jours == 0) jours = 1;
        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
    }

    @Override
    public String getCode() {
        return "STANDARD";
    }
}