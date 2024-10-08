package hexlet.code.service.user.service;

import hexlet.code.dto.users.UserCreateDTO;
import hexlet.code.dto.users.UserDTO;
import hexlet.code.dto.users.UserUpdateDTO;
import hexlet.code.exception.LinkingTasksToAnotherEntityException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UsersMapper;
import hexlet.code.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersMapper mapper;

    private final UsersRepository usersRepository;

    public List<UserDTO> getAll() {
        var list = usersRepository.findAll();
        return list.stream()
                .map(mapper::map)
                .toList();
    }

    public UserDTO getUser(Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found user with id: " + id));
        return mapper.map(user);
    }

    public UserDTO create(UserCreateDTO createDTO) {
        var user = mapper.map(createDTO);
        usersRepository.save(user);
        return mapper.map(user);
    }

    public UserDTO update(UserUpdateDTO updateDTO, Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found user with id: " + id));
        mapper.update(updateDTO, user);
        usersRepository.save(user);
        return mapper.map(user);
    }

    public void delete(Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found user with id: " + id));
        if (!user.getListTasks().isEmpty()) {
            throw new LinkingTasksToAnotherEntityException("User cannot be deleted, they have assigned tasks");
        }
        usersRepository.deleteById(id);
    }
}
