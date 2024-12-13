package stanl_2.final_backend.domain.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NaverNewsDTO {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;
}
