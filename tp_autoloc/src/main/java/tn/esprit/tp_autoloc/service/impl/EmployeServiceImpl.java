package tn.esprit.tp_autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp_autoloc.domain.Employe;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IEmployeRepository;
import tn.esprit.tp_autoloc.service.IEmployeService;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tp_autoloc.domain.Agence;
import tn.esprit.tp_autoloc.repository.IAgenceRepository;
import tn.esprit.tp_autoloc.web.dto.EmployeDTO;
import tn.esprit.tp_autoloc.web.mapper.EmployeMapper;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final EmployeMapper employeMapper;

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau employe ne doit pas avoir d'identifiant");
        }
        valider(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        valider(employe);
        Employe existant = findById(id);
        // seuls les attributs simples sont recopiés : ni l'identifiant, ni les associations
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void valider(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
        }
        if (employe.getPrenom() == null || employe.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom de l'employé est obligatoire");
        }
    }
    @Override
    @Transactional
    public void affecterEmployeAAgence(Long employeId, Long agenceId) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", employeId));
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        employe.setAgence(agence);
        employeRepository.save(employe);
    }

    @Override
    @Transactional
    public void desaffecterEmployeDeAgence(Long employeId) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", employeId));
        if (employe.getAgence() == null) {
            throw new IllegalStateException("L'employé n'est rattaché à aucune agence");
        }
        employe.setAgence(null);
        employeRepository.save(employe);
    }
    @Override
    public EmployeDTO creerDto(EmployeDTO dto) {
        Employe employe = employeMapper.toEntity(dto);
        if (dto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getAgenceId()));
            employe.setAgence(agence);
        }
        Employe saved = employeRepository.save(employe);
        return employeMapper.toDto(saved);
    }
}
