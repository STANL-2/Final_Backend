# -- 외래 키 제약 조건 비활성화
# SET FOREIGN_KEY_CHECKS = 0;
#
# -- 자식 테이블부터 삭제
# DROP TABLE IF EXISTS tb_sales_history;
# DROP TABLE IF EXISTS tb_update_history;
# DROP TABLE IF EXISTS tb_product_option;
# DROP TABLE IF EXISTS tb_alarm;
# DROP TABLE IF EXISTS tb_schedule;
# DROP TABLE IF EXISTS tb_file;
# DROP TABLE IF EXISTS tb_promotion;
# DROP TABLE IF EXISTS tb_evaluation;
# DROP TABLE IF EXISTS tb_purchase_order;
# DROP TABLE IF EXISTS tb_problem;
# DROP TABLE IF EXISTS tb_order;
# DROP TABLE IF EXISTS tb_notice;
# DROP TABLE IF EXISTS tb_contract;
# DROP TABLE IF EXISTS tb_customer_info;
# DROP TABLE IF EXISTS tb_member_role;
# DROP TABLE IF EXISTS tb_member;
# DROP TABLE IF EXISTS tb_center;
# DROP TABLE IF EXISTS tb_family;
# DROP TABLE IF EXISTS tb_education;
# DROP TABLE IF EXISTS tb_certification;
# DROP TABLE IF EXISTS tb_career;
# DROP TABLE IF EXISTS tb_product;
# DROP TABLE IF EXISTS tb_organization_chart;

