package com.conanthelibrarian.librarymanagementsystem.constants;

/**
 * Enum que representa los géneros disponibles en el sistema.
 *
 * <p>Este enum está diseñado para ser compatible con una base de datos ya existente
 * que no se puede modificar.</p>
 *
 * <h2>¿Por qué es necesario este diseño?</h2>
 * <p>En Java, los nombres de los enums no pueden contener espacios. Sin embargo,
 * en la base de datos los géneros están guardados como texto legible, por ejemplo:</p>
 *
 * <ul>
 *     <li>{@code "Science Fiction"}</li>
 *     <li>{@code "Dystopian"}</li>
 * </ul>
 *
 * <p>Por tanto, el enum necesita mantener dos representaciones:</p>
 * <ul>
 *     <li><b>Nombre interno en Java</b> (ejemplo: {@code SCIENCE_FICTION})</li>
 *     <li><b>Valor exacto en BBDD</b> (ejemplo: {@code "Science Fiction"})</li>
 * </ul>
 *
 * <p>Este enum suele usarse junto a un {@code AttributeConverter} de JPA para que
 * la conversión se realice automáticamente al guardar y leer entidades.</p>
 */
public enum Genre {

    DYSTOPIAN("Dystopian"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    CLASSIC("Classic");

    /**
     * Valor exacto con el que este género se almacena en la base de datos.
     * Ejemplo: {@code "Science Fiction"}.
     */
    private final String dataBaseValue;

    /**
     * Constructor del enum.
     *
     * @param dataBaseValue valor exacto guardado en la base de datos para este género
     */
    Genre(String dataBaseValue) {
        this.dataBaseValue = dataBaseValue;
    }

    /**
     * Devuelve el valor exacto que debe almacenarse en base de datos para este género.
     *
     * <p>Este método se usa principalmente cuando se persiste una entidad en BBDD,
     * para convertir el enum a su representación en texto.</p>
     *
     * @return valor en base de datos correspondiente a este género
     */
    public String getDataBaseValue() {
        return dataBaseValue;
    }

    /**
     * Convierte un texto leído desde la base de datos al enum correspondiente.
     *
     * <p>Este método es necesario porque el valor almacenado en la base de datos
     * (por ejemplo {@code "Science Fiction"}) no coincide con el nombre del enum
     * en Java (por ejemplo {@code SCIENCE_FICTION}).</p>
     *
     * <p>La comparación se hace ignorando mayúsculas/minúsculas para que el sistema
     * sea más robusto ante variaciones.</p>
     *
     * @param value texto leído desde la base de datos
     * @return el {@link Genre} correspondiente al texto recibido
     * @throws IllegalArgumentException si el texto no coincide con ningún género conocido
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
