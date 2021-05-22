package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.web.api.dtos.AufgabentagUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabenteilUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AufgabeServiceImpl implements AufgabeService {
    @Override
    public Aufgabe getAufgabeById(Integer aufgabeId) {
        if (aufgabeId == null || aufgabeId < 1 || aufgabeId > 5) {
            return null;
        }
        return getAufgabe(aufgabeId);
    }

    private Aufgabe getAufgabe(Integer aufgabeId) {
        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setAufgabeId(aufgabeId);
        aufgabe.setAutor(new ProfilUiDto().profilId("test1"));
        aufgabe.setAufgabentitel("Aufgabe " + aufgabeId);
        aufgabe.setKategorie(AufgabenkategorieEnum.SOFTWAREENTWICKLUNG);
        aufgabe.setAufgabentagList(getAufgabentagList(aufgabeId));
        aufgabe.setAufgabenteilList(getAufgabenteilList(aufgabeId));
        return aufgabe;
    }

    private List<de.master.lernprogramm.web.api.dtos.AufgabentagUiDto> getAufgabentagList(Integer aufgabeId) {
        switch (aufgabeId) {
            case 1:
                return Arrays.asList(new AufgabentagUiDto().tag("angular"), new AufgabentagUiDto().tag("html"));
            case 2:
                return Arrays.asList(new AufgabentagUiDto().tag("vue"), new AufgabentagUiDto().tag("html"), new AufgabentagUiDto().tag("css"));
            case 3:
                return Arrays.asList(new AufgabentagUiDto().tag("css"), new AufgabentagUiDto().tag("html5"));
            case 4:
                return Arrays.asList(new AufgabentagUiDto().tag("angular"), new AufgabentagUiDto().tag("vue"));
            case 5:
                return Arrays.asList(new AufgabentagUiDto().tag("angular"), new AufgabentagUiDto().tag("html5"), new AufgabentagUiDto().tag("css"));
            default:
                return new ArrayList<>();
        }
    }

    private List<de.master.lernprogramm.web.api.dtos.AufgabenteilUiDto> getAufgabenteilList(Integer aufgabeId) {
        switch (aufgabeId) {
            case 1:
                return Arrays.asList(new AufgabenteilUiDto().laufenNr(1)
                    .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Aufgabe über Angular und HTML"));
            case 2:
                return Arrays.asList(new AufgabenteilUiDto().laufenNr(1)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Aufgabe über Vue..."),
                    new AufgabenteilUiDto().laufenNr(2)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("... HTML gehört auch dazu"));
            case 3:
                return Arrays.asList(new AufgabenteilUiDto().laufenNr(1)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Jeder"),
                    new AufgabenteilUiDto().laufenNr(7)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("CSS"),
                    new AufgabenteilUiDto().laufenNr(2)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("hasst"),
                    new AufgabenteilUiDto().laufenNr(6)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("und"),
                    new AufgabenteilUiDto().laufenNr(3)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Aufgaben"),
                    new AufgabenteilUiDto().laufenNr(5)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("HTML5"),
                    new AufgabenteilUiDto().laufenNr(4)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("mit"),
                    new AufgabenteilUiDto().laufenNr(8)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("!!!"));
            case 4:
                return Arrays.asList(new AufgabenteilUiDto().laufenNr(1)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Angular"),
                    new AufgabenteilUiDto().laufenNr(2)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("und"),
                    new AufgabenteilUiDto().laufenNr(3)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Vue"),
                    new AufgabenteilUiDto().laufenNr(4)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("nerven"));
            case 5:
                return Arrays.asList(new AufgabenteilUiDto().laufenNr(1)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Was wäre"),
                    new AufgabenteilUiDto().laufenNr(2)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("Angular"),
                    new AufgabenteilUiDto().laufenNr(3)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("ohne"),
                    new AufgabenteilUiDto().laufenNr(4)
                        .aufgabentyp(AufgabenteilUiDto.AufgabentypEnum.TEXT).text("HTML und CSS?"));
            default:
                return new ArrayList<>();
        }
    }
}
