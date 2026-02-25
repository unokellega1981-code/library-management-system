package com.conanthelibrarian.librarymanagementsystem.constants;

/**
 * Enumerado que representa los géneros literarios disponibles en el sistema.
 *
 * <p>
 * Cada valor del enum contiene el texto exacto almacenado en la base de datos,
 * lo que permite desacoplar el nombre interno del enum del valor persistido.
 * </p>
 *
 * <p>
 * Incluye un método auxiliar para convertir el valor almacenado en la base de datos
 * en su correspondiente valor del enum.
 * </p>
 */
public enum Genre {

    DYSTOPIAN("Dystopian"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    CLASSIC("Classic");

    /**
     * Valor exacto almacenado en la base de datos.
     */
    private final String dataBaseValue;

    /**
     * Constructor del enumerado.
     *
     * @param dataBaseValue valor asociado en la base de datos
     */
    Genre(String dataBaseValue) {
        this.dataBaseValue = dataBaseValue;
    }

    /**
     * Devuelve el valor almacenado en la base de datos para el género.
     *
     * @return valor en formato texto usado en la base de datos
     */
    public String getDataBaseValue() {
        return dataBaseValue;
    }

    /**
     * Convierte un valor de texto proveniente de la base de datos
     * en su correspondiente valor del enumerado.
     *
     * @param value texto almacenado en la base de datos
     * @return género correspondiente
     * @throws IllegalArgumentException si el género no existe
     */
    public static Genre fromDataBaseValue(String value) {
        for (Genre genre : values()) {
            if (genre.dataBaseValue.equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Género desconocido: " + value);
    }
}
