package com.devarsh.careeros.repository;

import com.devarsh.careeros.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByProfileIdOrderByStartDateDesc(Long profileId);

    Optional<Education> findByIdAndProfileId(Long id, Long profileId);
}