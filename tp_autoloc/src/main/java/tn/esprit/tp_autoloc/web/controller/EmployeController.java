package tn.esprit.tp_autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp_autoloc.web.dto.EmployeDTO;
import tn.esprit.tp_autoloc.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeDTO> creer(@Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.creerDto(dto));
    }

    @PutMapping("/{employeId}/agence/{agenceId}")
    public ResponseEntity<Void> affecter(@PathVariable Long employeId, @PathVariable Long agenceId) {
        employeService.affecterEmployeAAgence(employeId, agenceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{employeId}/desaffecter")
    public ResponseEntity<Void> desaffecter(@PathVariable Long employeId) {
        employeService.desaffecterEmployeDeAgence(employeId);
        return ResponseEntity.ok().build();
    }
}