# -- 조직 관련 테이블 생성
# CREATE TABLE tb_organization_chart
# (
#     ORG_CHA_ID    VARCHAR(255) NOT NULL,
#     ORG_CHA_NAME  VARCHAR(255) NOT NULL,
#     ORG_CHA_DEPTH INT          NOT NULL,
#     PRIMARY KEY (ORG_CHA_ID)
# );
#
# CREATE TABLE tb_center
# (
#     CENT_ID      VARCHAR(255) NOT NULL,
#     CENT_NAME    VARCHAR(255) NOT NULL,
#     CENT_ADR     VARCHAR(255) NOT NULL,
#     CENT_PHO     VARCHAR(255) NOT NULL,
#     CENT_MEM_CNT INT          NOT NULL,
#     CREATED_AT   CHAR(19)     NOT NULL,
#     UPDATED_AT   CHAR(19)     NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
#     DELETED_AT   CHAR(19)     NULL,
#     ACTIVE       BOOLEAN      NOT NULL,
#     CENT_OPR_AT  VARCHAR(255) NOT NULL,
#     PRIMARY KEY (CENT_ID)
# );
#
# CREATE TABLE tb_member
# (
#     MEM_ID        VARCHAR(255) NOT NULL,
#     MEM_LOGIN_ID  VARCHAR(255) NOT NULL,
#     MEM_PWD       VARCHAR(255) NOT NULL,
#     MEM_NAME      VARCHAR(255) NOT NULL,
#     MEM_EMA       VARCHAR(255) NOT NULL,
#     MEM_AGE       INT          NOT NULL,
#     MEM_SEX       VARCHAR(255) NOT NULL DEFAULT 'FEMALE',
#     MEM_IDEN_NO   VARCHAR(255) NOT NULL,
#     MEM_PHO       VARCHAR(255) NOT NULL,
#     MEM_EMER_PHO  VARCHAR(255) NULL,
#     MEM_ADR       VARCHAR(255) NOT NULL,
#     MEM_NOTE      VARCHAR(255) NULL,
#     MEM_POS       VARCHAR(255) NOT NULL DEFAULT 'INTERN',
#     MEM_GRD       VARCHAR(255) NOT NULL DEFAULT 'High School',
#     MEM_JOB_TYPE  VARCHAR(255) NOT NULL,
#     MEM_MIL       VARCHAR(255) NOT NULL DEFAULT 'exemption',
#     MEM_BANK_NAME VARCHAR(255) NULL,
#     MEM_ACC       VARCHAR(255) NULL,
#     CREATED_AT    CHAR(19)     NOT NULL,
#     UPDATED_AT    CHAR(19)     NOT NULL,
#     DELETED_AT    CHAR(19)     NULL,
#     ACTIVE        BOOLEAN      NOT NULL DEFAULT TRUE,
#     CENTER_ID     VARCHAR(255) NOT NULL,
#     ORG_CHA_ID    VARCHAR(255) NOT NULL,
#     PRIMARY KEY (MEM_ID),
#     FOREIGN KEY (CENTER_ID) REFERENCES tb_center (CENT_ID),
#     FOREIGN KEY (ORG_CHA_ID) REFERENCES tb_organization_chart (ORG_CHA_ID)
# );
#
# CREATE TABLE tb_member_role
# (
#     MEM_ROL_ID   VARCHAR(255) NOT NULL,
#     MEM_ROL_NAME VARCHAR(255) NOT NULL DEFAULT '영업 사원',
#     MEM_ID       VARCHAR(255) NOT NULL,
#     PRIMARY KEY (MEM_ROL_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_customer_info
# (
#     CUST_ID       VARCHAR(255) NOT NULL,
#     CUST_NAME     VARCHAR(255) NOT NULL,
#     CUST_AGE      INT          NOT NULL,
#     CUST_SEX      VARCHAR(255) NOT NULL DEFAULT 'FEMALE',
#     CUST_PHO      VARCHAR(255) NOT NULL,
#     CUST_EMER_PHO VARCHAR(255) NOT NULL,
#     CUST_EMA      VARCHAR(255) NOT NULL,
#     ACTIVE        BOOLEAN      NOT NULL DEFAULT TRUE,
#     MEM_ID        VARCHAR(255) NOT NULL,
#     PRIMARY KEY (CUST_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# -- 부모 테이블 생성
# CREATE TABLE tb_product
# (
#     PROD_ID     VARCHAR(255) NOT NULL,
#     PROD_SER_NO VARCHAR(255) NOT NULL,
#     PROD_COST   INT          NOT NULL,
#     PROD_NAME   VARCHAR(255) NOT NULL,
#     PROD_STCK   INT          NOT NULL DEFAULT 0,
#     CREATED_AT  CHAR(19)     NOT NULL,
#     UPDATED_AT  CHAR(19)     NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
#     DELETED_AT  CHAR(19)     NULL,
#     ACTIVE      BOOLEAN      NOT NULL DEFAULT TRUE,
#     PRIMARY KEY (PROD_ID, PROD_SER_NO)
# );
#
# CREATE TABLE tb_contract
# (
#     CONR_ID            VARCHAR(255) NOT NULL,
#     CONR_NAME          VARCHAR(255) NOT NULL,
#     CONR_CUST_NAME     VARCHAR(255) NOT NULL,
#     CONR_CUST_IDEN_NO  VARCHAR(255) NOT NULL,
#     CONR_CUST_ADR      VARCHAR(255) NOT NULL,
#     CONR_CUST_EMA      VARCHAR(255) NOT NULL,
#     CONR_CUST_PHO      VARCHAR(255) NOT NULL,
#     CONR_COMP_NAME     VARCHAR(255) NULL,
#     CONR_CUST_CLA      VARCHAR(255) NOT NULL DEFAULT 'PERSONAL',
#     CONR_CUST_PUR_COND VARCHAR(255) NOT NULL DEFAULT 'CASH',
#     CONR_SERI_NUM      VARCHAR(255) NOT NULL,
#     CONR_SELE_OPTI     VARCHAR(255) NOT NULL,
#     CONR_DOWN_PAY      INT          NOT NULL,
#     CONR_INTE_PAY      INT          NOT NULL,
#     CONR_REM_PAY       INT          NOT NULL,
#     CONR_CONS_PAY      INT          NOT NULL,
#     CONR_DELV_DATE     VARCHAR(255) NULL,
#     CONR_DELV_LOC      VARCHAR(255) NULL,
#     CONR_STAT          VARCHAR(255) NULL     DEFAULT 'WAIT',
#     CONR_NO_OF_VEH     INT          NOT NULL DEFAULT 1,
#     CREATED_URL        TEXT NOT NULL,
#     DELETED_URL        TEXT NULL,
#     ACTIVE             BOOLEAN      NOT NULL DEFAULT TRUE,
#     CREATED_AT         CHAR(19)     NOT NULL,
#     UPDATED_AT         CHAR(19)     NOT NULL,
#     DELETED_AT         CHAR(19)     NULL,
#     MEM_ID             VARCHAR(255) NOT NULL,
#     CENT_ID            VARCHAR(255) NOT NULL,
#     CUST_ID            VARCHAR(255) NULL,
#     PROD_ID            VARCHAR(255) NOT NULL,
#     PRIMARY KEY (CONR_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID) ON DELETE CASCADE,
#     FOREIGN KEY (CUST_ID) REFERENCES tb_customer_info (CUST_ID) ON DELETE CASCADE,
#     FOREIGN KEY (PROD_ID) REFERENCES tb_product (PROD_ID) ON DELETE CASCADE,
#     FOREIGN KEY (CENT_ID) REFERENCES tb_center (CENT_ID) ON DELETE CASCADE
# );
#
# CREATE TABLE tb_notice
# (
#     NOT_ID     VARCHAR(255) NOT NULL,
#     NOT_TTL    VARCHAR(255) NOT NULL,
#     NOT_TAG    VARCHAR(255) NOT NULL DEFAULT 'ALL',
#     NOT_CLA    VARCHAR(255) NOT NULL DEFAULT 'NORMAL',
#     NOT_CONT   TEXT         NOT NULL,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     MEM_ID     VARCHAR(255) NOT NULL,
#     PRIMARY KEY (NOT_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_order
# (
#     ORD_ID     VARCHAR(255) NOT NULL,
#     ORD_TTL    VARCHAR(255) NOT NULL,
#     ORD_CONT   TEXT         NOT NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     ORD_STAT   VARCHAR(255) NOT NULL DEFAULT 'WAIT',
#     CONR_ID    VARCHAR(255) NOT NULL,
#     MEM_ID     VARCHAR(255) NOT NULL,
#     ADMIN_ID    VARCHAR(255) NULL,
#     PRIMARY KEY (ORD_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID) ON DELETE CASCADE,
#     FOREIGN KEY (ADMIN_ID) REFERENCES tb_member (MEM_ID) ON DELETE CASCADE,
#     FOREIGN KEY (CONR_ID) REFERENCES tb_contract (CONR_ID) ON DELETE CASCADE
# );
#
# CREATE TABLE tb_problem
# (
#     PROB_ID    VARCHAR(255) NOT NULL,
#     PROB_TTL   VARCHAR(255) NOT NULL,
#     PROB_CONT  VARCHAR(255) NOT NULL,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     DELETED_AT CHAR(19)     NULL,
#     CUST_ID    VARCHAR(255) NOT NULL,
#     MEM_ID     VARCHAR(255) NOT NULL,
#     PROD_ID    VARCHAR(255) NOT NULL,
#     PRIMARY KEY (PROB_ID),
#     FOREIGN KEY (CUST_ID) REFERENCES tb_customer_info (CUST_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID),
#     FOREIGN KEY (PROD_ID) REFERENCES tb_product (PROD_ID)
# );
#
# CREATE TABLE tb_purchase_order
# (
#     PUR_ORD_ID   VARCHAR(255) NOT NULL,
#     PUR_ORD_TTL  VARCHAR(255) NOT NULL,
#     PUR_CONT     TEXT         NOT NULL,
#     CREATED_AT   CHAR(19)     NOT NULL,
#     UPDATED_AT   CHAR(19)     NOT NULL,
#     DELETED_AT   CHAR(19)     NULL,
#     ACTIVE       BOOLEAN      NOT NULL DEFAULT TRUE,
#     PUR_ORD_STAT VARCHAR(255) NOT NULL DEFAULT 'WAIT',
#     ORD_ID       VARCHAR(255) NOT NULL,
#     MEM_ID       VARCHAR(255) NOT NULL,
#     PRIMARY KEY (PUR_ORD_ID),
#     FOREIGN KEY (ORD_ID) REFERENCES tb_order (ORD_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_EVALUATION
# (
#     EVAL_ID    VARCHAR(255) NOT NULL,
#     EVAL_TTL   VARCHAR(255) NOT NULL,
#     EVAL_CONT  VARCHAR(255) NOT NULL,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     CENT_ID    VARCHAR(255) NOT NULL,
#     MEM_ID     VARCHAR(255) NOT NULL,
#     WRI_ID     VARCHAR(255) NOT NULL,
#     PRIMARY KEY (EVAL_ID),
#     FOREIGN KEY (CENT_ID) REFERENCES tb_center (CENT_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID),
#     FOREIGN KEY (WRI_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_promotion
# (
#     PROM_ID    VARCHAR(255) NOT NULL,
#     PROM_TTL   VARCHAR(255) NOT NULL,
#     PROM_CONT  VARCHAR(255) NOT NULL,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     MEM_ID     VARCHAR(255) NOT NULL,
#     PRIMARY KEY (PROM_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_file
# (
#     FILE_ID    VARCHAR(255) NOT NULL,
#     FILE_NAME  VARCHAR(255) NOT NULL,
#     FILE_URL   VARCHAR(255) NOT NULL,
#     FILE_TYPE  VARCHAR(255) NOT NULL,
#     ACTIVE     BOOLEAN      NOT NULL DEFAULT TRUE,
#     CREATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     MEM_ID     VARCHAR(255) NULL,
#     NOT_ID     VARCHAR(255) NULL,
#     CONR_ID    VARCHAR(255) NULL,
#     PROB_ID    VARCHAR(255) NULL,
#     CENT_ID    VARCHAR(255) NULL,
#     PROM_ID    VARCHAR(255) NULL,
#     EVAL_ID    VARCHAR(255) NULL,
#     CONR_ID2   VARCHAR(255) NULL,
#     PRIMARY KEY (FILE_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID),
#     FOREIGN KEY (NOT_ID) REFERENCES tb_notice (NOT_ID),
#     FOREIGN KEY (CONR_ID) REFERENCES tb_contract (CONR_ID),
#     FOREIGN KEY (PROB_ID) REFERENCES tb_problem (PROB_ID),
#     FOREIGN KEY (CENT_ID) REFERENCES tb_center (CENT_ID),
#     FOREIGN KEY (PROM_ID) REFERENCES tb_promotion (PROM_ID),
#     FOREIGN KEY (EVAL_ID) REFERENCES tb_evaluation (EVAL_ID),
#     FOREIGN KEY (CONR_ID2) REFERENCES tb_contract (CONR_ID)
# );
#
# CREATE TABLE tb_schedule
# (
#     SCH_ID     VARCHAR(255) NOT NULL COMMENT 'Comment 1번 참고',
#     SCH_NAME   VARCHAR(255) NOT NULL,
#     SCH_CONT   VARCHAR(255) NOT NULL,
#     SCH_TAG    VARCHAR(255) NOT NULL,
#     SCH_SRT_AT CHAR(19)     NOT NULL,
#     SCH_END_AT CHAR(19)     NOT NULL,
#     CREATED_AT CHAR(19)     NOT NULL,
#     UPDATED_AT CHAR(19)     NOT NULL,
#     DELETED_AT CHAR(19)     NULL,
#     ACTIVE     BOOLEAN      NOT NULL COMMENT 'TRUE/FALSE',
#     MEM_ID     VARCHAR(255) NOT NULL COMMENT 'Comment 1번 참고',
#     PRIMARY KEY (SCH_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_alarm
# (
#     ALR_ID        VARCHAR(255) NOT NULL,
#     ALR_MSG       VARCHAR(255) NOT NULL,
#     ALR_URL       VARCHAR(255) NOT NULL,
#     ALR_READ_STAT BOOLEAN      NOT NULL DEFAULT FALSE,
#     CREATED_AT    CHAR(19)     NOT NULL,
# #     ALR_STAT       VARCHAR(255) NOT NULL DEFAULT 'PENDING',
#     MEM_ID        VARCHAR(255) NOT NULL,
#     PRIMARY KEY (ALR_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID)
# );
#
# CREATE TABLE tb_family
# (
#     FAM_ID          VARCHAR(255) NOT NULL,
#     FAM_NAME        VARCHAR(255) NOT NULL,
#     FAM_REL         VARCHAR(255) NOT NULL,
#     FAM_BIR         VARCHAR(255) NOT NULL,
#     FAM_IDEN_NO     VARCHAR(255) NOT NULL,
#     FAM_PHO         VARCHAR(255) NOT NULL,
#     FAM_SEX         VARCHAR(255) NOT NULL,
#     FAM_DIS         BOOLEAN      NOT NULL,
#     FAM_DIE         BOOLEAN      NOT NULL,
#     FAM_NOTE        VARCHAR(255) NULL,
#     CREATED_AT      CHAR(19)     NOT NULL,
#     UPDATED_AT      CHAR(19)     NOT NULL,
#     MEM_ID          VARCHAR(255) NOT NULL,
#     PRIMARY KEY (FAM_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member(MEM_ID)
# );
#
# CREATE TABLE tb_education
# (
#     EDU_ID          VARCHAR(255) NOT NULL,
#     EDU_ENTD        VARCHAR(255) NOT NULL,
#     EDU_GRAD        VARCHAR(255) NOT NULL,
#     EDU_NAME        VARCHAR(255) NOT NULL,
#     EDU_MJR         VARCHAR(255) NOT NULL,
#     EDU_SCO         VARCHAR(255) NULL,
#     EDU_NOTE        VARCHAR(255) NULL,
#     CREATED_AT      CHAR(19)     NOT NULL,
#     UPDATED_AT      CHAR(19)     NOT NULL,
#     MEM_ID          VARCHAR(255) NOT NULL,
#     PRIMARY KEY (EDU_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member(MEM_ID)
# );
#
# CREATE TABLE tb_certification
# (
#     CER_ID          VARCHAR(255) NOT NULL,
#     CER_DATE        VARCHAR(255) NOT NULL,
#     CER_INST        VARCHAR(255) NOT NULL,
#     CER_NAME        VARCHAR(255) NOT NULL,
#     CER_SCO         VARCHAR(255) NOT NULL,
#     CER_NOTE        VARCHAR(255) NULL,
#     CREATED_AT      CHAR(19)     NOT NULL,
#     MEM_ID          VARCHAR(255) NOT NULL,
#     PRIMARY KEY (CER_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member(MEM_ID)
# );
#
# CREATE TABLE tb_career
# (
#     CAR_ID          VARCHAR(255) NOT NULL,
#     CAR_EMP_DATE    VARCHAR(255) NOT NULL,
#     CAR_RTR_DATE    VARCHAR(255) NULL,
#     CAR_NAME        VARCHAR(255) NOT NULL,
#     CAR_NOTE        VARCHAR(255) NULL,
#     CREATED_AT      CHAR(19)     NOT NULL,
#     MEM_ID          VARCHAR(255) NOT NULL,
#     PRIMARY KEY (CAR_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member(MEM_ID)
# );
#
# CREATE TABLE tb_UPDATE_HISTORY
# (
#     UPD_ID      VARCHAR(255) NOT NULL,
#     UPD_IP      VARCHAR(255) NOT NULL,
#     UPDATED_AT  CHAR(19)     NOT NULL,
#     UPDATED_URL VARCHAR(255) NOT NULL,
#     MEM_ID      VARCHAR(255) NOT NULL,
#     CONR_ID     VARCHAR(255) NOT NULL,
#     PRIMARY KEY (UPD_ID),
#     FOREIGN KEY (MEM_ID) REFERENCES tb_member (MEM_ID),
#     FOREIGN KEY (CONR_ID) REFERENCES tb_contract (CONR_ID)
# );
#
# CREATE TABLE tb_PRODUCT_OPTION
# (
#     PROD_ID          VARCHAR(255) NOT NULL,
#     PROD_SER_NO      VARCHAR(255) NOT NULL,
#     OPT_CNTY         CHAR(1)      NOT NULL DEFAULT 'K',
#     OPT_MNFR         CHAR(1)      NOT NULL DEFAULT 'N',
#     OPT_VHC_TYPE     CHAR(1)      NOT NULL,
#     OPT_CHSS         CHAR(1)      NOT NULL,
#     OPT_DTIL_TYPE    CHAR(1)      NOT NULL,
#     OPT_BODY_TYPE    CHAR(1)      NOT NULL,
#     OPT_SFTY_DVCE    CHAR(1)      NOT NULL,
#     OPT_ENGN_CPCT    CHAR(1)      NOT NULL,
#     OPT_SCRT_CODE    CHAR(1)      NOT NULL,
#     OPT_PRDC_YEAR    CHAR(1)      NOT NULL,
#     OPT_PRDC_PLNT    CHAR(1)      NOT NULL,
#     OPT_ENGN         CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_MSSN         CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_TRIM         CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_XTNL_COLR    CHAR(1)      NOT NULL,
#     OPT_ITNL_COLR    CHAR(1)      NOT NULL,
#     OPT_HUD          CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_NAVI         CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_DRVE_WISE    CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_SMRT_CNCT    CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_STYL         CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_MY_CFRT_PCKG CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_OTDR_PCKG    CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_SUN_ROOF     CHAR(1)      NOT NULL DEFAULT '0',
#     OPT_SOND         CHAR(1)      NOT NULL DEFAULT '0',
#     ACTIVE           BOOLEAN      NOT NULL DEFAULT TRUE,
#     PRIMARY KEY (PROD_ID, PROD_SER_NO),
#     FOREIGN KEY (PROD_ID, PROD_SER_NO) REFERENCES tb_product (PROD_ID, PROD_SER_NO) ON DELETE CASCADE
# );
#
# CREATE TABLE tb_sales_history
# (
#     SAL_HIST_ID VARCHAR(255) NOT NULL COMMENT 'Comment 1번 참고',
#     CONR_ID     VARCHAR(255) NOT NULL COMMENT 'Comment 1번 참고'
# );

