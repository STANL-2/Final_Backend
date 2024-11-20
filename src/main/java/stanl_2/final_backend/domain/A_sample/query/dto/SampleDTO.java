package stanl_2.final_backend.domain.A_sample.query.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SampleDTO {
    private String id;
    private String name;
    private Integer num;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Boolean active;
}
