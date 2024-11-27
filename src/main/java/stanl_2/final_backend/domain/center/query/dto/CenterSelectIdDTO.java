package stanl_2.final_backend.domain.center.query.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CenterSelectIdDTO {

    private String id;
    private String name;
    private String address;
    private String phone;
    private Integer memberCount;
    private String operatingAt;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Boolean active;
    private String imageUrl;
}
