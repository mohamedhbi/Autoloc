package tn.esprit.tp_autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tp_autoloc.domain.Employe;
import tn.esprit.tp_autoloc.web.dto.EmployeDTO;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(target = "id", source = "idEmploye")
    @Mapping(target = "agenceId", source = "agence.idAgence")
    EmployeDTO toDto(Employe employe);

    @Mapping(target = "idEmploye", source = "id")
    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);
}