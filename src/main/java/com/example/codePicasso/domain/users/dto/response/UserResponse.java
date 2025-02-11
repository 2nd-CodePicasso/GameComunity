package com.example.codePicasso.domain.users.dto.response;

import com.example.codePicasso.domain.users.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(String loginId, String nickname) {
}
