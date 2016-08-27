package com.cf.study;

import java.io.Serializable;

public class Employee implements Serializable {

    private static final long serialVersionUID = -2915718246915294218L;

    private String name;

    private transient String translatedName;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(final String translatedName) {
        this.translatedName = translatedName;
    }

}
