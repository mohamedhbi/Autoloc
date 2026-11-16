package tn.esprit.tp_autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp_autoloc.domain.Contrat;
import tn.esprit.tp_autoloc.domain.Paiement;
import tn.esprit.tp_autoloc.domain.Reservation;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.repository.IContratRepository;
import tn.esprit.tp_autoloc.repository.IReservationRepository;
import tn.esprit.tp_autoloc.service.IContratService;
import tn.esprit.tp_autoloc.web.dto.ContratDTO;
import tn.esprit.tp_autoloc.web.dto.PaiementDTO;
import tn.esprit.tp_autoloc.web.mapper.ContratMapper;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;   // ← ajout
    private final ContratMapper contratMapper;

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        valider(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        valider(contrat);
        Contrat existant = findById(id);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    // ===== Méthode DTO avec gestion des paiements et de la réservation =====
    @Override
    public ContratDTO creerDto(ContratDTO dto) {
        // 1. Convertir le DTO en entité
        Contrat contrat = contratMapper.toEntity(dto);

        // 2. Associer la réservation si fournie
        if (dto.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(dto.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", dto.getReservationId()));
            contrat.setReservation(reservation);
        }

        // 3. Associer les paiements au contrat (cascade via orphanRemoval)
        if (dto.getPaiements() != null && !dto.getPaiements().isEmpty()) {
            for (PaiementDTO p : dto.getPaiements()) {
                Paiement paiement = new Paiement();
                paiement.setMontant(p.getMontant());
                paiement.setDatePaiement(p.getDatePaiement());
                paiement.setModePaiement(p.getModePaiement());
                paiement.setContrat(contrat);  // lien bidirectionnel
                contrat.getPaiements().add(paiement);
            }
        }

        // 4. Sauvegarder (les paiements sont sauvés en cascade)
        Contrat saved = contratRepository.save(contrat);

        // 5. Reconvertir en DTO
        return contratMapper.toDto(saved);
    }

    private void valider(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }
    }
}