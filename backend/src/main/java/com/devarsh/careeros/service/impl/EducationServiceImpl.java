package com.devarsh.careeros.service.impl;

import com.devarsh.careeros.dto.EducationRequest;
import com.devarsh.careeros.dto.EducationResponse;
import com.devarsh.careeros.entity.Education;
import com.devarsh.careeros.entity.Profile;
import com.devarsh.careeros.exception.BadRequestException;
import com.devarsh.careeros.exception.ResourceNotFoundException;
import com.devarsh.careeros.repository.EducationRepository;
import com.devarsh.careeros.security.CurrentUserService;
import com.devarsh.careeros.service.EducationService;
import com.devarsh.careeros.util.TextUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final CurrentUserService currentUserService;

    public EducationServiceImpl(EducationRepository educationRepository,
                                CurrentUserService currentUserService) {
        this.educationRepository = educationRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<EducationResponse> getMyEducations() {
        Profile profile = currentUserService.getCurrentProfile();

        List<Education> educations =
                educationRepository.findByProfileIdOrderByStartDateDesc(profile.getId());

        List<EducationResponse> result = new ArrayList<>();
        for (Education education : educations) {
            result.add(toResponse(education));
        }
        return result;
    }

    @Override
    public EducationResponse addEducation(EducationRequest request) {
        checkDates(request);
        Profile profile = currentUserService.getCurrentProfile();

        Education education = new Education();
        education.setProfile(profile);
        copyFields(request, education);

        Education saved = educationRepository.save(education);
        return toResponse(saved);
    }

    @Override
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        checkDates(request);
        Education education = findMyEducation(id);
        copyFields(request, education);

        Education saved = educationRepository.save(education);
        return toResponse(saved);
    }

    @Override
    public void deleteEducation(Long id) {
        Education education = findMyEducation(id);
        educationRepository.delete(education);
    }

    // The ownership check: only finds the education if it belongs to MY profile
    private Education findMyEducation(Long id) {
        Profile profile = currentUserService.getCurrentProfile();

        return educationRepository.findByIdAndProfileId(id, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Education not found"));
    }

    // Business rule: the end date cannot come before the start date
    private void checkDates(EducationRequest request) {
        if (request.endDate() != null && request.endDate().isBefore(request.startDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }
    }

    // Copies the cleaned request values into the entity
    private void copyFields(EducationRequest request, Education education) {
        education.setInstitution(request.institution().trim());
        education.setDegree(request.degree().trim());
        education.setFieldOfStudy(TextUtil.cleanOrNull(request.fieldOfStudy()));
        education.setStartDate(request.startDate());
        education.setEndDate(request.endDate());
        education.setMarks(TextUtil.cleanOrNull(request.marks()));
    }

    private EducationResponse toResponse(Education education) {
        return new EducationResponse(
                education.getId(),
                education.getInstitution(),
                education.getDegree(),
                education.getFieldOfStudy(),
                education.getStartDate(),
                education.getEndDate(),
                education.getMarks()
        );
    }
}