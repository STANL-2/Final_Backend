package stanl_2.final_backend.domain.order.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.service.OrderCommandService;
import stanl_2.final_backend.domain.order.common.response.OrderResponseMessage;

@RestController("OrderCommandController")
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderCommandService orderCommandService;

    @Autowired
    public OrderController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    /**
     * [POST] http://localhost:8080/api/v1/order
     * Request
     *  {
     *   "title": "241115 셀토스 계약 주문",
     *   "content": "<!DOCTYPE html>\n<html lang=\\\"ko\\\">\n<head>\n    <meta charset=\\\"UTF-8\\\">\n    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">\n    <title>자동차 매매 계약서</title>\n    <style>* {margin: 0; padding: 0; box-sizing: border-box;} body {font-family: 'Noto Sans KR', sans-serif; background-color: #f9f9f9; padding: 20px;} .container {max-width: 800px; margin: auto; background-color: white; border: 1px solid #ddd; padding: 20px; border-radius: 8px;} .header {text-align: center; margin-bottom: 20px;} .logo {width: 80px; margin-bottom: 10px;} h1 {font-size: 24px; margin-bottom: 10px;} .section {margin-top: 20px;} .section h2 {background-color: #333; color: #fff; padding: 10px; font-size: 18px;} table {width: 100%; border-collapse: collapse; margin-top: 10px;} th, td {border: 1px solid #ddd; padding: 8px; text-align: left;} th {background-color: #f0f0f0; font-weight: bold;} .masked {background-color: #eee; color: transparent; text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);} .signature {margin-top: 30px;} .sign-area {display: flex; justify-content: space-between; margin-top: 20px;} .info-table {width: 100%; border-collapse: collapse; margin-top: 10px; background-color: #f9f9f9; border: 1px solid #ddd;} .info-table th, .info-table td {border: 1px solid #ddd; padding: 10px; text-align: left;} .info-table th {background-color: #f0f0f0; font-weight: bold; width: 11%;} .info-table td {width: 13%;}</style>\n</head>\n<body>\n    <div class=\\\"container\\\">\n        <div class=\\\"header\\\">\n            <h1>기아 자동차 매매 계약서</h1>\n        </div>\n        <section class=\\\"section\\\">\n            <h2>계약 정보</h2>\n            <table class=\\\"info-table\\\">\n                <tr><th rowspan=\\\"2\\\">계약 번호</th><td rowspan=\\\"2\\\">KL-JS</td><th>계약일</th><td>2022-11-17</td><th>계약장소</th><td>서울 강남구</td></tr>\n                <tr><th>담당자</th><td>유혜진</td><th>전화번호</th><td>010-7158-8796</td></tr>\n            </table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>고객사항</h2>\n            <table><tr><th>성명</th><td>홍길동</td><th>상호</th><td></td></tr><tr><th>주민등록번호</th><td>****-*******</td><th>사업자등록번호</th><td></td></tr><tr><th>주소</th><td>**********</td><th>사업자등록주소</th><td>OOOOOOOO</td></tr><tr><th>전화(휴대폰)</th><td>010-****-****</td><th>구분</th><td>개인</td></tr><tr><th>E-mail</th><td>****@****.com</td><th>구매유형</th><td>현금</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>차량사항</h2>\n            <table><tr><th>차종</th><td>Q4 e-tron 40</td><th>일련번호</th><td>2Y2Y</td></tr><tr></tr><tr><th>선택옵션</th><td>AO</td><th>대수</th><td>1대</td></tr><tr><th>인도예정일</th><td>2023년 인도</td><th>인도장소</th><td>서울지점</td></tr><tr></tr><tr><th>특약사항</th><td colspan=\\\"3\\\">- 특약사항 내용이 여기에 표시됩니다.</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>금액사항</h2>\n            <table><tr><th>차량가격</th><td>66,700,000원</td></tr><tr><th>계약금</th><td>900,000원</td></tr><tr><th>중도금</th><td>100,000원</td></tr><tr><th>인도금</th><td>65,700,000원</td></tr><tr><th>탁송료</th><td>65,700,000원</td></tr></table>\n        </section>\n        <section class=\\\"section signature\\\">\n            <p>본 계약서 주요 내용을 확인하고 계약을 체결하였음을 확인합니다.</p>\n            <div class=\\\"sign-area\\\">\n                <div>매수인 (서명): ****</div>\n                <div>매도인 (서명): ****</div>\n            </div>\n        </section>\n    </div>\n</body>\n</html>",
     *   "conrId": "CON_000000001",
     *   "memId": "MEM_000000001"
     * }
     * */
    @Operation(summary = "수주서 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 등록 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<OrderResponseMessage> postOrder(@RequestBody OrderRegistDTO orderRegistDTO) {

        orderCommandService.registerOrder(orderRegistDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서가 성공적으로 등록되었습니다.")
                                                    .result(null)
                                                    .build());
    }


    /**
     * [PUT] http://localhost:8080/api/v1/order/ORD_000000011
     * Request
     *  {
     *   "title": "241115 셀토스 계약 주문 수정!!",
     *   "content": "<!DOCTYPE html>\n<html lang=\\\"ko\\\">\n<head>\n    <meta charset=\\\"UTF-8\\\">\n    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">\n    <title>자동차 매매 계약서</title>\n    <style>* {margin: 0; padding: 0; box-sizing: border-box;} body {font-family: 'Noto Sans KR', sans-serif; background-color: #f9f9f9; padding: 20px;} .container {max-width: 800px; margin: auto; background-color: white; border: 1px solid #ddd; padding: 20px; border-radius: 8px;} .header {text-align: center; margin-bottom: 20px;} .logo {width: 80px; margin-bottom: 10px;} h1 {font-size: 24px; margin-bottom: 10px;} .section {margin-top: 20px;} .section h2 {background-color: #333; color: #fff; padding: 10px; font-size: 18px;} table {width: 100%; border-collapse: collapse; margin-top: 10px;} th, td {border: 1px solid #ddd; padding: 8px; text-align: left;} th {background-color: #f0f0f0; font-weight: bold;} .masked {background-color: #eee; color: transparent; text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);} .signature {margin-top: 30px;} .sign-area {display: flex; justify-content: space-between; margin-top: 20px;} .info-table {width: 100%; border-collapse: collapse; margin-top: 10px; background-color: #f9f9f9; border: 1px solid #ddd;} .info-table th, .info-table td {border: 1px solid #ddd; padding: 10px; text-align: left;} .info-table th {background-color: #f0f0f0; font-weight: bold; width: 11%;} .info-table td {width: 13%;}</style>\n</head>\n<body>\n    <div class=\\\"container\\\">\n        <div class=\\\"header\\\">\n            <h1>기아 자동차 매매 계약서</h1>\n        </div>\n        <section class=\\\"section\\\">\n            <h2>계약 정보</h2>\n            <table class=\\\"info-table\\\">\n                <tr><th rowspan=\\\"2\\\">계약 번호</th><td rowspan=\\\"2\\\">KL-JS</td><th>계약일</th><td>2022-11-17</td><th>계약장소</th><td>서울 강남구</td></tr>\n                <tr><th>담당자</th><td>유혜진</td><th>전화번호</th><td>010-7158-8796</td></tr>\n            </table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>고객사항</h2>\n            <table><tr><th>성명</th><td>홍길동</td><th>상호</th><td></td></tr><tr><th>주민등록번호</th><td>****-*******</td><th>사업자등록번호</th><td></td></tr><tr><th>주소</th><td>**********</td><th>사업자등록주소</th><td>OOOOOOOO</td></tr><tr><th>전화(휴대폰)</th><td>010-****-****</td><th>구분</th><td>개인</td></tr><tr><th>E-mail</th><td>****@****.com</td><th>구매유형</th><td>현금</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>차량사항</h2>\n            <table><tr><th>차종</th><td>Q4 e-tron 40</td><th>일련번호</th><td>2Y2Y</td></tr><tr></tr><tr><th>선택옵션</th><td>AO</td><th>대수</th><td>1대</td></tr><tr><th>인도예정일</th><td>2023년 인도</td><th>인도장소</th><td>서울지점</td></tr><tr></tr><tr><th>특약사항</th><td colspan=\\\"3\\\">- 특약사항 내용이 여기에 표시됩니다.</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>금액사항</h2>\n            <table><tr><th>차량가격</th><td>66,700,000원</td></tr><tr><th>계약금</th><td>900,000원</td></tr><tr><th>중도금</th><td>100,000원</td></tr><tr><th>인도금</th><td>65,700,000원</td></tr><tr><th>탁송료</th><td>65,700,000원</td></tr></table>\n        </section>\n        <section class=\\\"section signature\\\">\n            <p>본 계약서 주요 내용을 확인하고 계약을 체결하였음을 확인합니다.</p>\n            <div class=\\\"sign-area\\\">\n                <div>매수인 (서명): ****</div>\n                <div>매도인 (서명): ****</div>\n            </div>\n        </section>\n    </div>\n</body>\n</html>",
     *   "memId": "MEM_000000001"
     * }
     * */
    @Operation(summary = "수주서 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 수정 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<OrderResponseMessage> putOrder(@PathVariable String id,
                                                         @RequestBody OrderModifyDTO orderModifyDTO) {

        orderModifyDTO.setOrderId(id);
        OrderModifyDTO orderModifyResponseDTO = orderCommandService.modifyOrder(orderModifyDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서가 성공적으로 수정되었습니다.")
                                                    .result(orderModifyResponseDTO)
                                                    .build());
    }

    /**
     * [PUT] http://localhost:8080/api/v1/order/ORD_000000011
     * Request
     *  {
     *   "title": "241115 셀토스 계약 주문 수정!!",
     *   "content": "<!DOCTYPE html>\n<html lang=\\\"ko\\\">\n<head>\n    <meta charset=\\\"UTF-8\\\">\n    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">\n    <title>자동차 매매 계약서</title>\n    <style>* {margin: 0; padding: 0; box-sizing: border-box;} body {font-family: 'Noto Sans KR', sans-serif; background-color: #f9f9f9; padding: 20px;} .container {max-width: 800px; margin: auto; background-color: white; border: 1px solid #ddd; padding: 20px; border-radius: 8px;} .header {text-align: center; margin-bottom: 20px;} .logo {width: 80px; margin-bottom: 10px;} h1 {font-size: 24px; margin-bottom: 10px;} .section {margin-top: 20px;} .section h2 {background-color: #333; color: #fff; padding: 10px; font-size: 18px;} table {width: 100%; border-collapse: collapse; margin-top: 10px;} th, td {border: 1px solid #ddd; padding: 8px; text-align: left;} th {background-color: #f0f0f0; font-weight: bold;} .masked {background-color: #eee; color: transparent; text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);} .signature {margin-top: 30px;} .sign-area {display: flex; justify-content: space-between; margin-top: 20px;} .info-table {width: 100%; border-collapse: collapse; margin-top: 10px; background-color: #f9f9f9; border: 1px solid #ddd;} .info-table th, .info-table td {border: 1px solid #ddd; padding: 10px; text-align: left;} .info-table th {background-color: #f0f0f0; font-weight: bold; width: 11%;} .info-table td {width: 13%;}</style>\n</head>\n<body>\n    <div class=\\\"container\\\">\n        <div class=\\\"header\\\">\n            <h1>기아 자동차 매매 계약서</h1>\n        </div>\n        <section class=\\\"section\\\">\n            <h2>계약 정보</h2>\n            <table class=\\\"info-table\\\">\n                <tr><th rowspan=\\\"2\\\">계약 번호</th><td rowspan=\\\"2\\\">KL-JS</td><th>계약일</th><td>2022-11-17</td><th>계약장소</th><td>서울 강남구</td></tr>\n                <tr><th>담당자</th><td>유혜진</td><th>전화번호</th><td>010-7158-8796</td></tr>\n            </table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>고객사항</h2>\n            <table><tr><th>성명</th><td>홍길동</td><th>상호</th><td></td></tr><tr><th>주민등록번호</th><td>****-*******</td><th>사업자등록번호</th><td></td></tr><tr><th>주소</th><td>**********</td><th>사업자등록주소</th><td>OOOOOOOO</td></tr><tr><th>전화(휴대폰)</th><td>010-****-****</td><th>구분</th><td>개인</td></tr><tr><th>E-mail</th><td>****@****.com</td><th>구매유형</th><td>현금</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>차량사항</h2>\n            <table><tr><th>차종</th><td>Q4 e-tron 40</td><th>일련번호</th><td>2Y2Y</td></tr><tr></tr><tr><th>선택옵션</th><td>AO</td><th>대수</th><td>1대</td></tr><tr><th>인도예정일</th><td>2023년 인도</td><th>인도장소</th><td>서울지점</td></tr><tr></tr><tr><th>특약사항</th><td colspan=\\\"3\\\">- 특약사항 내용이 여기에 표시됩니다.</td></tr></table>\n        </section>\n        <section class=\\\"section\\\">\n            <h2>금액사항</h2>\n            <table><tr><th>차량가격</th><td>66,700,000원</td></tr><tr><th>계약금</th><td>900,000원</td></tr><tr><th>중도금</th><td>100,000원</td></tr><tr><th>인도금</th><td>65,700,000원</td></tr><tr><th>탁송료</th><td>65,700,000원</td></tr></table>\n        </section>\n        <section class=\\\"section signature\\\">\n            <p>본 계약서 주요 내용을 확인하고 계약을 체결하였음을 확인합니다.</p>\n            <div class=\\\"sign-area\\\">\n                <div>매수인 (서명): ****</div>\n                <div>매도인 (서명): ****</div>\n            </div>\n        </section>\n    </div>\n</body>\n</html>",
     *   "memId": "MEM_000000001"
     * }
     * */
    @Operation(summary = "수주서 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseMessage> deleteTest(@PathVariable("id") String id) {

        orderCommandService.deleteOrder(id);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서를 성공적으로 삭제하였습니다.")
                                                    .result(null)
                                                    .build());
    }
}