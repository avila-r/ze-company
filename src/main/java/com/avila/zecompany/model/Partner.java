package com.avila.zecompany.model;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Partner {
    private Long id;
    private String tradingName;
    private String ownerName;
    private Long document;
}