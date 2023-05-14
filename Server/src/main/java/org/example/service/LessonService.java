package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Lesson;
import org.example.model.Room;
import org.example.model.Subject;
import org.example.model.Teacher;
import org.example.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final RoomService roomService;


    public LessonService(LessonRepository lessonRepository,
                         SubjectService subjectService,
                         TeacherService teacherService,
                         RoomService roomService) {
        this.lessonRepository = lessonRepository;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.roomService = roomService;
    }

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Занятие не найдено"));
    }

    public List<Lesson> getAllById(Iterable<Long> ids) {
        return lessonRepository.findAllById(ids);
    }

    public List<Lesson> getByRoomId(Long id) {
        return lessonRepository.findByRoomId(id);
    }

    public List<Lesson> getByTeacherId(Long id) {
        return lessonRepository.findByTeacherId(id);
    }

    public List<Lesson> getBySubjectId(Long id) {
        return lessonRepository.findBySubjectId(id);
    }

    public void insert(Lesson lesson, Long subjectId, Long teacherId, Long roomId) {
        Subject subject = subjectService.getById(subjectId);
        Teacher teacher = teacherService.getById(teacherId);
        Room room = roomService.getById(roomId);

        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setRoom(room);

        lessonRepository.save(lesson);
    }

    public void update(Lesson lesson, Long id) {
        getById(id);

        lesson.setId(id);
        lessonRepository.save(lesson);
    }

    public void updateTeacher(Long id, Long teacherId) {
        Lesson lesson = getById(id);
        Teacher teacher = teacherService.getById(teacherId);
        lesson.setTeacher(teacher);

        lessonRepository.save(lesson);
    }

    public void updateRoom(Long id, Long roomId) {
        Lesson lesson = getById(id);
        Room room = roomService.getById(roomId);
        lesson.setRoom(room);

        lessonRepository.save(lesson);
    }

    public void updateSubject(Long id, Long subjectId) {
        Lesson lesson = getById(id);
        Subject subject = subjectService.getById(subjectId);
        lesson.setSubject(subject);

        lessonRepository.save(lesson);
    }
    public void delete(Long id) {
        getById(id);

        lessonRepository.deleteById(id);
    }

}
