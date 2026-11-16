package tn.esprit.tp_autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp_autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tp_autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tp_autoloc.web.validator.ReservationRequestValidator;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRequestValidator reservationRequestValidator;
    // private final IReservationService reservationService; // à brancher plus tard

    @PostMapping
    public ResponseEntity<?> creer(
            @Valid @RequestBody ReservationRequestDTO dto,
            BindingResult result) {

        reservationRequestValidator.validate(dto, result);

        if (result.hasErrors()) {
            java.util.Map<String, String> erreurs = new java.util.HashMap<>();
            result.getFieldErrors().forEach(err ->
                    erreurs.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erreurs);
        }

        // ... créer la réservation
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}