package com.likelion.shopchoyu.repository;

import com.likelion.shopchoyu.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    //JpaRepository를 확장하여, 기본적인 CRUD(Create, Read, Update, Delete) 작업을 위한 메소드를 상속
    //이메일을 기반으로 토큰을 찾는 사용자 정의 메소드 findByEmail을 추가로 정의

    //<Token, Long>: Token은 엔티티 클래스이며, Long은 엔티티의 기본 키 타입
    Optional<Token> findByEmail(String email);
    //이메일을 인자로 받아 해당 이메일과 일치하는 Token 엔티티를 찾아 Optional<Token> 타입으로 반환
    //Optional은 결과가 없을 경우를 안전하게 처리하기 위해 사용
}