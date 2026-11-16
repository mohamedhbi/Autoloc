package tn.esprit.tp_autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.*;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehiculeDTO {
    private Long id;
    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    @NotNull(message = "La catégorie est obligatoire")
    private CategorieVehicule categorie;
    @NotNull(message = "Le tarif journalier est obligatoire")
    @Positive(message = "Le tarif journalier doit être strictement positif")
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;
    @NotNull(message = "L'identifiant de l'agence est obligatoire")
    private Long agenceId;
}