package com.avila.zecompany.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor @NoArgsConstructor @Getter
@Entity @Table(name = "partners")
public class Partner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trading_name", nullable = false)
    private String tradingName;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "document", nullable = false, unique = true)
    private String document;
}