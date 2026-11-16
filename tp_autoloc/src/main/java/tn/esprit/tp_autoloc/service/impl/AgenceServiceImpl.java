package tn.esprit.tp_autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ✅ BON IMPORT
import tn.esprit.tp_autoloc.domain.Agence;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IAgenceRepository;
import tn.esprit.tp_autoloc.service.IAgenceService;
import tn.esprit.tp_autoloc.web.dto.AgenceDTO;
import tn.esprit.tp_autoloc.web.mapper.AgenceMapper;

@Service
@RequiredArgsConstructor
@Transactional // ✅ Spring, pas Jakarta
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau agence ne doit pas avoir d'identifiant");
        }
        valider(agence);
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional(readOnly = true) // ✅ Maintenant reconnu
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        valider(agence);
        Agence existant = findById(id);
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private void valider(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
    }

    // ===== Méthodes DTO (Atelier 7) =====

    @Override
    public AgenceDTO creerDto(AgenceDTO dto) {
        Agence agence = agenceMapper.toEntity(dto);
        Agence saved = agenceRepository.save(agence);
        return agenceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO obtenirParId(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        return agenceMapper.toDto(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> listerToutes() {
        return agenceRepository.findAll().stream()
                .map(agenceMapper::toDto)
                .toList();
    }
}