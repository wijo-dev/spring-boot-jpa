package com.wonya.demo.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegexTest {

    public boolean email(String email) {
        String pattern = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
        return !(Pattern.matches(pattern, email));
    }

    public boolean password(String pw) {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{12,}$";
        return !(Pattern.matches(pattern, pw));
    }
}
