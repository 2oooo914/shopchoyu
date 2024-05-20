package com.likelion.shopchoyu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    //@NotBlank: 해당 필드가 null이 아니며, 공백 문자를 제외한 길이가 0보다 커야 함을 지정
    //@Pattern: 해당 필드 값이 지정된 정규 표현식과 일치해야 함을 지정, 여기서는 이메일과 비밀번호의 형식을 검사
    //@Size: 문자열의 길이가 지정된 범위 내에 있어야 함을 지정, 여기서는 비밀번호의 최소 길이를 8로 지정

    @NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
    public String email;

    @NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
    @Size(min = 8, message = "[ERROR] 비밀번호는 최소 8자리 이이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,64}$", message = "[ERROR] 비밀번호는 8자 이상, 64자 이하이며 특수문자 한 개를 포함해야 합니다.")
    public String password;
}