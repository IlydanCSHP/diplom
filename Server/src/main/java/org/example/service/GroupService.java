package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Group;
import org.example.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public Group getById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Группа не найдена"));
    }

    public void insert(Group group) {
        groupRepository.save(group);
    }

    public void update(Group group, Long id) {
        getById(id);

        group.setId(id);
        groupRepository.save(group);
    }

    public void delete(Long id){
        getById(id);

        groupRepository.deleteById(id);
    }

}
