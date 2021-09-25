package de.master.lernprogramm.domain.objekt;


import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Profil {

    private String profilId;
    private String vorname;
    private String nachname;
    private String gewaehlteTags;
    private List<Aufgabenhistorie> aufgabenhistorieList;
    private List<Aufgabe> aufgabeList;
    private List<Aufgabentag> allPossibleTagList;

    private int punkte;
    private int rang;

    public List<BewerteterAufgabentag> getAllTagListBewertet() {
        List<BewerteterAufgabentag> bewerteterAufgabentagList = new ArrayList<>();
        if (aufgabenhistorieList == null || allPossibleTagList == null) {
            return bewerteterAufgabentagList;
        }
        allPossibleTagList
            .forEach(tag -> {
                double bewertung = aufgabenhistorieList.stream()
                    .filter(aufgabenhistorie -> aufgabenhistorie.getAufgabe().getAufgabentagList().stream()
                        .anyMatch(aufgtag -> tag.getAufgabentagId().equals(aufgtag.getAufgabentagId())))
                    .mapToDouble(Aufgabenhistorie::getBewertungNachFormel)
                    .average().orElse(0);
                BewerteterAufgabentag result = new BewerteterAufgabentag();
                result.setAufgabentag(tag);
                result.setBewertung(Double.valueOf(bewertung).intValue());
                bewerteterAufgabentagList.add(result);
            });
        setSelectedIfTagIsSelected(bewerteterAufgabentagList);
        return bewerteterAufgabentagList;
    }

    private void setSelectedIfTagIsSelected(List<BewerteterAufgabentag> bewerteterAufgabentagList) {
        if (gewaehlteTags != null) {
            List<String> tagList = Arrays.asList(gewaehlteTags.split(","));
            bewerteterAufgabentagList.forEach(tag -> tag.setSelected(tagList.contains(tag.getAufgabentag().getTag())));
        }
    }

}
