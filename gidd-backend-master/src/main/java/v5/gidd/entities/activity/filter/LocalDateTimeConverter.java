package v5.gidd.entities.activity.filter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Implemented for compatibility reasons with database.
 */

@Converter(autoApply = true) // this makes it to apply anywhere there is a need
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp value) {
        return value.toLocalDateTime();
    }
}

