package tn.esprit.tp_autoloc.web.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp_autoloc.service.IReservationService;
import tn.esprit.tp_autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tp_autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tp_autoloc.web.dto.TopClientDTO;
import tn.esprit.tp_autoloc.web.validator.ReservationRequestValidator;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRequestValidator reservationRequestValidator;
    private final IReservationService reservationService; // ← injecté

    // ===== Création avec validateur personnalisé =====
    @PostMapping
    public ResponseEntity<?> creer(
            @Valid @RequestBody ReservationRequestDTO dto,
            BindingResult result) {

        // 1. Validation personnalisée (dates)
        reservationRequestValidator.validate(dto, result);

        if (result.hasErrors()) {
            java.util.Map<String, String> erreurs = new java.util.HashMap<>();
            result.getFieldErrors().forEach(err ->
                    erreurs.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erreurs);
        }

        // 2. Appel au service pour créer la réservation
        ReservationResponseDTO response = reservationService.creer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ===== Atelier 8 : Top 5 clients =====
    @GetMapping("/top-clients")
    public ResponseEntity<List<TopClientDTO>> topClients(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(reservationService.topClients(PageRequest.of(0, limit)));
    }
}