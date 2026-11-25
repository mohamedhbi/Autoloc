package tn.esprit.tp_autoloc.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;

    @PositiveOrZero(message = "Le montant total doit être positif ou nul")
    private BigDecimal montantTotal;

    private boolean valide;
    private Long reservationId;

    @Valid
    private List<PaiementDTO> paiements;
}