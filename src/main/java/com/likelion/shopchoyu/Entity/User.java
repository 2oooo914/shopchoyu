package com.likelion.shopchoyu.Entity;

import com.likelion.shopchoyu.Dto.UpdateOrderRequestDto;
import com.likelion.shopchoyu.Dto.UpdateUserRequestDto;
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
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orderList;

    //제어와 유지보수에 용이하기 위해 Setter를 사용하지 않는다
    public void update(UpdateUserRequestDto userRequestDto) { // User Entity의 정보를 업데이트
        name = userRequestDto.getName();
        address = userRequestDto.getAddress();
    }
    //Dto 값을 User Entity의 해당 필드에 할당 -> 새로운 값으로 업데이트
    // UpdateUserRequestDto 객체로부터 받은 값으로 name, address 필드를 업데이트
}