INSERT INTO tb_organization_chart (ORG_CHA_ID, ORG_CHA_NAME, ORG_CHA_DEPTH)
VALUES ('ORG_000000001', '본사', 1),
       ('ORG_000000002', '서울지사', 2),
       ('ORG_000000003', '부산지사', 2),
       ('ORG_000000004', '인천지사', 2),
       ('ORG_000000005', '대전지사', 2),
       ('ORG_000000006', '영업부', 3),
       ('ORG_000000007', '기술부', 3),
       ('ORG_000000008', '고객지원부', 3),
       ('ORG_000000009', '마케팅부', 3),
       ('ORG_000000010', '재무부', 3);

INSERT INTO tb_center (CENT_ID, CENT_NAME, CENT_ADR, CENT_PHO, CENT_MEM_CNT, CREATED_AT, UPDATED_AT, ACTIVE,
                       CENT_OPR_AT)
VALUES ('CEN_000000001', '서울센터', '서울특별시 중구', '010-1234-5678', 50, '2024-01-01 12:00:00', '2024-01-01 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000002', '부산센터', '부산광역시 해운대구', '010-2345-6789', 40, '2024-01-02 12:00:00', '2024-01-02 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000003', '대구센터', '대구광역시', '010-3456-7890', 30, '2024-01-03 12:00:00', '2024-01-03 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000004', '인천센터', '인천광역시', '010-4567-8901', 20, '2024-01-04 12:00:00', '2024-01-04 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000005', '울산센터', '울산광역시', '010-5678-9012', 25, '2024-01-05 12:00:00', '2024-01-05 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000006', '대전센터', '대전광역시', '010-6789-0123', 35, '2024-01-06 12:00:00', '2024-01-06 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000007', '광주센터', '광주광역시', '010-7890-1234', 45, '2024-01-07 12:00:00', '2024-01-07 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000008', '경기센터', '경기도 수원시', '010-8901-2345', 20, '2024-01-08 12:00:00', '2024-01-08 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000009', '청주센터', '충청북도 청주시', '010-9012-3456', 10, '2024-01-09 12:00:00', '2024-01-09 12:00:00', TRUE,
        '09:00-18:00'),
       ('CEN_000000010', '전주센터', '전라북도 전주시', '010-0123-4567', 15, '2024-01-10 12:00:00', '2024-01-10 12:00:00', TRUE,
        '09:00-18:00');

INSERT INTO tb_member (MEM_ID, MEM_LOGIN_ID, MEM_PWD, MEM_NAME, MEM_EMA, MEM_AGE, MEM_SEX, MEM_IDEN_NO, MEM_PHO, MEM_ADR,
                       MEM_POS, MEM_GRD, MEM_JOB_TYPE, CENTER_ID, ORG_CHA_ID, CREATED_AT, UPDATED_AT, ACTIVE)
VALUES ('MEM_000000001', 101, 'pwd1234', '김철수', 'chulsoo@example.com', 35, 'MALE', '123456-7890123', '010-9876-5432',
        '서울특별시', 'Manager', 'Bachelor', 'Full-time', 'CEN_000000001', 'ORG_000000006', '2024-01-01 12:00:00',
        '2024-01-01 12:00:00', TRUE),
       ('MEM_000000002', 102, 'pwd2345', '이영희', 'younghee@example.com', 28, 'FEMALE', '234567-8901234', '010-8765-4321',
        '부산광역시', 'Staff', 'Master', 'Full-time', 'CEN_000000002', 'ORG_000000006', '2024-01-02 12:00:00',
        '2024-01-02 12:00:00', TRUE),
       ('MEM_000000003', 103, 'pwd3456', '박지훈', 'jihun@example.com', 42, 'MALE', '345678-9012345', '010-7654-3210',
        '대구광역시', 'Engineer', 'Doctorate', 'Contract', 'CEN_000000003', 'ORG_000000007', '2024-01-03 12:00:00',
        '2024-01-03 12:00:00', TRUE),
       ('MEM_000000004', 104, 'pwd4567', '최민수', 'minsoo@example.com', 38, 'MALE', '456789-0123456', '010-6543-2109',
        '인천광역시', 'Manager', 'Bachelor', 'Part-time', 'CEN_000000004', 'ORG_000000007', '2024-01-04 12:00:00',
        '2024-01-04 12:00:00', TRUE),
       ('MEM_000000005', 105, 'pwd5678', '박미영', 'miyoung@example.com', 29, 'FEMALE', '567890-1234567', '010-5432-1098',
        '울산광역시', 'Intern', 'High School', 'Full-time', 'CEN_000000005', 'ORG_000000008', '2024-01-05 12:00:00',
        '2024-01-05 12:00:00', TRUE),
       ('MEM_000000006', 106, 'pwd6789', '정수현', 'soohyun@example.com', 31, 'FEMALE', '678901-2345678', '010-4321-0987',
        '대전광역시', 'Senior Engineer', 'Master', 'Full-time', 'CEN_000000006', 'ORG_000000007', '2024-01-06 12:00:00',
        '2024-01-06 12:00:00', TRUE),
       ('MEM_000000007', 107, 'pwd7890', '한지민', 'jimin@example.com', 45, 'FEMALE', '789012-3456789', '010-3210-9876',
        '광주광역시', 'Sales Rep', 'Bachelor', 'Part-time', 'CEN_000000007', 'ORG_000000006', '2024-01-07 12:00:00',
        '2024-01-07 12:00:00', TRUE),
       ('MEM_000000008', 108, 'pwd8901', '이준호', 'junho@example.com', 36, 'MALE', '890123-4567890', '010-2109-8765',
        '경기도 수원시', 'Technician', 'Bachelor', 'Full-time', 'CEN_000000008', 'ORG_000000008', '2024-01-08 12:00:00',
        '2024-01-08 12:00:00', TRUE);

INSERT INTO tb_member_role (MEM_ROL_ID, MEM_ROL_NAME, MEM_ID)
VALUES ('MEM_ROL_000000001', '영업 사원', 'MEM_000000001'),
       ('MEM_ROL_000000002', '영업 사원', 'MEM_000000002'),
       ('MEM_ROL_000000003', '영업 사원', 'MEM_000000003'),
       ('MEM_ROL_000000004', '영업 사원', 'MEM_000000004'),
       ('MEM_ROL_000000005', '영업 관리자', 'MEM_000000005'),
       ('MEM_ROL_000000006', '영업 관리자', 'MEM_000000009'),
       ('MEM_ROL_000000007', '영업 관리자', 'MEM_000000007'),
       ('MEM_ROL_000000008', '영업 담당자', 'MEM_000000008'),
       ('MEM_ROL_000000009', '영업 담당자', 'MEM_000000010'),
       ('MEM_ROL_000000010', '영업 담당자', 'MEM_000000006');

INSERT INTO tb_customer_info (CUST_ID, CUST_NAME, CUST_AGE, CUST_SEX, CUST_PHO, CUST_EMER_PHO, CUST_EMA, ACTIVE, MEM_ID)
VALUES ('CUS_000000001', '홍길동', 45, 'MALE', '010-1111-2222', '010-2222-3333', 'gildong@example.com', TRUE,
        'MEM_000000001'),
       ('CUS_000000002', '김민수', 38, 'MALE', '010-3333-4444', '010-4444-5555', 'minsoo@example.com', TRUE,
        'MEM_000000002'),
       ('CUS_000000003', '이영희', 32, 'FEMALE', '010-5555-6666', '010-6666-7777', 'younghee@example.com', TRUE,
        'MEM_000000003'),
       ('CUS_000000004', '박지훈', 27, 'MALE', '010-7777-8888', '010-8888-9999', 'jihun@example.com', TRUE,
        'MEM_000000004'),
       ('CUS_000000005', '최정민', 22, 'FEMALE', '010-9999-0000', '010-0000-1111', 'jungmin@example.com', TRUE,
        'MEM_000000005'),
       ('CUS_000000006', '정수민', 31, 'MALE', '010-1212-3434', '010-2323-4545', 'suming@example.com', TRUE,
        'MEM_000000006'),
       ('CUS_000000007', '한민정', 29, 'FEMALE', '010-4545-5656', '010-5656-6767', 'hanmj@example.com', TRUE,
        'MEM_000000007'),
       ('CUS_000000008', '이동수', 41, 'MALE', '010-6767-7878', '010-7878-8989', 'dongsu@example.com', TRUE,
        'MEM_000000008'),
       ('CUS_000000009', '윤소라', 33, 'FEMALE', '010-8989-9090', '010-9090-1010', 'sora@example.com', TRUE,
        'MEM_000000009'),
       ('CUS_000000010', '박성훈', 36, 'MALE', '010-1010-1112', '010-1212-1313', 'seonghun@example.com', TRUE,
        'MEM_000000010');

