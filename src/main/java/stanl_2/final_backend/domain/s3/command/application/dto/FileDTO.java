package stanl_2.final_backend.domain.s3.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileDTO {
    private String fileId;
    private String fileUrl;
}
