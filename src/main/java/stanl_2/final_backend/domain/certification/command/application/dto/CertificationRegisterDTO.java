package stanl_2.final_backend.domain.certification.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CertificationRegisterDTO {
    private String memberLoginId;
    private String name;
    private String agency;
    private String acquisitionDate;
    private String note;
    private String memberId;
}
