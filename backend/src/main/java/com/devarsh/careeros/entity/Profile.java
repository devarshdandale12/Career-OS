package com.devarsh.careeros.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class Profile extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 200)
    private String headline;

    @Column(length = 100)
    private String targetRole;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String location;

    @Column(length = 1000)
    private String summary;

    @Column
    private String linkedinUrl;
    @Column
    private String githubUrl;
    @Column
    private String portfolioUrl;
    @Column
    private String profilePhotoUrl;


}
