package tn.esprit.tp_autoloc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.tp_autoloc.domain.Client;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.enums.StatutReservation;
import tn.esprit.tp_autoloc.repository.IReservationRepository;
import tn.esprit.tp_autoloc.service.impl.ReservationServiceImpl;
import tn.esprit.tp_autoloc.service.observer.EmailNotificationObserver;
import tn.esprit.tp_autoloc.service.observer.ReservationSubject;
import tn.esprit.tp_autoloc.service.observer.SmsNotificationObserver;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private IReservationRepository reservationRepository;

    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        EmailNotificationObserver emailObs = new EmailNotificationObserver();
        SmsNotificationObserver smsObs = new SmsNotificationObserver();
        ReservationSubject subject = new ReservationSubject(List.of(emailObs, smsObs));
        reservationService = new ReservationServiceImpl(reservationRepository, subject);
    }

    @Test
    void testCreationReservation_DoitNotifierEmailEtSms() {
        Client client = new Client();
        client.setEmail("test@client.com");
        client.setTelephone("0612345678");

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation saved = reservationService.create(reservation);

        assertNotNull(saved);
        assertEquals(StatutReservation.CONFIRMEE, saved.getStatut());
    }

    @Test
    void testAnnulationReservation_DoitNotifierEmailEtSms() {
        Client client = new Client();
        client.setEmail("test@client.com");
        client.setTelephone("0612345678");

        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setClient(client);
        reservation.setStatut(StatutReservation.CONFIRMEE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation annulee = reservationService.annulerReservation(1L);

        assertEquals(StatutReservation.ANNULEE, annulee.getStatut());
    }
}