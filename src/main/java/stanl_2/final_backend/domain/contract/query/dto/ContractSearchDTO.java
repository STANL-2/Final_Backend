package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractSearchDTO {

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
    private String status;
    private String noOfVeh;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memId;
    private String centId;
    private String custId;
    private String prodId;
    private String startAt;
    private String endAt;
    private String prodName;
}
