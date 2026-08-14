package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.MemberRequest;
import com.example.mslibrarymanagementsystem.dto.response.MemberResponse;
import com.example.mslibrarymanagementsystem.entity.MemberEntity;

public class MemberMapper {
    public static MemberEntity toEntity(MemberRequest memberRequest) {
        return MemberEntity.builder()
                .firstName(memberRequest.getFirstName())
                .lastName(memberRequest.getLastName())
                .email(memberRequest.getEmail())
                .build();
    }

    public static MemberResponse toResponse(MemberEntity memberEntity) {
        return MemberResponse.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .firstName(memberEntity.getFirstName())
                .lastName(memberEntity.getLastName())
                .build();
    }

    public static void updateMember(MemberEntity member, MemberRequest request) {
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
    }
}