package stanl_2.final_backend.domain.center.command.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CenterRegistResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private Integer memberCount;
    private String operatingAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean active;
}
