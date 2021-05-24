package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author tiedet (PiAL Consult GmbH) 2021.
 */
@Mapper
public interface FrontendMapper {

    @Mapping(target = "profilId", source = "id")
    @Mapping(target = "vorname", source = "firstName")
    @Mapping(target = "nachname", source = "lastName")
    @Mapping(target = "thementypen", ignore = true)
    @Mapping(target = "punkte", ignore = true)
    @Mapping(target = "rang", ignore = true)
    ProfilUiDto toUiDto(User user);

    @Mapping(target = "aufgabenteilList", ignore = true)
    @Mapping(target = "autor", ignore = true)
    AufgabeUiDto toUiDto(Aufgabe aufgabe);

}
