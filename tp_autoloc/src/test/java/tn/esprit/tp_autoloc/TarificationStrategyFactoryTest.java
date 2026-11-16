package tn.esprit.tp_autoloc;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.service.factory.TarificationStrategyFactory;
import tn.esprit.tp_autoloc.service.strategy.ITarificationStrategy;
import tn.esprit.tp_autoloc.service.strategy.TarifLongueDureeStrategy;
import tn.esprit.tp_autoloc.service.strategy.TarifStandardStrategy;
import tn.esprit.tp_autoloc.service.strategy.TarifWeekendStrategy;

class TarificationStrategyFactoryTest {

    private TarificationStrategyFactory factory;
    private TarifStandardStrategy standard;
    private TarifWeekendStrategy weekend;
    private TarifLongueDureeStrategy longueDuree;

    @BeforeEach
    void setUp() {
        standard = new TarifStandardStrategy();
        weekend = new TarifWeekendStrategy();
        longueDuree = new TarifLongueDureeStrategy();
        factory = new TarificationStrategyFactory(List.of(standard, weekend, longueDuree));
    }

    @Test
    void doitRetournerLongueDureePour7JoursOuPlus() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 1, 1));
        r.setDateFin(LocalDate.of(2024, 1, 8));

        ITarificationStrategy resultat = factory.resoudre(r);

        assertThat(resultat).isInstanceOf(TarifLongueDureeStrategy.class);
        assertThat(resultat.getCode()).isEqualTo("LONGUE_DUREE");
    }

    @Test
    void doitRetournerLongueDureePourExactement7Jours() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 5, 1));
        r.setDateFin(LocalDate.of(2024, 5, 8));

        ITarificationStrategy resultat = factory.resoudre(r);

        assertThat(resultat.getCode()).isEqualTo("LONGUE_DUREE");
    }

    @Test
    void doitRetournerWeekendPourCourtSejourVendredi() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 3, 8));
        r.setDateFin(LocalDate.of(2024, 3, 10));

        ITarificationStrategy resultat = factory.resoudre(r);

        assertThat(resultat).isInstanceOf(TarifWeekendStrategy.class);
        assertThat(resultat.getCode()).isEqualTo("WEEKEND");
    }

    @Test
    void doitRetournerStandardPourMoyenneDuree() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 3, 4));
        r.setDateFin(LocalDate.of(2024, 3, 8));

        ITarificationStrategy resultat = factory.resoudre(r);

        assertThat(resultat.getCode()).isEqualTo("STANDARD");
    }
}