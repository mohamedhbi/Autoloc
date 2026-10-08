package tn.esprit.tp_autoloc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tp_autoloc.domain.enums.StatutReservation;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
// contrat yetaamal wala supprimer maa el reservation
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;
}