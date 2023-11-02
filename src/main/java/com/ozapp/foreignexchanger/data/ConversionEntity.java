package com.ozapp.foreignexchanger.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Conversion")
@Table(indexes = {@Index(name = "idx_DetectionId", columnList = "signalId, sessionId"),
        @Index(name = "idx_startTimeMsIndex", columnList = "startTimeMs"), @Index(name = "idx_stopTimeMsIndex", columnList = "stopTimeMs"),
        @Index(name = "idx_startFrequencyHzIndex", columnList = "startFrequencyHz"),
        @Index(name = "idx_StopFrequencyHzIndex", columnList = "stopFrequencyHz"),
        @Index(name = "idx_CurrentIdentityIndex", columnList = "currentIdentity")})
public class ConversionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long transactionId;

    @Column
    private long date;

    @Column
    private double sourceCurrency;

    @Column
    private double targetCurrency;

    @Column
    private double sourceAmount;

    @Column
    private double targetAmount;

}
