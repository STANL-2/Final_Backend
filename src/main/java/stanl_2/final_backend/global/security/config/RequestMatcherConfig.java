package stanl_2.final_backend.global.security.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class RequestMatcherConfig {

    public static void configureRequestMatchers(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // 인증 없이 접근 가능한 API 설정
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/api/v1/auth/signin",
                        "/api/v1/auth/signup",
                        "/api/v1/auth/refresh"
                ).permitAll()

                // Alarm API
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm").hasAnyRole("alarm-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 회원 알림창 전체 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm/read/**").hasAnyRole("alarm-read-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 회원 읽은 알림 상세 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm/unread/**").hasAnyRole("alarm-unread-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 회원 읽지 않은 알림 상세 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm/connect").hasAnyRole("alarm-connect-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // sse 연결 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/alarm/**").hasAnyRole("alarm-*-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 회원 알림 읽음 처리 (전체)

                // Auth API
                .requestMatchers(HttpMethod.POST, "/api/v1/auth").hasAnyRole("auth-post", "GOD", "DIRECTOR", "ADMIN") // 권한 부여 (전체 except 영업사원)

                // Career API
                .requestMatchers(HttpMethod.GET, "/api/v1/career").hasAnyRole("career-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 경력 조회(접속중인 사용자) (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/career/other/**").hasAnyRole("career-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 경력 조회(with 사번) (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/career").hasAnyRole("career-post", "GOD", "DIRECTOR", "ADMIN") // 경력 등록 (영업관리자, 영업담당자)

                // Certification API
                .requestMatchers(HttpMethod.GET, "/api/v1/certification").hasAnyRole("certification-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 자격증/외국어 조회(접속중인 사용자) (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/certification/other/**").hasAnyRole("certification-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 자격증/외국어 조회(with 사번) (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/certification").hasAnyRole("certification-post", "GOD", "DIRECTOR", "ADMIN") // 자격증/외국어 등록 (영업 관리자, 영업담당자)

                // Contract API
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-get", "GOD", "DIRECTOR", "ADMIN") // 계약서 상세조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.PUT, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 수정 (전체)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 삭제 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/contract/status/**").hasAnyRole("contract-status-put", "GOD", "ADMIN") // 계약서 승인상태 수정 (관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract").hasAnyRole("contract-get", "GOD", "DIRECTOR", "ADMIN") // 계약서 전체 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.POST, "/api/v1/contract").hasAnyRole("contract-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 등록 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/search").hasAnyRole("contract-search-get", "GOD", "DIRECTOR", "ADMIN") // 계약서 검색 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee").hasAnyRole("contract-employee-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 전체 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee/**").hasAnyRole("contract-employee-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 상세조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee/search").hasAnyRole("contract-employee-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 계약서 검색 조회 (전체)

                // Customer API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customer/{customerId}").hasAnyRole("customer-id-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객정보 삭제(자기 고객만) (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/{customerId}").hasAnyRole("customer-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객정보 상세조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/list").hasAnyRole("customer-list-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객번호로 전체 목록 조회 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/customer/{customerId}").hasAnyRole("customer-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객정보 수정(자기 고객만) (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/customer").hasAnyRole("customer-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객정보 등록 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/search").hasAnyRole("customer-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 고객정보 조건별 조회 (전체)

                // Education API
                .requestMatchers(HttpMethod.GET, "/api/v1/education").hasAnyRole("education-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 학력 조회(접속중인 사용자) (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/education/other/**").hasAnyRole("education-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 학력 조회(with 사번) (전체)

                // Evaluation API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-delete", "GOD", "DIRECTOR", "ADMIN") // 평가서 삭제 (영업 관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/manager").hasAnyRole("evaluation-manager-get", "GOD", "ADMIN") // 평가서 관리자 전체 조회 (영업 관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/representative").hasAnyRole("evaluation-representative-get", "GOD", "DIRECTOR") // 평가서 담당자 전체 조회 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/representative/search").hasAnyRole("evaluation-representative-search-get", "GOD", "DIRECTOR") // 평가서 담당자 검색 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/manager/search").hasAnyRole("evaluation-manager-search-get", "GOD", "ADMIN") // 평가서 관리자 검색 (영업 관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-get", "GOD", "DIRECTOR", "ADMIN") // 평가서 상세 조회 (영업 관리자, 영업담당자)
                .requestMatchers(HttpMethod.POST, "/api/v1/evaluation").hasAnyRole("evaluation-post", "GOD", "DIRECTOR", "ADMIN") // 평가서 등록 (영업 관리자, 영업담당자)
                .requestMatchers(HttpMethod.PUT, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-put", "GOD", "DIRECTOR", "ADMIN") // 평가서 수정 (영업 관리자, 영업담당자)

                // Family API
                .requestMatchers(HttpMethod.GET, "/api/v1/family").hasAnyRole("family-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 가족 구성원 조회(접속중인 사용자) (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/family/other/{loginId}").hasAnyRole("family-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 가족 구성원 조회(with 사번) (전체)

                // Member API
                .requestMatchers(HttpMethod.GET, "/api/v1/member/authorities").hasAnyRole("GOD") // CHECK (시스템관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/member").hasAnyRole("member-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 회원 정보 조회 (전체)

                // Log API
                .requestMatchers(HttpMethod.GET, "/api/v1/log").hasAnyRole("log-get", "GOD") // 로그 조회 (시스템 관리자)

                // Notice API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/notice/{noticeId}").hasAnyRole("notice-delete", "GOD", "DIRECTOR") // 공지사항 삭제 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/notice/all").hasAnyRole("notice-all-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 공지사항 전체 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/notice/{noticeId}").hasAnyRole("notice-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 공지사항 Id로 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/notice").hasAnyRole("notice-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 공지사항 조건별 조회 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/notice/{noticeId}").hasAnyRole("notice-update", "GOD", "DIRECTOR") // 공지사항 수정 (영업담당자)
                .requestMatchers(HttpMethod.POST, "/api/v1/notice").hasAnyRole("notice-create", "GOD", "DIRECTOR") // 공지사항 작성 (영업담당자)

                // Order API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/order/{orderId}").hasAnyRole("order-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 삭제 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/order").hasAnyRole("order-get", "GOD", "DIRECTOR", "ADMIN") // 수주서 전체 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee").hasAnyRole("order-employee-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 전체 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/{orderId}").hasAnyRole("order-id-get", "GOD", "DIRECTOR", "ADMIN") // 수주서 상세 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee/{orderId}").hasAnyRole("order-employee-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 상세 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/search").hasAnyRole("order-search", "GOD", "DIRECTOR", "ADMIN") // 수주서 검색 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee/search").hasAnyRole("order-employee-search", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 검색 조회 (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/order").hasAnyRole("order-create", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 등록 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/{orderId}").hasAnyRole("order-update", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 수주서 수정 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/status/{orderId}").hasAnyRole("order-status-update", "GOD", "ADMIN") // 수주서 승인상태 변경 (영업관리자)

                // Organization API
                .requestMatchers(HttpMethod.GET, "/api/v1/organization").hasAnyRole("organization-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 사이드바 메뉴 조회 (전체)

                // Problem API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/problem/{problemId}").hasAnyRole("problem-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 문제사항 삭제 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/problem/{problemId}").hasAnyRole("problem-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 문제사항 Id로 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/problem").hasAnyRole("problem-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 문제사항 조건별 조회 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/problem/{problemId}").hasAnyRole("problem-update", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 문제사항 수정 (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/problem").hasAnyRole("problem-create", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 문제사항 작성 (전체)

                // Product API
                .requestMatchers(HttpMethod.GET, "/api/v1/product").hasAnyRole("product-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 제품 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/product/{id}").hasAnyRole("product-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 제품 상세 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/product/search").hasAnyRole("product-search", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 제품 검색 (전체)

                // Promotion API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-delete", "GOD", "DIRECTOR") // 프로모션 삭제 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 프로모션 Id로 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/promotion").hasAnyRole("promotion-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 프로모션 조건별 조회 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-update", "GOD", "DIRECTOR") // 프로모션 수정 (영업담당자)
                .requestMatchers(HttpMethod.POST, "/api/v1/promotion").hasAnyRole("promotion-create", "GOD", "DIRECTOR") // 프로모션 작성 (영업담당자)

                // Purchase-Order API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-delete", "GOD", "ADMIN") // 발주서 삭제 (영업관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order").hasAnyRole("purchase-order-get", "GOD", "DIRECTOR") // 발주서 전체 조회 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin").hasAnyRole("purchase-order-admin-get", "GOD", "ADMIN") // 발주서 전체 조회 (영업관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-id-get", "GOD", "DIRECTOR") // 발주서 상세 조회 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin/{purchaseOrderId}").hasAnyRole("purchase-order-admin-id-get", "GOD", "ADMIN") // 발주서 상세 조회 (영업관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/search").hasAnyRole("purchase-order-search", "GOD", "DIRECTOR") // 발주서 검색 조회 (영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin/search").hasAnyRole("purchase-order-admin-search", "GOD", "ADMIN") // 발주서 검색 조회 (영업관리자)
                .requestMatchers(HttpMethod.POST, "/api/v1/purchase-order").hasAnyRole("purchase-order-create", "GOD", "ADMIN") // 발주서 등록 (영업관리자)
                .requestMatchers(HttpMethod.PUT, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-update", "GOD", "ADMIN") // 발주서 수정 (영업관리자)
                .requestMatchers(HttpMethod.PUT, "/api/v1/purchase-order/stauts/{purchaseOrderId}").hasAnyRole("purchase-order-status-update", "GOD", "DIRECTOR") // 발주서 승인 상태 수정 (영업담당자)

                // Sample API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/sample/{id}").hasAnyRole("sample-delete", "GOD") // 샘플 삭제 테스트 (시스템관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/sample/detail/{id}").hasAnyRole("sample-detail-get", "GOD") // 샘플 상세 조회 테스트 (시스템관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/{scheduleId}").hasAnyRole("sample-schedule-get", "GOD") // 샘플 조회 테스트 (시스템관리자)
                .requestMatchers(HttpMethod.POST, "/api/v1/sample").hasAnyRole("sample-create", "GOD") // 샘플 요청 테스트 (시스템관리자)
                .requestMatchers(HttpMethod.PUT, "/api/v1/sample/{id}").hasAnyRole("sample-update", "GOD") // 샘플 수정 테스트 (시스템관리자)

                // Schedule API
                .requestMatchers(HttpMethod.DELETE, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 삭제 api (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-update", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 수정 api (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/schedule").hasAnyRole("schedule-create", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 등록 api (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule").hasAnyRole("schedule-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 전체 조회 api (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 상세 조회 api (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/{year}/{month}").hasAnyRole("schedule-month-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 일정 조건별(년&일) 전체 조회 api (전체)

                // Center API
                .requestMatchers(HttpMethod.GET, "/api/v1/center/{id}").hasAnyRole("center-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 영업매장 상세조회 (전체)
                .requestMatchers(HttpMethod.PUT, "/api/v1/center/{id}").hasAnyRole("center-update", "GOD", "ADMIN") // 매장 수정 (영업 관리자)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/center/{id}").hasAnyRole("center-delete", "GOD", "ADMIN") // 매장 삭제 (영업 관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/center").hasAnyRole("center-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 영업매장 조회 (전체)
                .requestMatchers(HttpMethod.POST, "/api/v1/center").hasAnyRole("center-create", "GOD", "ADMIN") // 매장 등록 (영업 관리자)
                .requestMatchers(HttpMethod.GET, "/api/v1/center/search").hasAnyRole("center-search", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 영업매장 검색 (전체)

                // SalesHistory API
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory").hasAnyRole("salesHistory-get", "GOD", "DIRECTOR", "ADMIN") // 판매내역 조회 (영업관리자, 영업담당자)
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee").hasAnyRole("salesHistory-employee-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 판매내역 조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/{salesHistoryId}").hasAnyRole("salesHistory-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 판매내역 상세조회 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search").hasAnyRole("salesHistory-statistics-search", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 사원 통계 조회기간별 검색 (전체)
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search/month").hasAnyRole("salesHistory-statistics-month-search", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN") // 사원 통계 월별 검색 (전체)

                // 그 외 나머지 시스템 관리자만 접근 가능
                .anyRequest().hasRole("GOD")
        );
    }
}


