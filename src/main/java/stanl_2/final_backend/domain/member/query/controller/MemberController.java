package stanl_2.final_backend.domain.member.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;
import stanl_2.final_backend.domain.member.query.dto.MemberCenterListDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberSearchDTO;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController(value = "queryMemberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Operation(summary = "회원 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<MemberResponseMessage> getMemberInfo(Principal principal) throws GeneralSecurityException {

        MemberDTO memberInfo = memberQueryService.selectMemberInfo(principal.getName());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(memberInfo)
                                                        .build());
    }

    @Operation(summary = "회원 정보 조회(with 사번)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("info/{loginId}")
    public ResponseEntity<MemberResponseMessage> getMemberInfoBymemberId(@PathVariable("loginId") String loginId) throws GeneralSecurityException {

        MemberDTO memberInfo = memberQueryService.selectMemberInfo(loginId);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberInfo)
                .build());
    }

    @Operation(summary = "회원 정보 조건(매장) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{centerId}")
    public ResponseEntity<MemberResponseMessage> getMemberByCenterId(@PathVariable("centerId") String centerId) throws GeneralSecurityException {

        List<MemberDTO> memberList = memberQueryService.selectMemberByCenterId(centerId);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberList)
                .build());
    }

    @Operation(summary = "회원 정보 조건(매장리스트) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("centerList")
    public ResponseEntity<MemberResponseMessage> getMemberByCenterList(@RequestBody MemberCenterListDTO memberCenterListDTO) throws GeneralSecurityException {

        List<MemberDTO> memberList = memberQueryService.selectMemberByCenterList(memberCenterListDTO.getCenterList());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberList)
                .build());
    }


    @Operation(summary = "회원 정보 조건(부서) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<MemberResponseMessage> getMemberByOrganizationId(@PathVariable("organizationId") String organizationId) throws GeneralSecurityException {

        List<MemberDTO> memberList = memberQueryService.selectMemberByOrganizationId(organizationId);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberList)
                .build());
    }


    @Operation(summary = "회원 id로 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/memberInfo/{memberId}")
    public ResponseEntity<MemberResponseMessage> getMemberInfoById(@PathVariable("memberId") String memberId) throws GeneralSecurityException {

        MemberDTO memberInfo = memberQueryService.selectMemberInfoById(memberId);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberInfo)
                .build());
    }

    @Operation(summary = "회원 정보 조건(사원이름) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("search")
    public ResponseEntity<MemberResponseMessage> getMemberByName(@RequestParam(required = true) String name) throws GeneralSecurityException {

        List<MemberDTO> memberList = memberQueryService.selectMemberByName(name);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberList)
                .build());
    }


    @Operation(summary = "회원 정보 검색 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("search/list")
    public ResponseEntity<MemberResponseMessage> getMemberByName(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String centerName,
            @RequestParam(required = false) String organizationName,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @PageableDefault(size = 10) Pageable pageable
    ) throws GeneralSecurityException {

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        MemberSearchDTO memberSearchDTO = new MemberSearchDTO(loginId, memberName, phone, email, centerName, organizationName);
        Page<MemberSearchDTO> memberDTOPage = memberQueryService.selectMemberBySearch(pageable, memberSearchDTO);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(memberDTOPage)
                                                      .build());
    }


    @Operation(summary = "엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("excel")
    public void exportCustomer(HttpServletResponse response) throws GeneralSecurityException {

        memberQueryService.exportCustomerToExcel(response);
    }


}
