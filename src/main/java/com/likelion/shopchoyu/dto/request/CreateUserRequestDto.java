package com.likelion.shopchoyu.dto.request;

import com.likelion.shopchoyu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor //매개변수 없는 생성자를 생성해 줍니다.
@AllArgsConstructor //모든 매개변수를 받는 생성자를 생성해 줍니다.
@Getter //Getter 메서드를 자동으로 생성해줍니다.
public class CreateUserRequestDto {

    public String name;

    public String id;

    public String password;

    public String address;

    //클래스의 인스턴스 변수들을 사용하여 Order 엔티티의 인스턴스를 생성하고 반환하는 역할
    //생성(Create) 작업에서는 toEntity() 메서드를 통해 DTO의 모든 정보를 새 엔티티 객체에 매핑
    public User toEntity(PasswordEncoder passwordEncoder){
        //Password Encoder 통해 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder()
                .name(name)
                .email(id)
                .password(encodedPassword) //비밀번호 저장
                .address(address)
                .build();
    }

}