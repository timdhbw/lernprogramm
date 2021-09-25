package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.objekt.*;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabentagMitSelectUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.Arrays;
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

    @AfterMapping
    default void afterMapping(@MappingTarget Aufgabe aufgabe, AufgabeUiDto aufgabeUiDto) {
        // wenn Aufgabe neu angelegt wird, initiale Bewertung mitgeben
        if (aufgabeUiDto.getAufgabeId() == null) {
            AufgabenbewertungHistorie aufgabenbewertungHistorie = new AufgabenbewertungHistorie();
            aufgabenbewertungHistorie.setBewertungsveraenderung(aufgabeUiDto.getBewertung());
            aufgabenbewertungHistorie.setDatum(LocalDate.now());
            aufgabe.setAufgabenberwtungHistList(Arrays.asList(aufgabenbewertungHistorie));
        }
    }

}
