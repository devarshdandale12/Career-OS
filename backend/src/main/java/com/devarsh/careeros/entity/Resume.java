package com.devarsh.careeros.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "resumes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_resumes_profile_title",
                columnNames = {"profile_id", "title"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Resume extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 100)
    private String targetRole;

    @Column(length = 1000)
    private String summaryOverride;

    @Column(nullable = false, length = 50)
    private String templateName = "classic";

    @Column(nullable = false)
    private boolean defaultResume = false;

    @Column(nullable = false)
    private boolean showPhoto = false;
}