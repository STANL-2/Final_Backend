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
                        "/api/v1/auth/refresh",
                        "/api/v1/auth/checkmail",
                        "/api/v1/auth/checknum"
                ).permitAll()

                // Organization API
                .requestMatchers(HttpMethod.GET, "/api/v1/organization").hasAnyRole("organization-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Auth API
                .requestMatchers(HttpMethod.POST, "/api/v1/auth").hasAnyRole("grant-auth", "GOD", "DIRECTOR", "ADMIN")

                // Member API
                .requestMatchers(HttpMethod.GET, "/api/v1/member").hasAnyRole("member-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/{centerId}").hasAnyRole("member-centerId-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/centerList").hasAnyRole("member-centerList-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/info/{loginId}").hasAnyRole("member-info-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/memberInfo/{memberId}").hasAnyRole("member-memberInfo-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/organization/{organizationId}").hasAnyRole("member-organization-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/search").hasAnyRole("member-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/search/list").hasAnyRole("member-search-list-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/member/excel").hasAnyRole("member-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Education API
                .requestMatchers(HttpMethod.GET, "/api/v1/education").hasAnyRole("education-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/education/other/{loginId}").hasAnyRole("education-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Family API
                .requestMatchers(HttpMethod.GET, "/api/v1/family").hasAnyRole("family-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/family/other/{loginId}").hasAnyRole("family-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Career API
                .requestMatchers(HttpMethod.GET, "/api/v1/career").hasAnyRole("career-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/career/other/{loginId}").hasAnyRole("career-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/career").hasAnyRole("career-post", "GOD", "DIRECTOR", "ADMIN")

                // Certification API
                .requestMatchers(HttpMethod.GET, "/api/v1/certification").hasAnyRole("certification-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/certification/other/{loginId}").hasAnyRole("certification-other-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/certification").hasAnyRole("certification-post", "GOD", "DIRECTOR", "ADMIN")

                // Customer API
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/list").hasAnyRole("customer-list-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/search").hasAnyRole("customer-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/{customerId}").hasAnyRole("customer-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/contract/{customerId}").hasAnyRole("customer-contract-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/excel").hasAnyRole("customer-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/customer").hasAnyRole("customer-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/customer/{customerId}").hasAnyRole("customer-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customer/{customerId}").hasAnyRole("customer-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // News API
                .requestMatchers(HttpMethod.GET, "/api/v1/news/car").hasAnyRole("news-car-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Claude API
                .requestMatchers(HttpMethod.POST, "/api/v1/claude/summary").hasAnyRole("claude-summary-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Contract API (Employee)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee").hasAnyRole("contract-employee-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee/search").hasAnyRole("contract-employee-search-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/employee/{contractId}").hasAnyRole("contract-employee-id-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/v1/contract").hasAnyRole("contract-post", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-put", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-delete", "GOD", "EMPLOYEE", "ADMIN")

                // Contract API (Center)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/center").hasAnyRole("contract-center-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/center/search").hasAnyRole("contract-center-search-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/center/{contractId}").hasAnyRole("contract-center-id-get", "GOD", "ADMIN")

                // Contract API (General)
                .requestMatchers(HttpMethod.GET, "/api/v1/contract").hasAnyRole("contract-get", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/search").hasAnyRole("contract-search-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/{contractId}").hasAnyRole("contract-id-get", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/api/v1/contract/status/{contractId}").hasAnyRole("contract-status-id-put", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/contract/excel").hasAnyRole("contract-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")


                // Order API (Employee)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee").hasAnyRole("order-employee-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee/search").hasAnyRole("order-employee-search-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/employee/{orderId}").hasAnyRole("order-employee-id-get", "GOD", "EMPLOYEE")

                // Order API (General)
                .requestMatchers(HttpMethod.GET, "/api/v1/order/center").hasAnyRole("order-center", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/center/search").hasAnyRole("order-center-search", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/order").hasAnyRole("order-post", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/{orderId}").hasAnyRole("order-id-put", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/order/{orderId}").hasAnyRole("order-id-delete", "GOD", "EMPLOYEE", "ADMIN")

                // Order API (Search and Excel)
                .requestMatchers(HttpMethod.GET, "/api/v1/order").hasAnyRole("order-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/{orderId}").hasAnyRole("order-id-get", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/search").hasAnyRole("order-search-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/order/excel").hasAnyRole("order-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Order API (Status)
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/status/{orderId}").hasAnyRole("order-status-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")


                // Purchase Order API
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin").hasAnyRole("purchase-order-admin-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin/search").hasAnyRole("purchase-order-admin-search-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/admin/{purchaseOrderId}").hasAnyRole("purchase-order-admin-id-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/purchase-order").hasAnyRole("purchase-order-post", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-put", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-delete", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order").hasAnyRole("purchase-order-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/search").hasAnyRole("purchase-order-search-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/{purchaseOrderId}").hasAnyRole("purchase-order-id-get", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/excel").hasAnyRole("purchase-order-excel-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/purchase-order/status/{purchaseOrderId}").hasAnyRole("purchase-order-status-put", "GOD", "DIRECTOR")

                // Alarm API
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm").hasAnyRole("alarm-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm/{type}").hasAnyRole("alarm-type-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/alarm/connect").hasAnyRole("alarm-connect-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/alarm/{alarmId}").hasAnyRole("alarm-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Schedule API
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule").hasAnyRole("schedule-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/{year}/{month}").hasAnyRole("schedule-year-month-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/schedule").hasAnyRole("schedule-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/schedule/{scheduleId}").hasAnyRole("schedule-id-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Notice API
                .requestMatchers(HttpMethod.GET, "/api/v1/notice").hasAnyRole("notice-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/notice/{noticeId}").hasAnyRole("notice-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/notice/excel").hasAnyRole("notice-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/notice").hasAnyRole("notice-post", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/notice/{noticeId}").hasAnyRole("notice-id-put", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/notice/{noticeId}").hasAnyRole("notice-id-delete", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")

                // Problem API
                .requestMatchers(HttpMethod.GET, "/api/v1/problem").hasAnyRole("problem-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/problem/{problemId}").hasAnyRole("problem-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/problem/excel").hasAnyRole("problem-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/problem").hasAnyRole("problem-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/problem/{problemId}").hasAnyRole("problem-id-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/problem/status/{problemId}").hasAnyRole("problem-status-put", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/problem/{problemId}").hasAnyRole("problem-id-delete", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Promotion API
                .requestMatchers(HttpMethod.GET, "/api/v1/promotion").hasAnyRole("promotion-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/promotion/excel").hasAnyRole("promotion-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/promotion").hasAnyRole("promotion-post", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-id-put", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/promotion/{promotionId}").hasAnyRole("promotion-id-delete", "GOD", "DIRECTOR", "EMPLOYEE", "ADMIN")

                // File API
                .requestMatchers(HttpMethod.POST, "/api/v1/file").hasAnyRole("file-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Evaluation API
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/manager").hasAnyRole("evaluation-manager-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/manager/search").hasAnyRole("evaluation-manager-search-get", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/representative").hasAnyRole("evaluation-representative-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/representative/search").hasAnyRole("evaluation-representative-search-get", "GOD", "DIRECTOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-get", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/evaluation/excel").hasAnyRole("evaluation-excel-get", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/evaluation").hasAnyRole("evaluation-post", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-put", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/evaluation/{id}").hasAnyRole("evaluation-id-delete", "GOD", "ADMIN")

                // Product API
                .requestMatchers(HttpMethod.GET, "/api/v1/product/excel").hasAnyRole("product-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/product/{productId}").hasAnyRole("product-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/product/search").hasAnyRole("product-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/product").hasAnyRole("product-post", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                // Center API
                .requestMatchers(HttpMethod.GET, "/api/v1/center").hasAnyRole("center-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/center/search").hasAnyRole("center-search-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/center/searchList").hasAnyRole("center-searchList-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/center/excel").hasAnyRole("center-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/center/{centerId}").hasAnyRole("center-id-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/center").hasAnyRole("center-post", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/center/{centerId}").hasAnyRole("center-id-put", "GOD", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/center/{centerId}").hasAnyRole("center-id-delete", "GOD", "ADMIN")

                // Sales History API
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory").hasAnyRole("salesHistory-get", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/excel").hasAnyRole("salesHistory-excel-get", "GOD", "EMPLOYEE", "DIRECTOR", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics").hasAnyRole("salesHistory-statistics-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/search").hasAnyRole("salesHistory-statistics-search-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/search/month").hasAnyRole("salesHistory-statistics-search-month-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/average").hasAnyRole("salesHistory-statistics-search-average", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/all").hasAnyRole("salesHistory-statistics-search-all", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/search").hasAnyRole("salesHistory-statistics-search-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/best").hasAnyRole("salesHistory-statistics-best-post", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/mySearch").hasAnyRole("salesHistory-statistics-best-post", "GOD", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics").hasAnyRole("salesHistory-employee-statistics-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search").hasAnyRole("salesHistory-employee-statistics-search-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search/month").hasAnyRole("salesHistory-employee-statistics-search-month-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/employee/search").hasAnyRole("salesHistory-employee-search-post", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee").hasAnyRole("salesHistory-employee-get", "GOD", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search/daily").hasAnyRole("salesHistory-employee-statistics-search-daily-get", "GOD", "EMPLOYEE", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/center/search").hasAnyRole("salesHistory-statistics-center-search-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/center/search/year").hasAnyRole("salesHistory-statistics-center-search-year-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/center/search/month").hasAnyRole("salesHistory-statistics-center-search-month-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/average/employee").hasAnyRole("salesHistory-statistics-average-employee-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/average/center").hasAnyRole("salesHistory-statistics-average-center-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/all").hasAnyRole("salesHistory-statistics-all-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/all/year").hasAnyRole("salesHistory-statistics-all-year-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/statistics/all/month").hasAnyRole("salesHistory-statistics-all-month-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/search").hasAnyRole("salesHistory-search-post", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/best").hasAnyRole("salesHistory-best", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee").hasAnyRole("salesHistory-employee-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics").hasAnyRole("salesHistory-employee-statistics-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search").hasAnyRole("salesHistory-employee-statistics-search-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search/year").hasAnyRole("salesHistory-employee-statistics-search-year-get", "GOD", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/salesHistory/employee/statistics/search/month").hasAnyRole("salesHistory-employee-statistics-search-month-get", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/v1/salesHistory/employee/search").hasAnyRole("salesHistory-employee-search-post", "GOD", "EMPLOYEE")

                // DashBoard API
                .requestMatchers(HttpMethod.GET, "/api/v1/dashBoard/employee").hasAnyRole("dashboard-get", "GOD", "DIRECTOR", "ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/v1/dashBoard/admin").hasAnyRole("dashboard-get", "GOD", "DIRECTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/dashBoard/director").hasAnyRole("dashboard-get", "GOD", "DIRECTOR")

                // 그 외 나머지 시스템 관리자만 접근 가능
                .anyRequest().hasRole("GOD")
        );
    }
}


