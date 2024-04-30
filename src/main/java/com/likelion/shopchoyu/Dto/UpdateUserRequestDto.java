package com.likelion.shopchoyu.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //매개변수 없는 생성자를 생성해 줍니다.
@AllArgsConstructor //모든 매개변수를 받는 생성자를 생성해 줍니다.
@Getter
public class UpdateUserRequestDto {

    public String name;

    public String address;

    //업데이트(Update) 작업에서는 toEntity() 메서드의 사용이 필요하지 않거나, 업데이트 로직에 맞게 메서드를 조정해야 할 수도 있습니다.
    //업데이트는 기존 엔티티의 상태를 변경하는 것이므로, DTO를 엔티티로 변환하는 대신, 변경이 필요한 필드만 업데이트하는 방식이 일반적입니다.
}