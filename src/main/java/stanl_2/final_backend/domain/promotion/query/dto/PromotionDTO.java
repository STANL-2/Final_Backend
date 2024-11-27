package stanl_2.final_backend.domain.promotion.query.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromotionDTO {
    private String promotionId;

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private Boolean active;

    private String memberId;

    private String fileUrl;

}
