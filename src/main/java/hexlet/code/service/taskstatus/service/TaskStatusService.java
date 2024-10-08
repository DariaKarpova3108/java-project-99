package hexlet.code.service.taskstatus.service;

import hexlet.code.dto.taskstatuses.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatuses.TaskStatusDTO;
import hexlet.code.dto.taskstatuses.TaskStatusUpdateDTO;
import hexlet.code.exception.LinkingTasksToAnotherEntityException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusMapper taskStatusMapper;

    public List<TaskStatusDTO> getList() {
        return taskStatusRepository.findAll().stream()
                .map(taskStatusMapper::map)
                .toList();
    }

    public TaskStatusDTO getTaskStatus(Long id) {
        var model = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id: " + id + " not found"));
        return taskStatusMapper.map(model);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO updateDTO, Long id) {
        var model = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id: " + id + " not found"));
        taskStatusMapper.update(updateDTO, model);
        taskStatusRepository.save(model);
        return taskStatusMapper.map(model);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO createDTO) {
        if (taskStatusRepository.existsBySlug(createDTO.getSlug())) {
            throw new IllegalArgumentException("A status with this slug already exists");
        }
        var model = taskStatusMapper.map(createDTO);
        taskStatusRepository.save(model);
        return taskStatusMapper.map(model);
    }

    public void delete(Long id) {
        var status = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id: " + id + " not found"));
        if (!status.getListTasks().isEmpty()) {
            throw new LinkingTasksToAnotherEntityException("TaskStatus cannot be deleted, they have assigned tasks");
        }
        taskStatusRepository.deleteById(id);
    }
}
