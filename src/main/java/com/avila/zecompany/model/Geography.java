package com.avila.zecompany.model;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data @Builder
public class Geography {

    @Getter public enum GeoType {
        ADDRESS("Point"), AREA("MultiPolygon");
        private final String type;
        GeoType(String type) {
            this.type = type;
        }
    }

    private Long id;
    private Partner partnerId;
    private GeoType type;
    private List<?> coordinates;
}