package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, String> {

    @Override
    public String convertToDatabaseColumn(Genre genre) {
        return genre == null ? null : genre.getDataBaseValue();
    }

    @Override
    public Genre convertToEntityAttribute(String dataBaseValue) {
        return dataBaseValue == null ? null : Genre.fromDataBaseValue(dataBaseValue);
    }
}
