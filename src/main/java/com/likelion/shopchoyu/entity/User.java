package com.likelion.shopchoyu.entity;

import com.likelion.shopchoyu.dto.request.UpdateUserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id //각각의 객체를 구별
    @GeneratedValue(strategy = GenerationType.AUTO) //1부터 id값 하나씩 증가
    @Column(name = "userId")
    private Long userId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "id", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column
    private String roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orderList;

    //제어와 유지보수에 용이하기 위해 Setter를 사용하지 않는다
    public void update(UpdateUserRequestDto updateUserRequestDto) {
        if(updateUserRequestDto.getName() != null) {
            this.name = updateUserRequestDto.getName();
        }
        if(updateUserRequestDto.getAddress() != null) {
            this.address = updateUserRequestDto.getAddress();
        }
    }
    //Dto 값을 User Entity의 해당 필드에 할당 -> 새로운 값으로 업데이트
    // UpdateUserRequestDto 객체로부터 받은 값으로 name, address 필드를 업데이트
}
