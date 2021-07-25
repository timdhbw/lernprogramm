package de.master.lernprogramm.repository.mapper;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Aufgabentag;
import de.master.lernprogramm.domain.objekt.Aufgabenteil;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProfilEntityMapper.class})
public interface AufgabeEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aufgabenteilEntities", source = "aufgabenteilList")
    @Mapping(target = "removeAufgabenteilEntity", ignore = true)
    @Mapping(target = "aufgabenhistorieEntities", ignore = true)
    @Mapping(target = "removeAufgabenhistorieEntity", ignore = true)
    @Mapping(target = "aufgabentags", ignore = true)
    @Mapping(target = "removeAufgabentag", ignore = true)
    @Mapping(target = "autor", ignore = true)
    AufgabeEntity toEntity(Aufgabe aufgabe);

    @Mapping(target = "id", source = "aufgabenteilId")
    @Mapping(target = "aufgabe", ignore = true)
    AufgabenteilEntity toEntity(Aufgabenteil aufgabenteil);

    @Mapping(target = "autorId", source = "autor.id")
    @Mapping(target = "aufgabeId", source = "id")
    @Mapping(target = "aufgabentagList", source = "aufgabentags")
    @Mapping(target = "aufgabenteilList", source = "aufgabenteilEntities")
    Aufgabe toDomain(AufgabeEntity aufgabeEntity);

    @Mapping(target = "aufgabentagId", source = "id")
    Aufgabentag toDomain(AufgabentagEntity aufgabentagEntity);

    @Mapping(target = "aufgabenteilId", source = "id")
    Aufgabenteil toDomain(AufgabenteilEntity aufgabenteilEntity);
}
