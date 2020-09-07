package com.wonya.demo.service;

import com.wonya.demo.repository.MemberRepository;
import com.wonya.demo.utils.RegexTest;
import com.wonya.demo.utils.Security;
import com.wonya.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService<Users, Long> {

    private final MemberRepository memberRepository;
    private final Security security;
    private final RegexTest regexTest;

    public int join(Users user){
        if(regexTest.email(user.getId())) return 1;
        if(regexTest.password(user.getPw())) return 2;

        user.setPw(security.sha256(user.getPw()));

        long count = memberRepository.countById(user.getId());
        if(count > 0) {
            return 3;
        } else {
            user = memberRepository.save(user);
        }
        if(user.getSeq() < 1) {
            return 4;
        }
        return 200;
    }

    public int login(Users user) {
        user.setPw(security.sha256(user.getPw()));
        user = memberRepository.findByIdAndPw(user.getId(), user.getPw());

        System.out.printf("users = " + user.toString());
        String accessToken = security.generateJwtToken(user);
        System.out.printf("token = " + accessToken);
//        if(user.getSeq() > 0) {
//            user.setLastDate(new Date());
//            memberRepository.save(user);
//        } else {
//            return 1;
//        }

        return 200;
    }
    
    public String info() {

        return "hello";
    }

}
