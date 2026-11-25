package tn.esprit.tp_autoloc.web.controller;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;
import tn.esprit.tp_autoloc.service.IVehiculeService;
import tn.esprit.tp_autoloc.web.dto.VehiculeDTO;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // ===== CRUD existants =====
    @PostMapping
    public ResponseEntity<VehiculeDTO> creer(@Valid @RequestBody VehiculeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.creerDto(dto));
    }

    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> findAll() {
        return ResponseEntity.ok(vehiculeService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.obtenirParId(id));
    }

    // ===== Affectation / Désaffectation =====
    @PutMapping("/{vehiculeId}/agence/{agenceId}")
    public ResponseEntity<Void> affecter(@PathVariable Long vehiculeId, @PathVariable Long agenceId) {
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{vehiculeId}/desaffecter")
    public ResponseEntity<Void> desaffecter(@PathVariable Long vehiculeId) {
        vehiculeService.desaffecterVehiculeDeAgence(vehiculeId);
        return ResponseEntity.ok().build();
    }

    // ===== Atelier 8 : Query Methods =====
    @GetMapping("/ville")
    public ResponseEntity<List<VehiculeDTO>> findByAgenceVille(@RequestParam String ville) {
        return ResponseEntity.ok(vehiculeService.findByAgenceVille(ville));
    }

    @GetMapping("/statut")
    public ResponseEntity<Page<VehiculeDTO>> findByStatut(
            @RequestParam StatutVehicule statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(vehiculeService.findByStatut(statut, PageRequest.of(page, size)));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehicule>> rechercherDisponibles(
            @RequestParam String ville,
            @RequestParam CategorieVehicule categorie,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(vehiculeService.rechercherVehiculesDisponibles(ville, categorie, dateDebut, dateFin));
    }
}