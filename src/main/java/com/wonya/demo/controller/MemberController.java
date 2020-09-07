package com.wonya.demo.controller;

import com.wonya.demo.repository.MemberRepository;
import com.wonya.demo.service.MemberService;
import com.wonya.demo.vo.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @RequestMapping(value = "join", method = RequestMethod.POST)
    public Boolean join(@RequestBody Users user) {
        if(memberService.join(user) == 200) return true;
        else return false;
    }

    @RequestMapping("/login")
    public String login(@RequestBody Users user) {
        if(memberService.login(user) == 200) return "token";
        else return "Login failed";
    }

    @RequestMapping("/test")
    public String getTest() {

        return "test";
    }
}
