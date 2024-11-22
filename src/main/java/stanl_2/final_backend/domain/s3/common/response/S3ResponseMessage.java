package stanl_2.final_backend.domain.s3.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class S3ResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}