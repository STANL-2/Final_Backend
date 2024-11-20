package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursorDTO {

    private Long cursorId;
    private List<AlarmQueryDTO> comment;
    private Boolean hasNext;
    private Integer size;
}
