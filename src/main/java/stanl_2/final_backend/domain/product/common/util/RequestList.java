package stanl_2.final_backend.domain.product.common.util;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestList<T> {
    private T data;
    private Pageable pageable;
}
