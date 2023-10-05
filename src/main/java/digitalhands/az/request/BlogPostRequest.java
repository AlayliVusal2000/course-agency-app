package digitalhands.az.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BlogPostRequest {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private Long experienceId;

}