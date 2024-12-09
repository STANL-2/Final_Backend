package stanl_2.final_backend.domain.log.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class LogExcelDTO {
    @ExcelColumnName(name = "로그 번호")
    private String logId;

    @ExcelColumnName(name = "접근한 유저")
    private String loginId;

    @ExcelColumnName(name = "트랜잭션 번호")
    private String transactionId;

    @ExcelColumnName(name = "요청 시간")
    private String requestTime;

    @ExcelColumnName(name = "요청 메소드")
    private String method;

    @ExcelColumnName(name = "URI")
    private String uri;

    @ExcelColumnName(name = "쿼리 스트링")
    private String queryString;

    @ExcelColumnName(name = "유저 소프트웨어")
    private String userAgent;

    @ExcelColumnName(name = "IP 주소")
    private String ipAddress;

    @ExcelColumnName(name = "호스트명")
    private String hostName;

    @ExcelColumnName(name = "원격포트")
    private Integer remotePort;

    @ExcelColumnName(name = "상태")
    private String status;

    @ExcelColumnName(name = "에러 메시지")
    private String errorMessage;
}
