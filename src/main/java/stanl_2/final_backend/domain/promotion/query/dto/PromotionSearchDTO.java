package stanl_2.final_backend.domain.promotion.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PromotionSearchDTO {
    private String promotionId;
    private String title;
    private String memberId;
    private String startDate;
    private String endDate;

    public PromotionSearchDTO(String title, String memberId, String startDate, String endDate) {
        this.title = title;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
