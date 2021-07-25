package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Aufgabentag;
import de.master.lernprogramm.domain.objekt.Aufgabenteil;
import de.master.lernprogramm.repository.AufgabeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AufgabeServiceImpl implements AufgabeService {

    private final AufgabeRepository aufgabeRepository;

    public AufgabeServiceImpl(AufgabeRepository aufgabeRepository, ProfilService profilService) {
        this.aufgabeRepository = aufgabeRepository;
    }

    @Override
    public Aufgabe getAufgabeById(Integer aufgabeId) {
        log.info("AufgabeId: {}", aufgabeId);
        if (aufgabeId == null) {
            return createNewAufgabe();
        }
        if (aufgabeId < 1 || aufgabeId > 5) {
            return null;
        }
        return getAufgabe(aufgabeId);
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabe) {
        log.info("AufgabeToSave: {}", aufgabe);
        Aufgabe saved = aufgabeRepository.saveAufgabe(aufgabe);
        log.info("Saved Aufgabe: {}", saved);
        return saved;
    }

    private Aufgabe createNewAufgabe() {
        return getAufgabe(1);
    }

    private Aufgabe getAufgabe(Integer aufgabeId) {
        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setAufgabeId(aufgabeId);
        aufgabe.setAutorId(1);
        aufgabe.setAufgabentitel("Aufgabe " + aufgabeId);
        aufgabe.setKategorie(AufgabenkategorieEnum.SOFTWAREENTWICKLUNG);
        aufgabe.setAufgabentagList(getAufgabentagList(aufgabeId));
        aufgabe.setAufgabenteilList(getAufgabenteilList(aufgabeId));
        return aufgabe;
    }

    private List<Aufgabentag> getAufgabentagList(Integer aufgabeId) {
        switch (aufgabeId) {
            case 1:
                return Arrays.asList(Aufgabentag.builder().tag("angular").build(),
                    Aufgabentag.builder().tag("html").build());
            case 2:
                return Arrays.asList(Aufgabentag.builder().tag("vue").build(),
                    Aufgabentag.builder().tag("html").build(), Aufgabentag.builder().tag("css").build());
            case 3:
                return Arrays.asList(Aufgabentag.builder().tag("css").build(),
                    Aufgabentag.builder().tag("html5").build());
            case 4:
                return Arrays.asList(Aufgabentag.builder().tag("angular").build(),
                    Aufgabentag.builder().tag("vue").build());
            case 5:
                return Arrays.asList(Aufgabentag.builder().tag("angular").build(),
                    Aufgabentag.builder().tag("html5").build(), Aufgabentag.builder().tag("css").build());
            default:
                return new ArrayList<>();
        }
    }

    private List<Aufgabenteil> getAufgabenteilList(Integer aufgabeId) {
        switch (aufgabeId) {
            case 1:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1).aufgabenteiltyp(AufgabenteiltypEnum.TEXT)
                    .text("Aufgabe über Angular und HTML").build());
            case 2:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Aufgabe über Vue...").build(),
                    Aufgabenteil.builder().laufenNr(2)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("... HTML gehört auch dazu").build());
            case 3:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Jeder").build(),
                    Aufgabenteil.builder().laufenNr(7)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("CSS").build(),
                    Aufgabenteil.builder().laufenNr(2)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("hasst").build(),
                    Aufgabenteil.builder().laufenNr(6)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("und").build(),
                    Aufgabenteil.builder().laufenNr(3)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Aufgaben").build(),
                    Aufgabenteil.builder().laufenNr(5)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("HTML5").build(),
                    Aufgabenteil.builder().laufenNr(4)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("mit").build(),
                    Aufgabenteil.builder().laufenNr(8)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("!!!").build());
            case 4:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Angular").build(),
                    Aufgabenteil.builder().laufenNr(2)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("und").build(),
                    Aufgabenteil.builder().laufenNr(3)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Vue").build(),
                    Aufgabenteil.builder().laufenNr(4)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("nerven").build());
            case 5:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Was wäre").build(),
                    Aufgabenteil.builder().laufenNr(2)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Angular").build(),
                    Aufgabenteil.builder().laufenNr(3)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("ohne").build(),
                    Aufgabenteil.builder().laufenNr(4)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("HTML und CSS?").build());
            default:
                return new ArrayList<>();
        }
    }
}
