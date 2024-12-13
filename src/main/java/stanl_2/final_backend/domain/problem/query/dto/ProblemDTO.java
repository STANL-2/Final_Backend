package stanl_2.final_backend.domain.problem.query.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProblemDTO {
    private String problemId;

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private Boolean active;

    private String status;

    private String customerId;

    private String memberId;

    private String productId;

    private String fileUrl;
}
