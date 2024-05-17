package com.avila.zecompany.dto;
import lombok.Builder;
import java.util.List;

@Builder
public record GeoDataDTO(String type, List<?> coordinates) { }