package de.master.lernprogramm.repository.mapper;

import de.master.lernprogramm.domain.objekt.*;
import de.master.lernprogramm.repository.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProfilEntityMapper.class})
public interface AufgabeEntityMapper {

    @Mapping(target = "id", source = "aufgabeId")
    @Mapping(target = "aufgabenteilEntities", source = "aufgabenteilList")
    @Mapping(target = "aufgabenbwtunghistEntities", source = "aufgabenberwtungHistList")
    @Mapping(target = "removeAufgabenteilEntity", ignore = true)
    @Mapping(target = "aufgabenhistorieEntities", ignore = true)
    @Mapping(target = "removeAufgabenhistorieEntity", ignore = true)
    @Mapping(target = "aufgabentags", ignore = true)
    @Mapping(target = "removeAufgabentag", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "removeAufgabenbwtunghistEntity", ignore = true)
    AufgabeEntity toEntity(Aufgabe aufgabe);

    @Mapping(target = "id", source = "aufgabenteilId")
    @Mapping(target = "aufgabe", ignore = true)
    @Mapping(target = "removeMultipleChoiceAntwortEntity", ignore = true)
    @Mapping(target = "multipleChoiceAntwortEntities", source = "multiplechoiceAntwortenList")
    AufgabenteilEntity toEntity(Aufgabenteil aufgabenteil);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aufgabenteil", ignore = true)
    MultipleChoiceAntwortEntity toEntity(MultipleChoiceAntwort multipleChoiceAntwort);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aufgabe", ignore = true)
    AufgabenbwtunghistEntity toEntity(AufgabenbewertungHistorie aufgabenbewertunghistorie);

    @Mapping(target = "autorId", source = "autor.id")
    @Mapping(target = "aufgabeId", source = "id")
    @Mapping(target = "aufgabentagList", source = "aufgabentags")
    @Mapping(target = "aufgabenteilList", source = "aufgabenteilEntities")
    @Mapping(target = "aufgabenberwtungHistList", source = "aufgabenbwtunghistEntities")
    Aufgabe toDomain(AufgabeEntity aufgabeEntity);

    @Mapping(target = "aufgabentagId", source = "id")
    Aufgabentag toDomain(AufgabentagEntity aufgabentagEntity);

    @Mapping(target = "aufgabenteilId", source = "id")
    @Mapping(target = "multiplechoiceAntwortenList", source = "multipleChoiceAntwortEntities")
    Aufgabenteil toDomain(AufgabenteilEntity aufgabenteilEntity);
}
