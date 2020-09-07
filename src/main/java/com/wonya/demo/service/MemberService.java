package com.wonya.demo.service;

import com.wonya.demo.vo.Users;

import java.security.NoSuchAlgorithmException;

public interface MemberService<T, ID> {

    //id, pw, 이름
    //id는 반드시 이메일
    //pwd 는 영어 대문, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열
    //비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다.
    int join(Users user);

    // 로그인 URL login
    // ID, 비밀번호 입력받아 로그인 처리
    // id와 비밀번호가 가입되어 있는 회원의 정보와 일치하면 로그인이 되었따는 응답으로 AcceeToken을 제공
    int login(Users user);

    // 회원정보 조회 URL는 다음과 깥습니다.
    // 로그인이 된 사용자에 대해서는 사용자이름, Email, 직전 로그인 일시에 제공합니다.
    // (화면에는 다음과 같이 표시됩니다.)
    // ex) 홍길동(wonya@bithumbcorp.com) 님, 환영합니다.
    //    (직전로그인 : 2020/07/01 00:00:00.121412)
    // 로그인이 안된 사용자는 HTTP Status Code 를 401 (Unauthorized) 로 응답합니다.
    String info();

}