INSERT INTO tb_product (PROD_ID, PROD_SER_NO, PROD_COST, PROD_NAME, PROD_STCK, CREATED_AT, UPDATED_AT, ACTIVE)
VALUES ('PRO_000000001', 'KNAHAA4AALU1A00001', 25000000, '쏘렌토', 10, '2024-01-10 10:00:00', '2024-01-10 11:00:00', TRUE),
       ('PRO_000000002', 'KNAHBA4BALR2Z00002', 22000000, '스포티지', 15, '2024-01-11 11:00:00', '2024-01-11 12:00:00',
        TRUE),
       ('PRO_000000003', 'KMBHC64CAMJ5A00003', 28000000, 'K7', 8, '2024-01-12 12:00:00', '2024-01-12 13:00:00', TRUE),
       ('PRO_000000004', 'KNJFA42DALU3C00004', 19000000, '셀토스', 12, '2024-01-13 13:00:00', '2024-01-13 14:00:00', TRUE),
       ('PRO_000000005', 'KFBGBM5EARP1M00005', 18000000, 'K3', 20, '2024-01-14 14:00:00', '2024-01-14 15:00:00', TRUE),
       ('PRO_000000006', 'KNHGA6BALUP7A00006', 34000000, '모하비', 7, '2024-01-15 15:00:00', '2024-01-15 16:00:00', TRUE),
       ('PRO_000000007', 'KNJFA34AALU4Z00007', 32000000, 'K8', 5, '2024-01-16 16:00:00', '2024-01-16 17:00:00', TRUE),
       ('PRO_000000008', 'KMAHDA2AAMJ3T00008', 27000000, '스팅어', 9, '2024-01-17 17:00:00', '2024-01-17 18:00:00', TRUE),
       ('PRO_000000009', 'KNAHCA5BALU5C00009', 23000000, '니로', 14, '2024-01-18 18:00:00', '2024-01-18 19:00:00', TRUE),
       ('PRO_000000010', 'KNFHC54CAMR1A00010', 28000000, 'K5', 6, '2024-01-19 19:00:00', '2024-01-19 20:00:00', TRUE);


INSERT INTO tb_contract (CONR_ID, CONR_NAME, CONR_CUST_NAME, CONR_CUST_IDEN_NO, CONR_CUST_ADR,
                         CONR_CUST_EMA, CONR_CUST_PHO, CONR_COMP_NAME, CONR_CUST_CLA, CONR_CUST_PUR_COND,
                         CONR_SERI_NUM, CONR_SELE_OPTI, CONR_DOWN_PAY, CONR_INTE_PAY, CONR_REM_PAY,
                         CONR_CONS_PAY, CONR_DELV_DATE, CONR_DELV_LOC, CONR_STAT, CONR_NO_OF_VEH,
                         CREATED_URL, DELETED_URL, ACTIVE, CREATED_AT, UPDATED_AT, DELETED_AT,
                         MEM_ID, CENT_ID, CUST_ID, PROD_ID)
VALUES
-- 계약 1
('CON_000000001', '쏘렌토 계약', '박지훈', '880512-1234567', '서울특별시 강남구',
 'jihun@example.com', '010-1234-5678', '기아자동차', 'PERSONAL', 'CASH',
 'SER_001', '풀옵션', 5000000, 300000, 25000000, 30000000,
 '2024-02-20', '서울특별시 강남구 배송센터', 'WAIT', 1,
 '/contracts/1', NULL, TRUE, '2024-01-10 12:00:00', '2024-01-11 13:00:00', NULL,
 'MEM_000000001', 'CEN_000000001', 'CUS_000000001', 'PRO_000000001'),

-- 계약 2
('CON_000000002', '스포티지 계약', '김영희', '900123-2345678', '부산광역시 해운대구',
 'younghee@example.com', '010-2345-6789', '기아자동차', 'PERSONAL', 'CREDIT',
 'SER_002', '기본옵션', 3000000, 200000, 17000000, 25000000,
 '2024-03-05', '부산광역시 해운대구 배송센터', 'CONFIRMED', 1,
 '/contracts/2', NULL, TRUE, '2024-01-12 14:00:00', '2024-01-13 15:00:00', NULL,
 'MEM_000000002', 'CEN_000000002', 'CUS_000000002', 'PRO_000000002'),

-- 계약 3
('CON_000000003', 'K7 계약', '이철수', '950405-3456789', '대구광역시 수성구',
 'chulsoo@example.com', '010-3456-7890', '기아자동차', 'BUSINESS', 'LEASE',
 'SER_003', '풀옵션', 8000000, 400000, 28000000, 35000000,
 '2024-03-10', '대구광역시 배송센터', 'WAIT', 2,
 '/contracts/3', NULL, TRUE, '2024-01-14 09:00:00', '2024-01-15 10:00:00', NULL,
 'MEM_000000003', 'CEN_000000003', 'CUS_000000003', 'PRO_000000003'),

-- 계약 4
('CON_000000004', '셀토스 계약', '박민준', '970512-4567890', '인천광역시 미추홀구',
 'minjoon@example.com', '010-4567-8901', '기아자동차', 'PERSONAL', 'CASH',
 'SER_004', '기본옵션', 4000000, 250000, 12000000, 20000000,
 '2024-04-01', '인천광역시 배송센터', 'DELIVERED', 1,
 '/contracts/4', NULL, TRUE, '2024-01-16 11:00:00', '2024-01-17 12:00:00', NULL,
 'MEM_000000004', 'CEN_000000004', 'CUS_000000004', 'PRO_000000004'),

-- 계약 5
('CON_000000005', 'K3 계약', '최민수', '980618-5678901', '울산광역시 중구',
 'minsoo@example.com', '010-5678-9012', '기아자동차', 'BUSINESS', 'INSTALLMENT',
 'SER_005', '기본옵션', 2000000, 100000, 13000000, 18000000,
 '2024-04-15', '울산광역시 배송센터', 'CANCELLED', 1,
 '/contracts/5', NULL, TRUE, '2024-01-18 13:00:00', '2024-01-19 14:00:00', NULL,
 'MEM_000000005', 'CEN_000000005', 'CUS_000000005', 'PRO_000000005'),

-- 계약 6
('CON_000000006', '모하비 계약', '김민주', '850824-6789012', '대전광역시 서구',
 'minju@example.com', '010-6789-0123', '기아자동차', 'PERSONAL', 'CASH',
 'SER_006', '풀옵션', 6000000, 500000, 34000000, 40000000,
 '2024-05-01', '대전광역시 배송센터', 'CONFIRMED', 1,
 '/contracts/6', NULL, TRUE, '2024-01-20 15:00:00', '2024-01-21 16:00:00', NULL,
 'MEM_000000006', 'CEN_000000006', 'CUS_000000006', 'PRO_000000006'),

-- 계약 7
('CON_000000007', 'K8 계약', '정수영', '940705-7890123', '광주광역시 북구',
 'suyoung@example.com', '010-7890-1234', '기아자동차', 'LEASE', 'LEASE',
 'SER_007', '럭셔리 패키지', 7000000, 350000, 25000000, 32000000,
 '2024-05-20', '광주광역시 배송센터', 'WAIT', 1,
 '/contracts/7', NULL, TRUE, '2024-01-22 17:00:00', '2024-01-23 18:00:00', NULL,
 'MEM_000000007', 'CEN_000000007', 'CUS_000000007', 'PRO_000000007'),

-- 계약 8
('CON_000000008', '스팅어 계약', '이준호', '930910-8901234', '경기도 수원시',
 'junho@example.com', '010-8901-2345', '기아자동차', 'PERSONAL', 'CREDIT',
 'SER_008', '풀옵션', 5000000, 300000, 22000000, 27000000,
 '2024-06-01', '수원시 배송센터', 'DELIVERED', 1,
 '/contracts/8', NULL, TRUE, '2024-01-24 19:00:00', '2024-01-25 20:00:00', NULL,
 'MEM_000000008', 'CEN_000000008', 'CUS_000000008', 'PRO_000000008'),

-- 계약 9
('CON_000000009', '니로 계약', '박소영', '890321-9012345', '충청북도 청주시',
 'soyoung@example.com', '010-9012-3456', '기아자동차', 'PERSONAL', 'CASH',
 'SER_009', '기본옵션', 3500000, 150000, 17000000, 23000000,
 '2024-06-15', '청주시 배송센터', 'CONFIRMED', 1,
 '/contracts/9', NULL, TRUE, '2024-01-26 21:00:00', '2024-01-27 22:00:00', NULL,
 'MEM_000000009', 'CEN_000000009', 'CUS_000000009', 'PRO_000000009'),

-- 계약 10
('CON_000000010', 'K5 계약', '김정훈', '900815-0123456', '전라북도 전주시',
 'junghoon@example.com', '010-0123-4567', '기아자동차', 'BUSINESS', 'LEASE',
 'SER_010', '풀옵션', 6000000, 250000, 20000000, 28000000,
 '2024-07-01', '전주시 배송센터', 'WAIT', 1,
 '/contracts/10', NULL, TRUE, '2024-01-28 23:00:00', '2024-01-29 23:59:00', NULL,
 'MEM_000000010', 'CEN_000000010', 'CUS_000000010', 'PRO_000000010');

