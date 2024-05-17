package com.avila.zecompany.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Builder @Getter
@Entity @Table(name = "geo_data")
public class GeoData {

    @Converter @Component
    private record DataConverter(@Autowired ObjectMapper objectMapper) implements AttributeConverter<List<?>, String> {
        @Override @SneakyThrows
        public String convertToDatabaseColumn(List<?> attribute) {
            return objectMapper.writeValueAsString(attribute);
        }

        @Override @SneakyThrows
        public List<?> convertToEntityAttribute(@NotNull String data) {
            return objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
        }
    }

    @Getter public enum GeoType {
        ADDRESS("Point"), AREA("MultiPolygon");
        private final String type;
        GeoType(String type) {
            this.type = type;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GeoType type;

    @Convert(converter = DataConverter.class)
    @Column(name = "coordinates", nullable = false)
    private List<?> coordinates;
}