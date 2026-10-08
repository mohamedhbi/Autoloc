package tn.esprit.tp_autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule")
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Maintenance> maintenances = new ArrayList<>();

    @ManyToMany
    private Set<Equipement> equipements = new HashSet<>();
}