INSERT INTO tb_order (
    ORD_ID, ORD_TTL, ORD_CONT, ACTIVE, CREATED_AT, UPDATED_AT, DELETED_AT, ORD_STAT, CONR_ID, MEM_ID, ADMIN_ID
) VALUES
-- 주문 1
('ORD_000000001', '쏘렌토 계약 주문', '쏘렌토 차량 계약 완료 후 주문 처리', TRUE, '2024-01-10 10:00:00', '2024-01-10 11:00:00', NULL, 'CONFIRMED', 'CON_000000001', 'MEM_000000001', 'MEM_000000005'),
-- 주문 2
('ORD_000000002', '스포티지 구매 주문', '스포티지 고객 상담 후 주문 확정', TRUE, '2024-01-11 12:00:00', '2024-01-11 13:00:00', NULL, 'WAIT', 'CON_000000002', 'MEM_000000002', 'MEM_000000005'),
-- 주문 3
('ORD_000000003', 'K7 리스 주문', 'K7 리스 계약 완료 후 주문 생성', TRUE, '2024-01-12 09:00:00', '2024-01-12 10:00:00', NULL, 'CONFIRMED', 'CON_000000003', 'MEM_000000003', 'MEM_000000005'),
-- 주문 4
('ORD_000000004', '셀토스 추가 주문', '셀토스 고객 추가 옵션 주문', TRUE, '2024-01-13 10:30:00', '2024-01-13 11:30:00', NULL, 'CANCELLED', 'CON_000000004', 'MEM_000000004', 'MEM_000000005'),
-- 주문 5
('ORD_000000005', 'K3 계약 주문', 'K3 차량 계약 후 최종 주문', TRUE, '2024-01-14 11:00:00', '2024-01-14 12:00:00', NULL, 'DELIVERED', 'CON_000000005', 'MEM_000000001', 'MEM_000000006'),
-- 주문 6
('ORD_000000006', '모하비 계약 수정 주문', '모하비 계약 변경에 따른 주문 수정', TRUE, '2024-01-15 13:00:00', '2024-01-15 14:00:00', NULL, 'WAIT', 'CON_000000006', 'MEM_000000002', 'MEM_000000006'),
-- 주문 7
('ORD_000000007', 'K8 리스 주문', 'K8 차량 리스 계약 주문', TRUE, '2024-01-16 14:30:00', '2024-01-16 15:30:00', NULL, 'CONFIRMED', 'CON_000000007', 'MEM_000000003', 'MEM_000000006'),
-- 주문 8
('ORD_000000008', '스팅어 구매 주문', '스팅어 차량 구매 계약 후 주문 확정', TRUE, '2024-01-17 16:00:00', '2024-01-17 17:00:00', NULL, 'DELIVERED', 'CON_000000008', 'MEM_000000004', 'MEM_000000006'),
-- 주문 9
('ORD_000000009', '니로 전기차 주문', '니로 전기차 계약 주문', TRUE, '2024-01-18 17:30:00', '2024-01-18 18:30:00', NULL, 'WAIT', 'CON_000000009', 'MEM_000000001', 'MEM_000000010'),
-- 주문 10
('ORD_000000010', 'K5 재고 주문', 'K5 재고 차량 추가 주문', TRUE, '2024-01-19 09:00:00', '2024-01-19 10:00:00', NULL, 'CONFIRMED', 'CON_000000010', 'MEM_000000001', 'MEM_00000009');


INSERT INTO tb_problem (PROB_ID, PROB_TTL, PROB_CONT, CREATED_AT, UPDATED_AT, ACTIVE, CUST_ID, MEM_ID, PROD_ID)
VALUES ('PROB_000000001', '쏘렌토 엔진 문제', '엔진에서 소음 발생', '2024-01-12 14:00:00', '2024-01-12 15:00:00', TRUE,
        'CUS_000000001', 'MEM_000000001', 'PRO_000000001'),
       ('PROB_000000002', '스포티지 브레이크 문제', '브레이크 페달 작동 불량', '2024-01-13 10:00:00', '2024-01-13 11:00:00', TRUE,
        'CUS_000000002', 'MEM_000000002', 'PRO_000000002'),
       ('PROB_000000003', 'K7 전자 장비 문제', '내비게이션 오류', '2024-01-14 09:00:00', '2024-01-14 10:00:00', TRUE,
        'CUS_000000003', 'MEM_000000003', 'PRO_000000003'),
       ('PROB_000000004', '셀토스 에어컨 문제', '에어컨 작동 안됨', '2024-01-15 11:00:00', '2024-01-15 12:00:00', TRUE,
        'CUS_000000004', 'MEM_000000004', 'PRO_000000004'),
       ('PROB_000000005', 'K3 연비 문제', '연비가 예상보다 낮음', '2024-01-16 12:00:00', '2024-01-16 13:00:00', TRUE,
        'CUS_000000005', 'MEM_000000005', 'PRO_000000005'),
       ('PROB_000000006', '모하비 변속기 문제', '변속기 오류 발생', '2024-01-17 13:00:00', '2024-01-17 14:00:00', TRUE,
        'CUS_000000006', 'MEM_000000006', 'PRO_000000006'),
       ('PROB_000000007', 'K8 배터리 문제', '배터리 수명 짧음', '2024-01-18 14:00:00', '2024-01-18 15:00:00', TRUE, 'CUS_000000007',
        'MEM_000000007', 'PRO_000000007'),
       ('PROB_000000008', '스팅어 오디오 문제', '오디오 스피커 잡음', '2024-01-19 15:00:00', '2024-01-19 16:00:00', TRUE,
        'CUS_000000008', 'MEM_000000008', 'PRO_000000008'),
       ('PROB_000000009', '니로 전기 문제', '충전 불량', '2024-01-20 16:00:00', '2024-01-20 17:00:00', TRUE, 'CUS_000000009',
        'MEM_000000009', 'PRO_000000009'),
       ('PROB_000000010', 'K5 시동 문제', '시동이 걸리지 않음', '2024-01-21 17:00:00', '2024-01-21 18:00:00', TRUE, 'CUS_000000010',
        'MEM_000000010', 'PRO_000000010');

INSERT INTO tb_purchase_order (PUR_ORD_ID, PUR_ORD_TTL, PUR_CONT, CREATED_AT, UPDATED_AT, ACTIVE, PUR_ORD_STAT, ORD_ID,
                               MEM_ID)
VALUES ('PUR_000000001', '쏘렌토 부품 주문', '엔진 부품 요청', '2024-01-12 10:00:00', '2024-01-12 11:00:00', TRUE, 'WAIT',
        'ORD_000000001', 'MEM_000000001'),
       ('PUR_000000002', '스포티지 타이어 주문', '타이어 4개 요청', '2024-01-13 12:00:00', '2024-01-13 13:00:00', TRUE, 'CONFIRMED',
        'ORD_000000002', 'MEM_000000002'),
       ('PUR_000000003', 'K7 배터리 주문', '배터리 교체 요청', '2024-01-14 13:00:00', '2024-01-14 14:00:00', TRUE, 'DELIVERED',
        'ORD_000000003', 'MEM_000000003'),
       ('PUR_000000004', '셀토스 브레이크 주문', '브레이크 패드 요청', '2024-01-15 14:00:00', '2024-01-15 15:00:00', TRUE, 'WAIT',
        'ORD_000000004', 'MEM_000000004'),
       ('PUR_000000005', 'K3 내비게이션 주문', '내비게이션 교체', '2024-01-16 15:00:00', '2024-01-16 16:00:00', TRUE, 'CANCELLED',
        'ORD_000000005', 'MEM_000000005'),
       ('PUR_000000006', '모하비 오일 필터 주문', '오일 필터 10개 요청', '2024-01-17 09:00:00', '2024-01-17 10:00:00', TRUE,
        'CONFIRMED', 'ORD_000000006', 'MEM_000000006'),
       ('PUR_000000007', 'K8 헤드라이트 주문', 'LED 헤드라이트 2개 요청', '2024-01-18 10:00:00', '2024-01-18 11:00:00', TRUE, 'WAIT',
        'ORD_000000007', 'MEM_000000007'),
       ('PUR_000000008', '스팅어 도어 핸들 주문', '도어 핸들 4개 요청', '2024-01-19 12:00:00', '2024-01-19 13:00:00', TRUE, 'CONFIRMED',
        'ORD_000000008', 'MEM_000000008'),
       ('PUR_000000009', '니로 충전 케이블 주문', '전기차 충전 케이블 요청', '2024-01-20 14:00:00', '2024-01-20 15:00:00', TRUE,
        'DELIVERED', 'ORD_000000009', 'MEM_000000009'),
       ('PUR_000000010', 'K5 엔진오일 주문', '엔진오일 20L 요청', '2024-01-21 15:00:00', '2024-01-21 16:00:00', TRUE, 'WAIT',
        'ORD_000000010', 'MEM_000000010');

INSERT INTO tb_notice (NOT_ID, NOT_TTL, NOT_TAG, NOT_CLA, NOT_CONT, CREATED_AT, UPDATED_AT, ACTIVE, MEM_ID)
VALUES ('NOT_000000001', '신차 출시 공지', 'ALL', 'IMPORTANT', '신형 쏘렌토가 출시되었습니다.', '2024-01-10 09:00:00',
        '2024-01-10 09:30:00', TRUE, 'MEM_000000001'),
       ('NOT_000000002', '정기 점검 공지', 'SERVICE', 'NORMAL', '1월 정기 점검 안내입니다.', '2024-01-11 10:00:00',
        '2024-01-11 10:30:00', TRUE, 'MEM_000000002'),
       ('NOT_000000003', '할인 프로모션', 'SALES', 'IMPORTANT', 'K5 한정 할인 프로모션 안내', '2024-01-12 11:00:00',
        '2024-01-12 11:30:00', TRUE, 'MEM_000000003'),
       ('NOT_000000004', '서비스 센터 이전 안내', 'SERVICE', 'NORMAL', '서울센터가 이전되었습니다.', '2024-01-13 12:00:00',
        '2024-01-13 12:30:00', TRUE, 'MEM_000000004'),
       ('NOT_000000005', '부품 재고 부족', 'ALL', 'WARNING', '모하비 부품 재고가 부족합니다.', '2024-01-14 13:00:00',
        '2024-01-14 13:30:00', TRUE, 'MEM_000000005'),
       ('NOT_000000006', '설 연휴 휴무 안내', 'ALL', 'NORMAL', '설 연휴 기간 동안 휴무합니다.', '2024-01-15 14:00:00',
        '2024-01-15 14:30:00', TRUE, 'MEM_000000006'),
       ('NOT_000000007', 'K8 시승 이벤트', 'EVENT', 'NORMAL', 'K8 시승 이벤트에 참여하세요.', '2024-01-16 15:00:00',
        '2024-01-16 15:30:00', TRUE, 'MEM_000000007'),
       ('NOT_000000008', '스포티지 리콜 안내', 'SERVICE', 'CRITICAL', '스포티지 일부 모델 리콜 안내', '2024-01-17 16:00:00',
        '2024-01-17 16:30:00', TRUE, 'MEM_000000008'),
       ('NOT_000000009', '신입사원 모집', 'HR', 'NORMAL', '기아자동차 신입사원 모집 공고', '2024-01-18 17:00:00', '2024-01-18 17:30:00',
        TRUE, 'MEM_000000009'),
       ('NOT_000000010', '고객 감사 이벤트', 'EVENT', 'NORMAL', '고객 감사 사은품 증정 이벤트', '2024-01-19 18:00:00',
        '2024-01-19 18:30:00', TRUE, 'MEM_000000010');

