package tn.esprit.tp_autoloc.service.factory;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.service.strategy.ITarificationStrategy;

@Component
public class TarificationStrategyFactory {

    private final Map<String, ITarificationStrategy> strategies;

    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ITarificationStrategy::getCode, Function.identity()));
    }

    public ITarificationStrategy resoudre(Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (jours == 0) jours = 1;

        if (jours >= 7) {
            return strategies.get("LONGUE_DUREE");
        }
        if (jours <= 3 && estWeekend(reservation)) {
            return strategies.get("WEEKEND");
        }
        return strategies.get("STANDARD");
    }

    private boolean estWeekend(Reservation reservation) {
        if (reservation.getDateDebut() == null) return false;
        DayOfWeek day = reservation.getDateDebut().getDayOfWeek();
        return day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}