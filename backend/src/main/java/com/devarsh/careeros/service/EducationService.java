package com.devarsh.careeros.service;

import com.devarsh.careeros.dto.EducationRequest;
import com.devarsh.careeros.dto.EducationResponse;

import java.util.List;

public interface EducationService {

    List<EducationResponse> getMyEducations();

    EducationResponse addEducation(EducationRequest request);

    EducationResponse updateEducation(Long id, EducationRequest request);

    void deleteEducation(Long id);
}