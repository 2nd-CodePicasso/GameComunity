package com.example.codePicasso.domain.post.dto.response;

import com.querydsl.core.Tuple;
import lombok.Builder;

import java.util.List;

@Builder
public record PostPopularListResponse (
        List<PostPopularResponse> postList
){

}
