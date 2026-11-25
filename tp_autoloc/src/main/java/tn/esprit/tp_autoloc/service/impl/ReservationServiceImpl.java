package tn.esprit.tp_autoloc.service.impl;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.tp_autoloc.domain.Client;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutReservation;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IClientRepository;
import tn.esprit.tp_autoloc.repository.IReservationRepository;
import tn.esprit.tp_autoloc.repository.IVehiculeRepository;
import tn.esprit.tp_autoloc.service.IReservationService;
import tn.esprit.tp_autoloc.service.factory.TarificationStrategyFactory;
import tn.esprit.tp_autoloc.service.observer.ReservationSubject;
import tn.esprit.tp_autoloc.service.strategy.ITarificationStrategy;
import tn.esprit.tp_autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tp_autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tp_autoloc.web.dto.TopClientDTO;
import tn.esprit.tp_autoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationSubject reservationSubject;
    private final TarificationStrategyFactory tarificationStrategyFactory;
    private final ReservationMapper reservationMapper;

    // ===== CRUD existants =====
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

    // ===== Atelier 7 : Création depuis DTO =====
    @Override
    public ReservationResponseDTO creer(ReservationRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId()));
        Vehicule vehicule = vehiculeRepository.findById(dto.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getVehiculeId()));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved = reservationRepository.save(reservation);
        reservationSubject.notifierConfirmation(saved);

        // Calcul du tarif via la Factory (Atelier 6)
        ITarificationStrategy strategy = tarificationStrategyFactory.resoudre(saved);
        BigDecimal tarif = strategy.calculerTarif(vehicule, saved);

        ReservationResponseDTO response = reservationMapper.toDto(saved);
        response.setTarifEstime(tarif);
        return response;
    }

    // ===== Atelier 8 : Top clients =====
    @Override
    public List<TopClientDTO> topClients(Pageable pageable) {
        return reservationRepository.topClients(pageable);
    }
}