package hexlet.code.dto.taskstatuses;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusCreateDTO {
    @NotNull
    private String name;
    @NotNull
    private String slug;
}
