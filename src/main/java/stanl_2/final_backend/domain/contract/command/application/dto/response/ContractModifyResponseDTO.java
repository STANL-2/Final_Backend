package stanl_2.final_backend.domain.contract.command.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ContractModifyResponseDTO {
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
}
