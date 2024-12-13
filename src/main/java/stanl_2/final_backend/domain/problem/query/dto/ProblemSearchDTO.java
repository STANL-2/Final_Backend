package stanl_2.final_backend.domain.problem.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProblemSearchDTO {
    private String problemId;
    private String title;
    private String content;
    private String memberId;
    private String productId;
    private String customerId;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String startDate;
    private String endDate;
    private String status;

    public ProblemSearchDTO(String title, String memberId, String productId, String customerId, String startDate, String endDate) {
        this.title = title;
        this.memberId = memberId;
        this.productId = productId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
