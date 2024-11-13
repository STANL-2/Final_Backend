package stanl_2.final_backend.global.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToStringConverterConfig implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "true" : "false";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "true".equalsIgnoreCase(dbData);
    }
}
