package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
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

    AufgabeUiDto toUiDto(Aufgabe aufgabe);

    @Mapping(target = "thementypen", source = "bewerteterAufgabentagList")
    ProfilUiDto toUiDto(Profil profil);
}
