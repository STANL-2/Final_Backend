SET foreign_key_checks = 0;


-- 테이블 삭제 순서
DROP TABLE IF EXISTS MEMBER;

-- 테이블 생성
CREATE TABLE member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_active BOOLEAN NOT NULL,
    member_created_at TIMESTAMP,
    member_email VARCHAR(255),
    member_login_id VARCHAR(50) UNIQUE,
    member_name VARCHAR(100),
    member_password VARCHAR(255),
    member_phone VARCHAR(15),
    member_role VARCHAR(50),
    member_updated_at TIMESTAMP
);


-- 제약 조건 추가


-- 더미 데이터 삽입

