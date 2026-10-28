package tn.esprit.tp_autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String numPermis;
    private LocalDate dateInscription;

    // Côté inverse : Reservation est créée seule avec "client": {"idClient": 1} -> @JsonIgnore
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();
}