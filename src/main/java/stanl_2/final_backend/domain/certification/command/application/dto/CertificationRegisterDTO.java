package stanl_2.final_backend.domain.certification.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CertificationRegisterDTO {
    private String acquisitionDate;
    private String agency;
    private String name;
    private String score;
    private String note;
    private String memberId;
}
