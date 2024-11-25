package stanl_2.final_backend.domain.member.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;
import stanl_2.final_backend.domain.member.query.dto.MemberCenterListDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
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

    @Operation(summary = "회원 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("info/{memberId}")
    public ResponseEntity<MemberResponseMessage> getMemberInfoBymemberId(@PathVariable("memberId") String memberId) throws GeneralSecurityException {

        MemberDTO memberInfo = memberQueryService.selectMemberInfo(memberId);

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


}
