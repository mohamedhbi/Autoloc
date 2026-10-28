package tn.esprit.tp_autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Contrat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

    // Côté inverse du OneToOne (la clé contrat_id est dans Reservation) -> @JsonIgnore
    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private Reservation reservation;

    //un paiement ytnaha m liste yetfsakh mel base
    // Composition : les paiements se créent dans le JSON du contrat
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("contrat-paiements")
    private List<Paiement> paiements = new ArrayList<>();
}