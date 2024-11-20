package stanl_2.final_backend.domain.certification.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CertificationDTO {
    private String name;
    private String agency;
    private String acquisitionDate;
    private String score;
    private String note;
}
