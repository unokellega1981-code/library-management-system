package com.conanthelibrarian.librarymanagementsystem.constants;

public enum Genre {

    DYSTOPIAN("Dystopian"), FANTASY("Fantasy"), SCIENCE_FICTION("Science Fiction"), CLASSIC("Classic");

    private final String dataBaseValue;

    Genre(String dataBaseValue) {
        this.dataBaseValue = dataBaseValue;
    }

    public String getDataBaseValue() {
        return dataBaseValue;
    }

    public static Genre fromDataBaseValue(String value) {
        for (Genre genre : values()) {
            if (genre.dataBaseValue.equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Género desconocido: " + value);
    }
}
