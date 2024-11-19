package stanl_2.final_backend.domain.evaluation.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EvaluationRegistDTO {
    private String title;
    private String content;
    private String memberId;
    private String writerId;
    /* 설명. 레포지토리에서 불러오게 할지, 서비스 층에 만들지 고민 중 (centerId)*/
    private String centerId;
}
