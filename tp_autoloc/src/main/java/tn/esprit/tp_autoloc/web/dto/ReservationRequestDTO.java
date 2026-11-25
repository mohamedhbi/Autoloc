package tn.esprit.tp_autoloc.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationRequestDTO {
    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long clientId;
    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long vehiculeId;
    @FutureOrPresent
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;
    @Future
    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;
}