INSERT INTO tb_file (FILE_ID, FILE_NAME, FILE_URL, FILE_TYPE, ACTIVE, CREATED_AT, MEM_ID, NOT_ID)
VALUES ('FIL_000000001', '쏘렌토_계약서.pdf', '/files/contract1.pdf', 'pdf', TRUE, '2024-01-10 10:00:00', 'MEM_000000001',
        'NOT_000000001'),
       ('FIL_000000002', '스포티지_메뉴얼.pdf', '/files/manual2.pdf', 'pdf', TRUE, '2024-01-11 10:30:00', 'MEM_000000002',
        'NOT_000000002'),
       ('FIL_000000003', 'K7_견적서.pdf', '/files/estimate3.pdf', 'pdf', TRUE, '2024-01-12 11:00:00', 'MEM_000000003',
        'NOT_000000003'),
       ('FIL_000000004', '셀토스_리콜_공지.pdf', '/files/recall4.pdf', 'pdf', TRUE, '2024-01-13 12:00:00', 'MEM_000000004',
        'NOT_000000004'),
       ('FIL_000000005', 'K3_정비보고서.pdf', '/files/report5.pdf', 'pdf', TRUE, '2024-01-14 13:00:00', 'MEM_000000005',
        'NOT_000000005'),
       ('FIL_000000006', '모하비_오일교환.pdf', '/files/oil6.pdf', 'pdf', TRUE, '2024-01-15 14:00:00', 'MEM_000000006',
        'NOT_000000006'),
       ('FIL_000000007', 'K8_이벤트_포스터.png', '/files/event7.png', 'image', TRUE, '2024-01-16 15:00:00', 'MEM_000000007',
        'NOT_000000007'),
       ('FIL_000000008', '스팅어_사용설명서.pdf', '/files/manual8.pdf', 'pdf', TRUE, '2024-01-17 16:00:00', 'MEM_000000008',
        'NOT_000000008'),
       ('FIL_000000009', '니로_충전가이드.pdf', '/files/guide9.pdf', 'pdf', TRUE, '2024-01-18 17:00:00', 'MEM_000000009',
        'NOT_000000009'),
       ('FIL_000000010', 'K5_고객이벤트_배너.jpg', '/files/banner10.jpg', 'image', TRUE, '2024-01-19 18:00:00',
        'MEM_000000010', 'NOT_000000010');

INSERT INTO tb_schedule (SCH_ID, SCH_NAME, SCH_CONT, SCH_TAG, SCH_SRT_AT, SCH_END_AT, CREATED_AT, UPDATED_AT, DELETED_AT, ACTIVE, MEM_ID)
VALUES
    ('SCH_000000001', '쏘렌토 신차 발표회', '신차 발표회 준비 및 진행', 'MEETING', '2024-11-01 09:00:00', '2024-11-01 12:00:00',
     '2024-01-20 10:00:00', '2024-01-20 10:30:00', NULL, TRUE, 'MEM_000000001'),
    ('SCH_000000002', '스포티지 고객 상담', '신차 구매 고객 상담 일정', 'CONSULTATION', '2024-11-05 10:00:00', '2024-11-05 11:30:00',
     '2024-01-21 11:00:00', '2024-01-21 11:30:00', NULL, TRUE, 'MEM_000000002'),
    ('SCH_000000003', 'K7 프로모션 회의', '프로모션 기획 및 준비 회의', 'MEETING', '2024-11-10 13:00:00', '2024-11-10 15:00:00',
     '2024-01-22 12:00:00', '2024-01-22 12:30:00', NULL, TRUE, 'MEM_000000003'),
    ('SCH_000000004', '셀토스 시승 이벤트', '고객 대상 시승 이벤트', 'CONSULTATION', '2024-11-12 14:00:00', '2024-11-12 17:00:00',
     '2024-01-23 13:00:00', '2024-01-23 13:30:00', NULL, TRUE, 'MEM_000000004'),
    ('SCH_000000005', 'K3 서비스 교육', '서비스 센터 직원 교육', 'TRAINING', '2024-11-15 09:00:00', '2024-11-15 12:00:00',
     '2024-01-24 14:00:00', '2024-01-24 14:30:00', NULL, TRUE, 'MEM_000000005'),
    ('SCH_000000006', '모하비 유지보수 회의', '유지보수 전략 논의', 'MEETING', '2024-11-17 10:00:00', '2024-11-17 11:00:00',
     '2024-01-25 15:00:00', '2024-01-25 15:30:00', NULL, TRUE, 'MEM_000000006'),
    ('SCH_000000007', 'K8 내부 검토', '신차 내부 디자인 검토', 'MEETING', '2024-11-20 11:00:00', '2024-11-20 13:00:00',
     '2024-01-26 16:00:00', '2024-01-26 16:30:00', NULL, TRUE, 'MEM_000000007'),
    ('SCH_000000008', '스팅어 기술 세미나', '스팅어 기술 세미나 및 발표', 'TRAINING', '2024-11-22 14:00:00', '2024-11-22 16:00:00',
     '2024-01-27 17:00:00', '2024-01-27 17:30:00', NULL, TRUE, 'MEM_000000008'),
    ('SCH_000000009', '연말 휴가', '연말 휴가 계획', 'VACATION', '2024-11-25 09:00:00', '2024-11-25 17:00:00',
     '2024-01-28 18:00:00', '2024-01-28 18:30:00', NULL, TRUE, 'MEM_000000009'),
    ('SCH_000000010', 'K5 재고 점검', 'K5 재고 관리 및 점검', 'MEETING', '2024-11-28 15:00:00', '2024-11-28 17:00:00',
     '2024-01-29 19:00:00', '2024-01-29 19:30:00', NULL, TRUE, 'MEM_000000010');

# INSERT INTO tb_alarm (ALR_ID, ALR_MSG, ALR_URL, ALR_TYPE, ALR_READ_STAT, CREATED_AT, ALR_STAT, MEM_ID)
# VALUES ('ALR_000000001', '신차 출시 공지 알림', '/notices/1', FALSE, '2024-01-20 12:00:00', 'MEM_000000001'),
#        ('ALR_000000002', '스포티지 리콜 안내', '/notices/2', FALSE, '2024-01-21 14:00:00', 'MEM_000000002'),
#        ('ALR_000000003', 'K7 고객 이벤트 초대', '/events/3', TRUE, '2024-01-22 09:00:00', 'MEM_000000003'),
#        ('ALR_000000004', '셀토스 정비 완료', '/service/4', FALSE, '2024-01-23 10:00:00', 'MEM_000000004'),
#        ('ALR_000000005', 'K3 테스트 드라이브 일정 변경', '/schedules/5', TRUE, '2024-01-24 11:00:00', 'MEM_000000005'),
#        ('ALR_000000006', '모하비 부품 입고 알림', '/inventory/6', FALSE, '2024-01-25 12:00:00', 'MEM_000000006'),
#        ('ALR_000000007', 'K8 디자인 변경 확정', '/design/7', TRUE, '2024-01-26 13:00:00', 'MEM_000000007'),
#        ('ALR_000000008', '스팅어 성능 테스트 보고서', '/reports/8', FALSE, '2024-01-27 14:00:00', 'MEM_000000008'),
#        ('ALR_000000009', '니로 전기차 충전소 위치', '/maps/9', TRUE, '2024-01-28 15:00:00', 'MEM_000000009'),
#        ('ALR_000000010', 'K5 재고 부족 경고', '/inventory/10', FALSE, '2024-01-29 16:00:00', 'MEM_000000010');

