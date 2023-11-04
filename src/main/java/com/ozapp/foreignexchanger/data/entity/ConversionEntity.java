package com.ozapp.foreignexchanger.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Conversion")
@Table(indexes = {@Index(name = "idx_TransactionIdIndex", columnList = "id"), @Index(name = "idx_DateIndex", columnList = "date")})
public class ConversionEntity {
    @Id
    @UuidGenerator
    @Column(unique = true, nullable = false)
    private String id;

    @Column
    private long date;

    @Column
    private String sourceCurrency;

    @Column
    private double sourceAmount;

    @Column
    private String targetCurrency;

    @Column
    private double targetAmount;

    @Column
    private double exchangeRate;

}
