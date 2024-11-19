package stanl_2.final_backend.domain.notices.query.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaginationDTO<T> {
    private List<T> data;
    private int totalPages;
    private long totalElements;
}
