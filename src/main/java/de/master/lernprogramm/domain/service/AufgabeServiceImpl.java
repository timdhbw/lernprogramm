package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import de.master.lernprogramm.domain.objekt.*;
import de.master.lernprogramm.repository.AufgabeRepository;
import de.master.lernprogramm.util.Randomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AufgabeServiceImpl implements AufgabeService {

    private final AufgabeRepository aufgabeRepository;

    private final ProfilService profilService;

    public AufgabeServiceImpl(AufgabeRepository aufgabeRepository, ProfilService profilService) {
        this.aufgabeRepository = aufgabeRepository;
        this.profilService = profilService;
    }

    @Override
    public Aufgabe getAufgabeById(Integer aufgabeId) {
        log.info("AufgabeId: {}", aufgabeId);
        if (aufgabeId == null) {
            return createNewAufgabe();
        }
        return aufgabeRepository.getAufgabeById(aufgabeId);
//        if (aufgabeId < 1 || aufgabeId > 5) {
//            return null;
//        }
//        return getAufgabe(aufgabeId);
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabe) {
        log.info("AufgabeToSave: {}", aufgabe);
        Aufgabe saved = aufgabeRepository.saveAufgabe(aufgabe);
        log.info("Saved Aufgabe: {}", saved);
        return saved;
    }

    @Override
    public String getRandomNextAufgabeForUser(User user) {
        if (hasUserAufgabeTodayAbgeschlossen(user)) {
            return "abgeschlossen" + LocalDate.now().toString();
        }
        final List<Integer> possibleIds = aufgabeRepository.getAllPossibleAufgabenIds();
        getAbgeschlosseneAufgabenIdsVonUser(user).forEach(possibleIds::remove);
        getAufgabenVonUser(user).forEach(possibleIds::remove);
        if (possibleIds.isEmpty()) {
            return null;
        }
        return Randomizer.getRandomItemOfList(possibleIds).toString();
    }

    private boolean hasUserAufgabeTodayAbgeschlossen(User user) {
        Profil profil = profilService.getProfilById(user.getId().toString());
        if (profil.getAufgabenhistorieList() != null) {
            return profil.getAufgabenhistorieList().stream().anyMatch(aufgHist -> LocalDate.now().equals(aufgHist.getDatum()));
        }
        return false;
    }

    private List<Integer> getAufgabenVonUser(User user) {
        if (user == null || user.getId() == null) {
            log.error("Keine Aufgabe von User: {}", user);
            return new ArrayList<>();
        }
        Profil profil = profilService.getProfilById(user.getId().toString());
        if (profil == null || profil.getAufgabeList() == null) {
            log.error("Keine Aufgaben von Profil: {}", profil);
            return new ArrayList<>();
        }
        return profil.getAufgabeList().stream().map(Aufgabe::getAufgabeId).collect(Collectors.toList());
    }

    @Override
    public void bewerteAufgabe(Integer aufgabeId, Integer aufgabeBewertung) {
        AufgabenbewertungHistorie aufgabenbewertungHistorie = new AufgabenbewertungHistorie();
        aufgabenbewertungHistorie.setDatum(LocalDate.now());
        aufgabenbewertungHistorie.setBewertungsveraenderung(aufgabeBewertung);
        aufgabeRepository.setAufgabeBewertung(aufgabeId, aufgabenbewertungHistorie);
    }

    @Override
    public double berechneErgebnis(Aufgabe aufgabe) {
        if (aufgabe == null || aufgabe.getAufgabenteilList() == null) {
            return 0;
        }
        return aufgabe.getAufgabenteilList().stream()
            .mapToDouble(Aufgabenteil::werteAufgabenteilAus)
            .filter(doub -> doub > -1)
            .average().orElse(5);
    }

    private List<Integer> getAbgeschlosseneAufgabenIdsVonUser(User user) {
        Profil profil = profilService.getProfilById(user.getId().toString());
        if (profil.getAufgabenhistorieList() == null) {
          return new ArrayList<>();
        }
        return profil.getAufgabenhistorieList().stream()
            .map(Aufgabenhistorie::getAufgabe)
            .map(Aufgabe::getAufgabeId)
            .collect(Collectors.toList());
    }

    private Aufgabe createNewAufgabe() {
        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setAufgabenteilList(new ArrayList<>());
        aufgabe.setAufgabentagList(new ArrayList<>());
        aufgabe.setKategorie(AufgabenkategorieEnum.SOFTWAREENTWICKLUNG);
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
                return Collections.singletonList(Aufgabenteil.builder()
                    .laufenNr(1).aufgabenteiltyp(AufgabenteiltypEnum.TEXT)
                    .text("Aufgabe ??ber Angular und HTML")
                    .build());
            case 2:
                return Arrays.asList(Aufgabenteil.builder().laufenNr(1)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Aufgabe ??ber Vue...").build(),
                    Aufgabenteil.builder().laufenNr(2)
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("... HTML geh??rt auch dazu").build());
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
                        .aufgabenteiltyp(AufgabenteiltypEnum.TEXT).text("Was w??re").build(),
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
