package tn.esprit.tp_autoloc.service.strategy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.Vehicule;

@Component
public class TarifWeekendStrategy implements ITarificationStrategy {

    private static final BigDecimal REMISE_WEEKEND = new BigDecimal("0.90");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (jours == 0) jours = 1;
        BigDecimal prixBase = vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
        return prixBase.multiply(REMISE_WEEKEND);
    }

    @Override
    public String getCode() {
        return "WEEKEND";
    }
}