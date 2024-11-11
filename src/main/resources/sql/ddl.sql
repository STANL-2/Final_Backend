-- SET foreign_key_checks = 0;
--
--
-- -- 테이블 삭제 순서
-- DROP TABLE IF EXISTS MEMBER;
-- DROP TABLE IF EXISTS CENTER;
--
-- -- 테이블 생성
-- CREATE TABLE member (
--     member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     member_active BOOLEAN NOT NULL,
--     member_created_at TIMESTAMP,
--     member_email VARCHAR(255),
--     member_login_id VARCHAR(50) UNIQUE,
--     member_name VARCHAR(100),
--     member_password VARCHAR(255),
--     member_phone VARCHAR(15),
--     member_role VARCHAR(50),
--     member_updated_at TIMESTAMP
-- );
--
-- CREATE TABLE center (
-- #     CENT_ID VARCHAR(255) NOT NULL PRIMARY KEY,
--     CENT_ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
--     CENT_NAME VARCHAR(255) NOT NULL,
--     CENT_ADR VARCHAR(255) NOT NULL,
--     CENT_PHO VARCHAR(255) NOT NULL,
--     CENT_MEM_CNT INTEGER NOT NULL,
--     CENT_OPR_AT VARCHAR(255) NOT NULL,
--     CREATED_AT CHAR(19) NOT NULL,
--     UPDATED_AT CHAR(19) NOT NULL DEFAULT CREATED_AT,
--     DELETED_AT CHAR(19) NULL,
--     ACTIVE BOOLEAN NOT NULL
-- );
--
-- -- 제약 조건 추가
--
--
-- -- 더미 데이터 삽입
--
