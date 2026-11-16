package tn.esprit.tp_autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tp_autoloc.domain.Client;
import tn.esprit.tp_autoloc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", source = "idClient")
    ClientDTO toDto(Client client);

    @Mapping(target = "idClient", source = "id")
    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);
}