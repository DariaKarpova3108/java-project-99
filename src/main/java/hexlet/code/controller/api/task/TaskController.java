package hexlet.code.controller.api.task;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService.TaskService;
import hexlet.code.specification.TaskSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskSpecification specification;

    /*    @GetMapping
        public ResponseEntity<List<TaskDTO>> getListTasks() {
            return ResponseEntity.ok()
                    .header("X-Total-Count", String.valueOf(taskService.getListTasks().size()))
                    .body(taskService.getListTasks());
        }*/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> getList(TaskParamDTO paramDTO, @RequestParam(defaultValue = "1") int page) {
        return taskService.getListTasks(paramDTO, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskCreateDTO createDTO) {
        return taskService.createTask(createDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@Valid @RequestBody TaskUpdateDTO updateDTO, @PathVariable Long id) {
        return taskService.updateTask(updateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
