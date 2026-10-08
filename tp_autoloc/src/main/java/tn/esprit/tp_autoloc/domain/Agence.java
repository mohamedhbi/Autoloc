package tn.esprit.tp_autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
//cascade all yaani si nfasakh agence bch nfaasakh automatiallly employe w vehicule
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)

    private List<Employe> employes = new ArrayList<>();

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)

    private List<Vehicule> vehicules = new ArrayList<>();
}