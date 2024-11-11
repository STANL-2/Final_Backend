package stanl_2.final_backend.domain.center.command.application.dto.response;

import java.sql.Timestamp;

public class CenterDeleteResponseDTO {

    private String name;
    private String address;
    private String phone;
    private Integer memberCount;
    private String operatingAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Boolean active;
}
