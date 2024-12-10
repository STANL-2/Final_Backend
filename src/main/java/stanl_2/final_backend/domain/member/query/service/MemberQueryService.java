package stanl_2.final_backend.domain.member.query.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberSearchDTO;

import java.security.GeneralSecurityException;
import java.util.List;

public interface MemberQueryService {
    MemberDTO selectMemberInfo(String name) throws GeneralSecurityException;

    List<String> selectMemberByRole(String role);

    List<MemberDTO> selectMemberByCenterId(String centerId);

    List<MemberDTO> selectMemberByCenterList(List<String> centerList);

    String selectNameById(String memberId) throws GeneralSecurityException;

    List<MemberDTO> selectMemberByOrganizationId(String organizationId) throws GeneralSecurityException;

    MemberDTO selectMemberInfoById(String memberId) throws GeneralSecurityException;

    List<MemberDTO> selectMemberByName(String name) throws GeneralSecurityException;

    Page<MemberSearchDTO> selectMemberBySearch(Pageable pageable, MemberSearchDTO memberSearchDTO) throws GeneralSecurityException;

    void exportCustomerToExcel(HttpServletResponse response) throws GeneralSecurityException;

    void sendErrorMail(String loginId, Log logEntry) throws GeneralSecurityException, MessagingException;

    String selectMemberRoleById(String memberId);

//    MemberDetailDTO selectMemberDetail(String name) throws GeneralSecurityException;
}
