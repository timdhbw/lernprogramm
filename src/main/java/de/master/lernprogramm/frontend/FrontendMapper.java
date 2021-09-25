package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Aufgabentag;
import de.master.lernprogramm.domain.objekt.BewerteterAufgabentag;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabentagMitSelectUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author tiedet (PiAL Consult GmbH) 2021.
 */
@Mapper
public interface FrontendMapper {

    List<AufgabeUiDto> toAufgabeUiDtoList(List<Aufgabe> aufgabeList);

    AufgabeUiDto toUiDto(Aufgabe aufgabe);

    @Mapping(target = "aufgaben", source = "aufgabeList")
    @Mapping(target = "allTagsBewertetList", source = "allTagListBewertet")
    ProfilUiDto toUiDto(Profil profil);

    @Mapping(target = "selected", constant = "false")
    @Mapping(target = "bewertung", constant = "0")
    AufgabentagMitSelectUiDto toUiDto(Aufgabentag aufgabentag);

    @Mapping(target = "aufgabentagId", source = "aufgabentag.aufgabentagId")
    @Mapping(target = "tag", source = "aufgabentag.tag")
    @Mapping(target = "text", source = "aufgabentag.text")
    AufgabentagMitSelectUiDto toUiDto(BewerteterAufgabentag bewerteterAufgabentag);

    @Mapping(target = "aufgabenberwtungHistList", ignore = true)
    Aufgabe toDomain(AufgabeUiDto aufgabeUiDto);


}