INSERT INTO tb_family (FAM_ID, FAM_NAME, FAM_REL, FAM_BIR, FAM_IDEN_NO, FAM_PHO, FAM_SEX, FAM_DIS, FAM_DIE, FAM_NOTE, CREATED_AT, UPDATED_AT, MEM_ID)
VALUES
    ('FAM_000000001', '김영희', '배우자', '1988-03-25', '880325-1234567', '010-1234-5678', 'FEMALE', FALSE, FALSE, NULL, '2024-01-10 10:00:00', '2024-01-10 11:00:00', 'MEM_000000001'),
    ('FAM_000000002', '이수진', '자녀', '2015-05-12', '150512-2345678', '010-2345-6789', 'FEMALE', FALSE, FALSE, '초등학교 3학년', '2024-01-11 12:00:00', '2024-01-11 13:00:00', 'MEM_000000002'),
    ('FAM_000000003', '박철수', '배우자', '1985-07-11', '850711-3456789', '010-3456-7890', 'MALE', FALSE, FALSE, NULL, '2024-01-12 14:00:00', '2024-01-12 15:00:00', 'MEM_000000003'),
    ('FAM_000000004', '최민수', '자녀', '2012-11-03', '121103-4567890', '010-4567-8901', 'MALE', TRUE, FALSE, '특수 교육 필요', '2024-01-13 09:00:00', '2024-01-13 10:00:00', 'MEM_000000004'),
    ('FAM_000000005', '정수정', '배우자', '1990-09-20', '900920-5678901', '010-5678-9012', 'FEMALE', FALSE, FALSE, NULL, '2024-01-14 11:00:00', '2024-01-14 12:00:00', 'MEM_000000005'),
    ('FAM_000000006', '김지훈', '자녀', '2010-06-15', '100615-6789012', '010-6789-0123', 'MALE', FALSE, FALSE, '중학교 1학년', '2024-01-15 13:00:00', '2024-01-15 14:00:00', 'MEM_000000006'),
    ('FAM_000000007', '한지민', '배우자', '1987-02-28', '870228-7890123', '010-7890-1234', 'FEMALE', FALSE, FALSE, NULL, '2024-01-16 15:00:00', '2024-01-16 16:00:00', 'MEM_000000007'),
    ('FAM_000000008', '이동수', '자녀', '2017-12-10', '171210-8901234', '010-8901-2345', 'MALE', FALSE, FALSE, '유치원생', '2024-01-17 17:00:00', '2024-01-17 18:00:00', 'MEM_000000008'),
    ('FAM_000000009', '윤소희', '배우자', '1989-04-03', '890403-9012345', '010-9012-3456', 'FEMALE', FALSE, FALSE, NULL, '2024-01-18 19:00:00', '2024-01-18 20:00:00', 'MEM_000000009'),
    ('FAM_000000010', '박성준', '자녀', '2014-08-25', '140825-0123456', '010-0123-4567', 'MALE', FALSE, FALSE, '초등학교 5학년', '2024-01-19 09:00:00', '2024-01-19 10:00:00', 'MEM_000000010');

INSERT INTO tb_education (EDU_ID, EDU_ENTD, EDU_GRAD, EDU_NAME, EDU_MJR, EDU_SCO, EDU_NOTE, CREATED_AT, UPDATED_AT, MEM_ID)
VALUES
    ('EDU_000000001', '2006-03-01', '2010-02-28', '서울대학교', '경영학', '3.8', NULL, '2024-01-10 10:00:00', '2024-01-10 11:00:00', 'MEM_000000001'),
    ('EDU_000000002', '2008-03-01', '2012-02-28', '연세대학교', '경제학', '3.5', '졸업 논문 우수상', '2024-01-11 12:00:00', '2024-01-11 13:00:00', 'MEM_000000002'),
    ('EDU_000000003', '2005-03-01', '2009-02-28', '고려대학교', '컴퓨터공학', '3.9', NULL, '2024-01-12 14:00:00', '2024-01-12 15:00:00', 'MEM_000000003'),
    ('EDU_000000004', '2010-03-01', '2014-02-28', '서강대학교', '심리학', '3.6', '사회봉사 활동 참여', '2024-01-13 09:00:00', '2024-01-13 10:00:00', 'MEM_000000004'),
    ('EDU_000000005', '2007-03-01', '2011-02-28', '성균관대학교', '화학공학', '3.7', NULL, '2024-01-14 11:00:00', '2024-01-14 12:00:00', 'MEM_000000005'),
    ('EDU_000000006', '2009-03-01', '2013-02-28', '한양대학교', '전자공학', '4.0', '학과 대표', '2024-01-15 13:00:00', '2024-01-15 14:00:00', 'MEM_000000006'),
    ('EDU_000000007', '2004-03-01', '2008-02-28', '이화여자대학교', '디자인', '3.8', '졸업 작품전 참여', '2024-01-16 15:00:00', '2024-01-16 16:00:00', 'MEM_000000007'),
    ('EDU_000000008', '2011-03-01', '2015-02-28', '중앙대학교', '물리학', '3.4', NULL, '2024-01-17 17:00:00', '2024-01-17 18:00:00', 'MEM_000000008'),
    ('EDU_000000009', '2003-03-01', '2007-02-28', '부산대학교', '수학', '3.9', '우수 장학금 수상', '2024-01-18 19:00:00', '2024-01-18 20:00:00', 'MEM_000000009'),
    ('EDU_000000010', '2012-03-01', '2016-02-28', '경북대학교', '환경공학', '3.6', NULL, '2024-01-19 09:00:00', '2024-01-19 10:00:00', 'MEM_000000010');

INSERT INTO tb_certification (CER_ID, CER_DATE, CER_INST, CER_NAME, CER_SCO, CER_NOTE, CREATED_AT, MEM_ID)
VALUES
    ('CER_000000001', '2018-06-20', '한국무역협회', '무역영어 1급', 'PASS', NULL, '2024-01-10 10:00:00', 'MEM_000000001'),
    ('CER_000000002', '2019-03-15', '한국산업인력공단', '정보처리기사', 'PASS', '필기 및 실기 합격', '2024-01-11 12:00:00', 'MEM_000000002'),
    ('CER_000000003', '2017-11-05', '대한상공회의소', '전산회계 2급', '90', NULL, '2024-01-12 14:00:00', 'MEM_000000003'),
    ('CER_000000004', '2020-08-10', '국제공인회계사협회', 'CPA', 'PASS', '국제 회계 자격증', '2024-01-13 09:00:00', 'MEM_000000004'),
    ('CER_000000005', '2016-02-22', '대한건설협회', '건축기사', 'PASS', NULL, '2024-01-14 11:00:00', 'MEM_000000005'),
    ('CER_000000006', '2021-05-30', '한국능률협회', 'PMP', 'PASS', '프로젝트 관리 자격증', '2024-01-15 13:00:00', 'MEM_000000006'),
    ('CER_000000007', '2015-12-12', '한국관광공사', '국내여행안내사', '85', NULL, '2024-01-16 15:00:00', 'MEM_000000007'),
    ('CER_000000008', '2018-09-18', '한국소방안전협회', '소방안전관리자 1급', 'PASS', NULL, '2024-01-17 17:00:00', 'MEM_000000008'),
    ('CER_000000009', '2022-04-25', '대한적십자사', '응급처치 강사', 'PASS', '응급처치 교육 수료', '2024-01-18 19:00:00', 'MEM_000000009'),
    ('CER_000000010', '2019-11-11', '한국정보통신기술협회', '정보보안기사', 'PASS', NULL, '2024-01-19 09:00:00', 'MEM_000000010');

INSERT INTO tb_career (CAR_ID, CAR_EMP_DATE, CAR_RTR_DATE, CAR_NAME, CAR_NOTE, CREATED_AT, MEM_ID)
VALUES
    ('CAR_000000001', '2012-03-01', '2016-12-31', '삼성전자', '마케팅팀 근무', '2024-01-10 10:00:00', 'MEM_000000001'),
    ('CAR_000000002', '2015-05-01', '2019-08-31', 'LG화학', '연구개발팀 근무', '2024-01-11 12:00:00', 'MEM_000000002'),
    ('CAR_000000003', '2010-09-01', '2015-02-28', '포스코', '공정관리팀', '2024-01-12 14:00:00', 'MEM_000000003'),
    ('CAR_000000004', '2013-01-01', '2018-03-31', '현대자동차', '생산기술팀', '2024-01-13 09:00:00', 'MEM_000000004'),
    ('CAR_000000005', '2017-06-01', '2021-11-30', 'SK텔레콤', '네트워크 운영팀', '2024-01-14 11:00:00', 'MEM_000000005'),
    ('CAR_000000006', '2011-02-01', '2016-07-31', '네이버', '프론트엔드 개발자', '2024-01-15 13:00:00', 'MEM_000000006'),
    ('CAR_000000007', '2014-04-01', '2019-12-31', '카카오', '백엔드 개발자', '2024-01-16 15:00:00', 'MEM_000000007'),
    ('CAR_000000008', '2018-01-01', '2022-06-30', 'CJ제일제당', '품질관리팀', '2024-01-17 17:00:00', 'MEM_000000008'),
    ('CAR_000000009', '2010-07-01', '2014-09-30', '롯데케미칼', '안전관리팀', '2024-01-18 19:00:00', 'MEM_000000009'),
    ('CAR_000000010', '2016-03-01', '2020-10-31', '한화생명', '재무팀', '2024-01-19 09:00:00', 'MEM_000000010');

INSERT INTO tb_update_history (UPD_ID, UPD_IP, UPDATED_AT, UPDATED_URL, MEM_ID, CONR_ID)
VALUES ('UPD_000000001', '192.168.1.10', '2024-01-10 12:00:00', '/contracts/1', 'MEM_000000001', 'CON_000000001'),
       ('UPD_000000002', '192.168.1.20', '2024-01-11 14:00:00', '/contracts/2', 'MEM_000000002', 'CON_000000002'),
       ('UPD_000000003', '192.168.1.30', '2024-01-12 16:00:00', '/contracts/3', 'MEM_000000003', 'CON_000000003'),
       ('UPD_000000004', '192.168.1.40', '2024-01-13 09:00:00', '/contracts/4', 'MEM_000000004', 'CON_000000004'),
       ('UPD_000000005', '192.168.1.50', '2024-01-14 10:00:00', '/contracts/5', 'MEM_000000005', 'CON_000000005'),
       ('UPD_000000006', '192.168.1.60', '2024-01-15 11:00:00', '/contracts/6', 'MEM_000000006', 'CON_000000006'),
       ('UPD_000000007', '192.168.1.70', '2024-01-16 12:00:00', '/contracts/7', 'MEM_000000007', 'CON_000000007'),
       ('UPD_000000008', '192.168.1.80', '2024-01-17 13:00:00', '/contracts/8', 'MEM_000000008', 'CON_000000008'),
       ('UPD_000000009', '192.168.1.90', '2024-01-18 14:00:00', '/contracts/9', 'MEM_000000009', 'CON_000000009'),
       ('UPD_000000010', '192.168.1.100', '2024-01-19 15:00:00', '/contracts/10', 'MEM_000000010', 'CON_000000010');

