package de.master.lernprogramm.domain.enums;

import lombok.Getter;

public enum AufgabenkategorieEnum {

    SOFTWAREENTWICKLUNG("SOFTWAREENTWICKLUNG");

    @Getter
    private String value;

    AufgabenkategorieEnum(String value) {
        this.value = value;
    }
}
