package de.master.lernprogramm.repository.mapper;

import de.master.lernprogramm.domain.objekt.Aufgabenhistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import de.master.lernprogramm.repository.entity.ProfilEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AufgabeEntityMapper.class})
public interface ProfilEntityMapper {
    @Mapping(target = "id", source = "profilId")
    @Mapping(target = "removeBewerteterAufgabentagEntity", ignore = true)
    @Mapping(target = "removeAufgabenhistorieEntity", ignore = true)
    @Mapping(target = "removeAufgabeEntity", ignore = true)
    @Mapping(target = "bewerteterAufgabentagEntities", ignore = true)
    @Mapping(target = "aufgabenhistorieEntities", ignore = true)
    @Mapping(target = "aufgabeEntities", ignore = true)
    ProfilEntity toEntity(Profil profil);

    @Mapping(target = "profilId", source = "id")
    @Mapping(target = "aufgabeList", source = "aufgabeEntities")
    @Mapping(target = "bewerteterAufgabentagList", ignore = true)
    @Mapping(target = "aufgabenhistorieList", source = "aufgabenhistorieEntities")
    @Mapping(target = "punkte", ignore = true)
    @Mapping(target = "rang", ignore = true)
    Profil toDomain(ProfilEntity profilEntity);

    Aufgabenhistorie toDomain(AufgabenhistorieEntity aufgabenhistorieEntity);


}
