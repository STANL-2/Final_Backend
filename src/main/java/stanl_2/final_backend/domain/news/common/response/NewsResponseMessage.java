package stanl_2.final_backend.domain.news.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NewsResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}