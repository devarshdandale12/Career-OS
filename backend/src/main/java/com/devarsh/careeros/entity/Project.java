package com.devarsh.careeros.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project extends ProfileOwnedEntity{

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String techStack;

    @Column
    private String projectUrl;
    @Column
    private String githubUrl;

    private LocalDate startDate;

    private LocalDate endDate;


}