INSERT INTO tb_evaluation (EVAL_ID, EVAL_TTL, EVAL_CONT, CREATED_AT, UPDATED_AT, ACTIVE, CENT_ID, MEM_ID, WRI_ID)
VALUES ('EVAL_000000001', '쏘렌토 성능 평가', '쏘렌토의 성능에 대한 평가입니다.', '2024-01-20 10:00:00', '2024-01-21 10:30:00', TRUE,
        'CEN_000000001', 'MEM_000000001', 'MEM_000000002'),
       ('EVAL_000000002', '스포티지 안전 평가', '스포티지의 안전성 평가 보고서.', '2024-01-21 11:00:00', '2024-01-22 11:30:00', TRUE,
        'CEN_000000002', 'MEM_000000002', 'MEM_000000003'),
       ('EVAL_000000003', 'K7 고객 만족도 평가', '고객 만족도 조사 결과입니다.', '2024-01-23 12:00:00', '2024-01-24 12:30:00', TRUE,
        'CEN_000000003', 'MEM_000000003', 'MEM_000000004'),
       ('EVAL_000000004', '셀토스 디자인 평가', '셀토스 디자인 피드백.', '2024-01-25 13:00:00', '2024-01-26 13:30:00', TRUE,
        'CEN_000000004', 'MEM_000000004', 'MEM_000000005'),
       ('EVAL_000000005', 'K3 내부 공간 평가', '내부 공간의 효율성 평가.', '2024-01-27 14:00:00', '2024-01-28 14:30:00', TRUE,
        'CEN_000000005', 'MEM_000000005', 'MEM_000000006'),
       ('EVAL_000000006', '모하비 유지비 평가', '유지 비용과 효율성 평가.', '2024-01-29 15:00:00', '2024-01-30 15:30:00', TRUE,
        'CEN_000000006', 'MEM_000000006', 'MEM_000000007'),
       ('EVAL_000000007', 'K8 디자인 리뉴얼', '새로운 디자인에 대한 평가.', '2024-02-01 16:00:00', '2024-02-02 16:30:00', TRUE,
        'CEN_000000007', 'MEM_000000007', 'MEM_000000008'),
       ('EVAL_000000008', '스팅어 출력 평가', '스팅어의 출력 성능 보고서.', '2024-02-03 17:00:00', '2024-02-04 17:30:00', TRUE,
        'CEN_000000008', 'MEM_000000008', 'MEM_000000009'),
       ('EVAL_000000009', '니로 전기차 평가', '전기차 효율성 평가 결과.', '2024-02-05 18:00:00', '2024-02-06 18:30:00', TRUE,
        'CEN_000000009', 'MEM_000000009', 'MEM_000000010'),
       ('EVAL_000000010', 'K5 연비 평가', 'K5의 연비 효율성 보고서.', '2024-02-07 19:00:00', '2024-02-08 19:30:00', TRUE,
        'CEN_000000010', 'MEM_000000010', 'MEM_000000001');

INSERT INTO tb_promotion (PROM_ID, PROM_TTL, PROM_CONT, CREATED_AT, UPDATED_AT, ACTIVE, MEM_ID)
VALUES ('PROM_000000001', '쏘렌토 겨울 특별 할인', '쏘렌토 구매 시 15% 할인 제공', '2024-01-05 09:00:00', '2024-01-06 10:00:00', TRUE,
        'MEM_000000001'),
       ('PROM_000000002', '스포티지 리스 프로모션', '리스 계약 시 추가 혜택 제공', '2024-01-10 11:00:00', '2024-01-10 12:00:00', TRUE,
        'MEM_000000002'),
       ('PROM_000000003', 'K7 고객 감사 이벤트', 'K7 구매 고객 대상 사은품 증정', '2024-01-15 14:00:00', '2024-01-15 15:00:00', TRUE,
        'MEM_000000003'),
       ('PROM_000000004', '셀토스 한정 프로모션', '셀토스 구매 시 무료 보증 연장', '2024-01-20 09:00:00', '2024-01-20 10:00:00', TRUE,
        'MEM_000000004'),
       ('PROM_000000005', 'K3 연비 보장 이벤트', 'K3 연비 테스트 이벤트 참여 시 혜택 제공', '2024-01-25 13:00:00', '2024-01-25 14:00:00',
        TRUE, 'MEM_000000005'),
       ('PROM_000000006', '모하비 프리미엄 서비스', '모하비 구매 고객 대상 프리미엄 서비스 제공', '2024-01-30 11:00:00', '2024-01-31 12:00:00',
        TRUE, 'MEM_000000006'),
       ('PROM_000000007', 'K8 럭셔리 패키지 할인', '럭셔리 패키지 선택 시 10% 할인 제공', '2024-02-01 12:00:00', '2024-02-02 13:00:00', TRUE,
        'MEM_000000007'),
       ('PROM_000000008', '스팅어 시승 이벤트', '스팅어 시승 후 계약 시 추가 혜택', '2024-02-05 10:00:00', '2024-02-05 11:00:00', TRUE,
        'MEM_000000008'),
       ('PROM_000000009', '니로 전기차 구매 지원', '전기차 구매 시 충전기 설치 지원', '2024-02-10 09:00:00', '2024-02-10 10:00:00', TRUE,
        'MEM_000000009'),
       ('PROM_000000010', 'K5 재고 한정 특별 할인', '재고 한정 K5 모델에 대한 추가 할인 제공', '2024-02-15 15:00:00', '2024-02-16 16:00:00',
        TRUE, 'MEM_000000010');

INSERT INTO tb_product_option (PROD_ID, PROD_SER_NO, OPT_CNTY, OPT_MNFR, OPT_VHC_TYPE, OPT_CHSS, OPT_DTIL_TYPE,
                               OPT_BODY_TYPE,
                               OPT_SFTY_DVCE, OPT_ENGN_CPCT, OPT_SCRT_CODE, OPT_PRDC_YEAR, OPT_PRDC_PLNT, OPT_ENGN,
                               OPT_MSSN,
                               OPT_TRIM, OPT_XTNL_COLR, OPT_ITNL_COLR, OPT_HUD, OPT_NAVI, OPT_DRVE_WISE, OPT_SMRT_CNCT,
                               OPT_STYL, OPT_MY_CFRT_PCKG, OPT_OTDR_PCKG, OPT_SUN_ROOF, OPT_SOND, ACTIVE)
VALUES
-- 데이터 1
('PRO_000000001', 'KNAHAA4AALU1A00001', 'K', 'N', 'A', 'A', 'L', '4', '2', 'A', 'P', 'A', 'U', '1', '0', '1', 'B', 'W',
 '1', '1', '1', '1', '1', '0', '1', '1', '1', TRUE),
-- 데이터 2
('PRO_000000002', 'KNAHBA4BALR2Z00002', 'K', 'N', 'H', 'B', 'L', '4', '4', 'B', 'R', 'B', 'Z', '1', '1', '0', 'G', 'B',
 '0', '1', '0', '1', '0', '1', '0', '0', '1', TRUE),
-- 데이터 3
('PRO_000000003', 'KMBHC64CAMJ5A00003', 'K', 'M', 'H', 'C', 'M', '6', '3', 'C', 'P', 'C', 'A', '0', '1', '1', 'R', 'G',
 '1', '1', '1', '0', '1', '1', '0', '1', '0', TRUE),
-- 데이터 4
('PRO_000000004', 'KNJFA42DALU3C00004', 'K', 'N', 'J', 'D', 'L', '2', '4', 'A', 'R', 'D', 'C', '1', '0', '0', 'B', 'R',
 '0', '0', '0', '1', '0', '0', '1', '0', '1', TRUE),
-- 데이터 5
('PRO_000000005', 'KFBGBM5EARP1M00005', 'K', 'B', 'F', 'B', 'M', '5', '1', 'B', 'P', 'E', 'M', '1', '1', '1', 'W', 'B',
 '1', '1', '0', '0', '1', '1', '0', '1', '1', TRUE),
-- 데이터 6
('PRO_000000006', 'KNHGA6BALUP7A00006', 'K', 'N', 'H', 'G', 'L', '6', '3', 'A', 'R', 'F', 'A', '0', '0', '1', 'B', 'G',
 '1', '1', '1', '1', '0', '1', '1', '0', '0', TRUE),
-- 데이터 7
('PRO_000000007', 'KNJFA34AALU4Z00007', 'K', 'N', 'J', 'A', 'M', '4', '4', 'C', 'P', 'G', 'Z', '1', '1', '0', 'R', 'W',
 '0', '0', '1', '0', '1', '0', '1', '1', '1', TRUE),
-- 데이터 8
('PRO_000000008', 'KMAHDA2AAMJ3T00008', 'K', 'M', 'H', 'D', 'L', '2', '2', 'A', 'R', 'H', 'T', '0', '0', '1', 'B', 'G',
 '1', '1', '0', '1', '0', '1', '1', '0', '0', TRUE),
-- 데이터 9
('PRO_000000009', 'KNAHCA5BALU5C00009', 'K', 'N', 'A', 'C', 'M', '5', '1', 'B', 'P', 'J', 'C', '1', '1', '0', 'R', 'R',
 '0', '1', '1', '1', '1', '0', '1', '1', '0', TRUE),
-- 데이터 10
('PRO_000000010', 'KNFHC54CAMR1A00010', 'K', 'N', 'F', 'F', 'N', '4', '3', 'C', 'R', 'K', 'A', '0', '1', '1', 'W', 'B',
 '1', '1', '0', '0', '0', '1', '0', '1', '1', TRUE);

INSERT INTO tb_sales_history (SAL_HIST_ID, CONR_ID)
VALUES ('SAL_000000001', 'CON_000000001'),
       ('SAL_000000002', 'CON_000000002'),
       ('SAL_000000003', 'CON_000000003'),
       ('SAL_000000004', 'CON_000000004'),
       ('SAL_000000005', 'CON_000000005'),
       ('SAL_000000006', 'CON_000000006'),
       ('SAL_000000007', 'CON_000000007'),
       ('SAL_000000008', 'CON_000000008'),
       ('SAL_000000009', 'CON_000000009'),
       ('SAL_000000010', 'CON_000000010');


