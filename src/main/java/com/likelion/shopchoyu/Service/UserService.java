package com.likelion.shopchoyu.Service;

import com.likelion.shopchoyu.Dto.CreateUserRequestDto;
import com.likelion.shopchoyu.Dto.OrderResponseDto;
import com.likelion.shopchoyu.Dto.UpdateUserRequestDto;
import com.likelion.shopchoyu.Dto.UserResponseDto;
import com.likelion.shopchoyu.Entity.Order;
import com.likelion.shopchoyu.Entity.User;
import com.likelion.shopchoyu.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class UserService {
    // 의존성 주입을 위한 final 필드 선언
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto){
        User user = createUserRequestDto.toEntity(); //Dto를 Entity로 변환
        try{
            user = userRepository.save(user); //사용자 저장
            return UserResponseDto.from(user);
        } catch (Exception e){
            log.error("{} 저장 실패", user.getUserId(), e);
        }
        return null;
    }

    public UserResponseDto getUser(Long userId) {
        //DB에서 가져오기
        try{
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){ //존재 하면
                User findUser = user.get();
                return UserResponseDto.from(findUser); //UserResponseDto로 변환하여 반환
            } else{
                // 사용자가 없을 경우
                log.info("사용자가 존재하지 않음: {}", userId);
                return null;
            }
        } catch (Exception e){
            log.error("{} 사용자 가져오기 실패", userId, e);
            return null;
        }
    }

    @Transactional //데이터 변경이 일어나므로, 클래스 레벨에서 설정한 readOnly=true를 오버라이드
    public UserResponseDto updateUser(Long userId, UpdateUserRequestDto updateUserRequestDto){
        //DB에서 가져오기
        try{
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){ //조회한 후 존재하면
                User findUser = user.get();
                findUser.update(updateUserRequestDto); //사용자 정보를 업데이트
                User updateUser = userRepository.save(findUser);
                return UserResponseDto.from(updateUser); // 수정된 사용자 정보를 반환
            } else {
                // 사용자가 없을 경우
                log.info("{} 사용자가 존재하지 않음", userId);
                return null;
            }
        } catch (Exception e){
            log.error("{} 사용자 수정 실패", userId, e);
            return null;
        }
    }

    public void deleteUser(Long userId){
        try{
            userRepository.deleteById(userId);
        } catch (Exception e){
            log.error("{} 사용자 삭제 실패", userId, e);
        }

    }
}


