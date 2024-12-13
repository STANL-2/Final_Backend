package stanl_2.final_backend.domain.promotion.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromotionModifyDTO {

    private String title;
    private String memberId;
    private String content;
    private String fileUrl;
    private String memberLoginId;

}
