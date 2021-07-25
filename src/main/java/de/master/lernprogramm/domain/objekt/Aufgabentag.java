package de.master.lernprogramm.domain.objekt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Aufgabentag {
    private Integer aufgabentagId;
    private String tag;
    private String text;

}
