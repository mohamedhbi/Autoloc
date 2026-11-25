package tn.esprit.tp_autoloc.web.dto;

public record TopClientDTO(
        Long clientId,
        String nom,
        String prenom,
        Long nombreReservations
) {}