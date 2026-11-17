package com.devarsh.careeros.service;

import com.devarsh.careeros.dto.ProfileRequest;
import com.devarsh.careeros.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse getMyProfile();

    ProfileResponse updateMyProfile(ProfileRequest request);
}