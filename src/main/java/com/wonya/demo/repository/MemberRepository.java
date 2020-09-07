package com.wonya.demo.repository;

import com.wonya.demo.vo.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Users, Long> {

    long countById(String id);

    Users findByIdAndPw(String id, String pw);
}
