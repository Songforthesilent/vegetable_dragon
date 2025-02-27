package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
// client 측에서 JSON 형태로 데이터를 보내면
// UserForm 이라는 자바 클래스 객체로 만들어준다.

// 그리고 우리는 UserForm 객체를, User에서 뽑아서 쓴다.
@Getter
@Setter
public class UserForm {

    @NotEmpty(message = "이메일은 필수입니다.")
    @Size(max = 100, message = "이메일은 100자리를 초과할 수 없습니다.")
    private String email;

    @NotEmpty(message = "이름은 필수입니다.")
    @Size(max = 20, message = "이름은 20자리를 초과할 수 없습니다.")
    private String userName;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(max = 100, message = "비밀번호는 100자리를 초과할 수 없습니다.")
    private String password;
}
