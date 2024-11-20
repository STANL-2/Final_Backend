package stanl_2.final_backend.domain.problem.query.dto;

import jakarta.persistence.Column;

public class ProblemDTO {
    private String problemId;

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private Boolean active;

    private String customerId;

    private String memberId;

    private String productId;

}
