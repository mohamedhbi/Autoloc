package tn.esprit.tp_autoloc.web.dto;

import java.math.BigDecimal;

public record ChiffreAffaireAgenceDTO(
        Long agenceId,
        String nomAgence,
        BigDecimal chiffreAffaires
) {}