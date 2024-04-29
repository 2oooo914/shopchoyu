package com.likelion.shopchoyu.Service;

import com.likelion.shopchoyu.Dto.CreateUserRequestDto;
import com.likelion.shopchoyu.Dto.UpdateUserRequestDto;
import com.likelion.shopchoyu.Dto.UserResponseDto;
import com.likelion.shopchoyu.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public void createUser(CreateUserRequestDto userRequestDto){
        User user = userRequestDto.toEntity();
    }

    public UserResponseDto getUser(Long userId){
        //DB에서 가져오기
        User user = new User();
        return UserResponseDto.from(user);
    }

    public void updateUser(UpdateUserRequestDto userRequestDto){
        //DB에서 가져오기
        User user = new User();
        user.update(userRequestDto);
    }

    public void deleteUser(Long userId){

    }
}


