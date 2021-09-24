package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Aufgabentag;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabentagMitSelectUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabentagbewertetUiDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tiedet (PiAL Consult GmbH) 2021.
 */
@Mapper
public interface FrontendMapper {

    List<AufgabeUiDto> toAufgabeUiDtoList(List<Aufgabe> aufgabeList);

    AufgabeUiDto toUiDto(Aufgabe aufgabe);

    @Mapping(target = "thementypen", source = "bewerteterAufgabentagList")
    @Mapping(target = "aufgaben", source = "aufgabeList")
    ProfilUiDto toUiDto(Profil profil);

    @Mapping(target = "selected", constant = "false")
    AufgabentagMitSelectUiDto toUiDto(Aufgabentag aufgabentag);

    @AfterMapping
    default void afterMap(@MappingTarget ProfilUiDto profilUiDto, Profil profil) {
        if (profilUiDto != null && profilUiDto.getThementypen() != null) {
            List<Integer> benutzeAufgabentagIds = profilUiDto.getThementypen().stream()
                .map(AufgabentagbewertetUiDto::getAufgabentag)
                .map(AufgabentagMitSelectUiDto::getAufgabentagId)
                .collect(Collectors.toList());
            profilUiDto.getAllPossibleTagList().forEach(tag -> {
                if (benutzeAufgabentagIds.contains(tag.getAufgabentagId())) {
                    tag.selected(true);
                }
            });
        }
    }

    @Mapping(target = "aufgabenberwtungHistList", ignore = true)
    Aufgabe toDomain(AufgabeUiDto aufgabeUiDto);


}
