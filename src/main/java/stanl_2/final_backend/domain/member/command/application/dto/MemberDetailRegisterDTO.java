package stanl_2.final_backend.domain.member.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MemberDetailRegisterDTO {
    private String memberDetailRelation;
    private String memberDetailName;
    private String memberDetailBirth;
    private String memberDetailIdentNo;
    private String memberDetailPhone;
    private String memberDetailSex;
    private Boolean memberDetailDisorder;
    private Boolean memberDetailDie;
    private String memberDetailNote;
    private String memberDetailEntrance;
    private String memberDetailGraduate;
    private String memberDetailFinalEdu;
    private String memberDetailEdu;
    private String memberDetailMajor;
    private String memberDetailEmpDate;
    private String memberDetailRtrDate;
    private String memberDetailCareerInfo;
    private String memberDetailCertificationDate;
    private String memberDetailCertificationInst;
    private String memberDetailCertificationName;
    private String memberDetailCertificationScore;
    private String memberId;
}
