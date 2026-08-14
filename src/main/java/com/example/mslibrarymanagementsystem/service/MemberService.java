package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.MemberRequest;
import com.example.mslibrarymanagementsystem.dto.response.BookResponse;
import com.example.mslibrarymanagementsystem.dto.response.MemberResponse;
import com.example.mslibrarymanagementsystem.entity.MemberEntity;
import com.example.mslibrarymanagementsystem.exceptions.MemberNotFoundException;
import com.example.mslibrarymanagementsystem.mapper.BookMapper;
import com.example.mslibrarymanagementsystem.mapper.MemberMapper;
import com.example.mslibrarymanagementsystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(MemberRequest memberRequest) {
        var member = MemberMapper.toEntity(memberRequest);
        memberRepository.save(member);
    }

    public Page<MemberResponse> getMembers(PageCriteria pageCriteria) {
        Sort.Direction direction = Sort.Direction.fromString(pageCriteria.getDirection());
        var pageable = PageRequest.of(
                pageCriteria.getPage(),
                pageCriteria.getCount(),
                Sort.by(direction, pageCriteria.getSortBy()));
        Page<MemberEntity> members = memberRepository.findAll(pageable);
        return members.map(MemberMapper::toResponse);
    }

    public MemberResponse getMemberById(Long id) {
        var member = fetchMemberIfExists(id);
        return MemberMapper.toResponse(member);
    }

    public List<BookResponse> getBorrowedBooksByMemberId(Long id) {
        var member = fetchMemberIfExists(id);
        return member.getBorrowedBooks()
                .stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    public MemberResponse updateMember(Long id, MemberRequest memberRequest) {
        var member = fetchMemberIfExists(id);
        MemberMapper.updateMember(member, memberRequest);
        var updatedMember = memberRepository.save(member);
        return MemberMapper.toResponse(updatedMember);
    }

    public void deleteMember(Long id) {
        var member = fetchMemberIfExists(id);
        memberRepository.delete(member);
    }

    private MemberEntity fetchMemberIfExists(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException("Member not found with id: " + id));
    }
}