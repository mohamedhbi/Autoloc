package tn.esprit.tp_autoloc.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tp_autoloc.domain.Agence;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IAgenceRepository;
import tn.esprit.tp_autoloc.repository.IVehiculeRepository;
import tn.esprit.tp_autoloc.service.IVehiculeService;
import tn.esprit.tp_autoloc.web.dto.VehiculeDTO;
import tn.esprit.tp_autoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    // ===== CRUD existants =====
    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        valider(vehicule);
        Vehicule existant = findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void valider(Vehicule vehicule) {
        if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException("L'immatriculation du véhicule est obligatoire");
        }
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }

    // ===== Affectation =====
    @Override
    @Transactional
    public void affecterVehiculeAAgence(Long vehiculeId, Long agenceId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        vehicule.setAgence(agence);
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void desaffecterVehiculeDeAgence(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        if (vehicule.getAgence() == null) {
            throw new IllegalStateException("Le véhicule n'est rattaché à aucune agence");
        }
        vehicule.setAgence(null);
        vehiculeRepository.save(vehicule);
    }

    // ===== DTO =====
    @Override
    public VehiculeDTO creerDto(VehiculeDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        if (dto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getAgenceId()));
            vehicule.setAgence(agence);
        }
        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    public VehiculeDTO obtenirParId(Long id) {
        return vehiculeMapper.toDto(findById(id));
    }

    @Override
    public List<VehiculeDTO> listerTous() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    // ===== Atelier 8 : Query Methods =====
    @Override
    public List<VehiculeDTO> findByAgenceVille(String ville) {
        return vehiculeRepository.findByAgenceVille(ville).stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    @Override
    public Page<VehiculeDTO> findByStatut(StatutVehicule statut, Pageable pageable) {
        return vehiculeRepository.findByStatut(statut, pageable).map(vehiculeMapper::toDto);
    }

    @Override
    public List<Vehicule> rechercherVehiculesDisponibles(String ville, CategorieVehicule categorie,
                                                         LocalDate dateDebut, LocalDate dateFin) {
        return vehiculeRepository.rechercherVehiculesDisponibles(ville, categorie, dateDebut, dateFin);
    }
}