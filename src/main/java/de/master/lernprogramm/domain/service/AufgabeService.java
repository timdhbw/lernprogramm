package de.master.lernprogramm.domain.service;

import javassist.NotFoundException;

public interface AufgabeService {

    de.master.lernprogramm.web.api.dtos.AufgabeUiDto getAufgabeById(Integer aufgabeId) throws NotFoundException;
}
