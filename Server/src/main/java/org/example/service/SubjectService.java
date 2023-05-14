package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Subject;
import org.example.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Subject getById(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Предмет не найден"));
    }

    public void insert(Subject subject) {
        subjectRepository.save(subject);
    }

    public void update(Subject subject, Long id) {
        getById(id);

        subject.setId(id);
        subjectRepository.save(subject);
    }

    public void delete(Long id) {
        getById(id);

        subjectRepository.deleteById(id);
    }

}
