package tn.esprit.tp_autoloc.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tp_autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tp_autoloc.web.dto.TopClientDTO;

public interface IReservationService {

    // ===== CRUD existants =====
    Reservation create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    void deleteById(Long id);
    Reservation annulerReservation(Long id);

    // ===== Atelier 7 : DTO =====
    ReservationResponseDTO creer(ReservationRequestDTO dto);

    // ===== Atelier 8 : Top clients (une seule déclaration) =====
    List<TopClientDTO> topClients(Pageable pageable);
}