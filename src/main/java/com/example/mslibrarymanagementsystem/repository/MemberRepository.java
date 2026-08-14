package com.example.mslibrarymanagementsystem.repository;

import com.example.mslibrarymanagementsystem.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
}