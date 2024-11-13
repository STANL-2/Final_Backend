package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ContractModifyDTO {

    private String id;
    private String name;
    private String custName;
    private String custIdenNo;
    private String custAddrress;
    private String custEmail;
    private String custPhone;
    private String compName;
    private String custCla;
    private String custPurCond;
    private String seriNum;
    private String seleOpti;
    private Integer downPay;
    private Integer intePay;
    private Integer remPay;
    private Integer consPay;
    private String delvDate;
    private String delvLoc;
    private String state;
    private String noOfVeh;
    private String createdUrl;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String memId;
    private String centId;
    private String custId;
    private String prodId;
}
