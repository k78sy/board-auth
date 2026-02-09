package com.green.boardauth;

import net.datafaker.Faker;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Locale;

// Slice Test (레이어 테스트) - Mybatis 테스트 용도, 오로지 mapper쪽만 빈등록

@MybatisTest
// Persistence Test는 기본적으로 H2 경량 데이터베이스를 이용한다. 딴 DB로 변경 가능. 그 기능 사용하지 않겠다! 원래 DB 쓰겠단의미
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Test는 트랜잭션으로 작동하며 테스트가 끝나면 Rollback 시킨다. 그런데 우리는 실제로 데이터 insert 해야하기 떄문에
// Rollback 기능 끌거임
@Rollback(value = false)
public class Dummy {
    @Autowired //DI 멤버필드 주입
    protected SqlSessionFactory sqlSessionFactory;

    protected Faker koFaker = new Faker(new Locale("ko")); //한글
    protected Faker enFaker = new Faker(new Locale("en")); //영어
}
