package com.devarsh.careeros.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
public class Certification extends ProfileOwnedEntity{


    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String issuingOrganization;

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate expiryDate;
    @Column(length = 100)
    private String credentialId;

    @Column(length = 100)
    private String credentialUrl;
}
