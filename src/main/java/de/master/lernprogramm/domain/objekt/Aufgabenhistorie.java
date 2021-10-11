package de.master.lernprogramm.domain.objekt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Slf4j
public class Aufgabenhistorie {

    private LocalDate datum;
    private Double bewertungsveraenderung;
    private Aufgabe aufgabe;

    public Double getBewertungNachFormel() {
        log.info("Aufgabenhistorie, datum {}, Aufgabe {}", datum, aufgabe);
        if (aufgabe == null || aufgabe.getBewertung() == null) {
            return 0.0;
        }
        long daysBetween = ChronoUnit.DAYS.between(datum, LocalDate.now());
        return (aufgabe.getBewertung() + bewertungsveraenderung) / (int) ((daysBetween < 1) ? 1 : daysBetween);
    }
}
