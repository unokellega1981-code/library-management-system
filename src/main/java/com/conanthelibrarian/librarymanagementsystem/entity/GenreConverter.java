package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA para transformar el enum Genre
 * en el valor de texto almacenado en la base de datos
 * y viceversa.
 *
 * <p>
 * Permite que la base de datos almacene valores como
 * "Science Fiction" mientras que en el código se utiliza
 * Genre.SCIENCE_FICTION.
 * </p>
 */
@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, String> {

    /**
     * Convierte el enum a su valor en la base de datos.
     */
    @Override
    public String convertToDatabaseColumn(Genre genre) {
        return genre == null ? null : genre.getDataBaseValue();
    }

    /**
     * Convierte el valor almacenado en la base de datos
     * al enum correspondiente.
     */
    @Override
    public Genre convertToEntityAttribute(String dataBaseValue) {
        return dataBaseValue == null ? null : Genre.fromDataBaseValue(dataBaseValue);
    }
}
