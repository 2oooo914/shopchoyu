package com.likelion.shopchoyu.Dto;

        import com.likelion.shopchoyu.Entity.User;
        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Getter;
        import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    public Long userId;

    public String name;

    public String address;

    //User 엔티티 객체를 매개변수로 받아, 해당 엔티티의 데이터를 기반으로 UserResponseDto 객체를 생성하고 반환하는 정적 메서드
    public static UserResponseDto from(User user){
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .address(user.getAddress())
                .build();
    }

}
