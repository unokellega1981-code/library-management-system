package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para mapear el enum {@link Genre} a un valor String en base de datos.
 *
 * <p>Se utiliza porque la base de datos guarda géneros con un formato específico
 * (por ejemplo "Science Fiction"), mientras que en Java el enum se llama
 * {@code SCIENCE_FICTION}.</p>
 *
 * <p>Este conversor se encarga de:</p>
 * <ul>
 *     <li>Guardar el enum como texto exacto en la base de datos</li>
 *     <li>Convertir el texto de la base de datos al enum correspondiente</li>
 * </ul>
 *
 * <p>Con {@code autoApply = true}, este conversor se aplicará automáticamente
 * a cualquier campo {@link Genre} en todas las entidades.</p>
 */
@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, String> {

    /**
     * Convierte el enum a su representación en base de datos.
     *
     * @param genre valor del enum en Java
     * @return texto que se guardará en base de datos
     */
    @Override
    public String convertToDatabaseColumn(Genre genre) {
        return genre == null ? null : genre.getDataBaseValue();
    }

    /**
     * Convierte el texto leído desde base de datos al enum correspondiente.
     *
     * @param dataBaseValue texto leído desde la base de datos
     * @return enum correspondiente
     */
    @Override
    public Genre convertToEntityAttribute(String dataBaseValue) {
        return dataBaseValue == null ? null : Genre.fromDataBaseValue(dataBaseValue);
    }

}
