package tn.esprit.tp_autoloc.web.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp_autoloc.service.IContratService;
import tn.esprit.tp_autoloc.web.dto.ChiffreAffaireAgenceDTO;
import tn.esprit.tp_autoloc.web.dto.ContratDTO;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // ===== Création =====
    @PostMapping
    public ResponseEntity<ContratDTO> creer(@Valid @RequestBody ContratDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.creerDto(dto));
    }

    // ===== Atelier 8 : Chiffre d'affaires par agence =====
    @GetMapping("/chiffre-affaires-agences")
    public ResponseEntity<List<ChiffreAffaireAgenceDTO>> chiffreAffaireParAgence() {
        return ResponseEntity.ok(contratService.chiffreAffaireParAgence());
    }
}