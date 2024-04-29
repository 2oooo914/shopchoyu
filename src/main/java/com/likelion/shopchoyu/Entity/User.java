package com.likelion.shopchoyu.Entity;

import com.likelion.shopchoyu.Dto.UpdateOrderRequestDto;
import com.likelion.shopchoyu.Dto.UpdateUserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id //각각의 객체를 구별
    @GeneratedValue(strategy = GenerationType.AUTO) //1부터 id값 하나씩 증가
    private Long id;

    @Column
    public String name;

    @Column
    public String userId;

    @Column
    public String password;

    @Column
    public String address;

    //제어와 유지보수에 용이하기 위해 Setter를 사용하지 않는다
    public void update(UpdateUserRequestDto userRequestDto) { // User Entity의 정보를 업데이트
        name = userRequestDto.getName();
        address = userRequestDto.getAddress();
    } //Dto 값을 User Entity의 해당 필드에 할당 -> 새로운 값으로 업데이트
}
