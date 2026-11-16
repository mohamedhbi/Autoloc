package tn.esprit.tp_autoloc.service;

import java.util.List;
import tn.esprit.tp_autoloc.domain.Client;
import tn.esprit.tp_autoloc.web.dto.ClientDTO;

public interface IClientService {

    // Méthodes existantes (entités)
    Client create(Client client);
    Client findById(Long id);
    List<Client> findAll();
    Client update(Long id, Client client);
    void deleteById(Long id);

    // Nouvelles méthodes DTO (Atelier 7)
    ClientDTO creerDto(ClientDTO dto);
    ClientDTO obtenirParId(Long id);
    List<ClientDTO> listerTous();
}