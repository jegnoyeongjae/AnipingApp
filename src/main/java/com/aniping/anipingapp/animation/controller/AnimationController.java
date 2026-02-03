package com.aniping.anipingapp.animation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AnimationController {
    @PostMapping("/anipang")
    public ResponseEntity<UserRequest> test(@RequestBody UserRequest  userRequest) {


        System.out.println("리턴데이터: " + userRequest.toString());
        return ResponseEntity.ok(userRequest);
    }

    public static class UserRequest {
        private String test;
        private String userName;

        // 필드명과 Getter/Setter 이름을 맞춰주세요.
        public String getTest() { return test; }
        public void setTest(String test) { this.test = test; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        @Override
        public String toString() {
            return "UserRequest{" + "test='" + test + '\'' + ", userName='" + userName + '\'' + '}';
        }
    }
}
