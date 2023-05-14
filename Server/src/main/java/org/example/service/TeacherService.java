package org.example.service;

import org.example.model.Teacher;
import org.example.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> getAll() {
        return repository.findAll();
    }

    public Teacher getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResolutionException("Преподаватель не найден"));
    }

    public void insert(Teacher teacher) {
        repository.save(teacher);
    }

    public void update(Teacher teacher, Long id) {
        getById(id);
        teacher.setId(id);
        repository.save(teacher);
    }

    public void delete(Long id){
        getById(id);
        repository.deleteById(id);
    }
}
