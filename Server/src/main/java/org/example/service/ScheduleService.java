package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Group;
import org.example.model.Lesson;
import org.example.model.Schedule;
import org.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupService groupService;
    private final LessonService lessonService;

    public ScheduleService(ScheduleRepository scheduleRepository, GroupService groupService, LessonService lessonService) {
        this.scheduleRepository = scheduleRepository;
        this.groupService = groupService;
        this.lessonService = lessonService;
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public Schedule getById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Расписание не найдено"));
    }

    public Schedule getByGroupId(Long id) {
        return scheduleRepository.findByGroupId(id).orElseThrow(() -> new ResourceNotFoundException("Расписание не найдено"));
    }

    public void insert(Schedule schedule, Long groupId, List<Long> lessonIds) {
        Group group = groupService.getById(groupId);
        List<Lesson> lessons = lessonService.getAllById(lessonIds);

        schedule.setGroup(group);
        schedule.setLessons(lessons);

        scheduleRepository.save(schedule);
    }

    public void insertLesson(Long id, Long lessonId) {
        Schedule schedule = getById(id);
        Lesson lesson = lessonService.getById(lessonId);
        List<Lesson> lessons = schedule.getLessons();

        lessons.add(lesson);
        schedule.setLessons(lessons);

        scheduleRepository.save(schedule);
    }

    public void update(Schedule schedule, Long id) {
        getById(id);

        schedule.setId(id);
        scheduleRepository.save(schedule);
    }

    public void updateLesson(Long id, Long oldId, Long newId) {
        Schedule schedule = getById(id);
        List<Lesson> lessons = schedule.getLessons();
        Lesson lesson = lessonService.getById(newId);
        lessons = lessons.stream()
                .filter(l -> !l.getId().equals(oldId))
                .collect(Collectors.toList());
        lessons.add(lesson);

        schedule.setLessons(lessons);

        scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        getById(id);

        scheduleRepository.deleteById(id);
    }

    public void deleteLesson(Long id, Long lessonId) {
        Schedule schedule = getById(id);
        List<Lesson> lessons = schedule.getLessons();

        lessons = lessons.stream()
                .filter(l -> !l.getId().equals(lessonId))
                .collect(Collectors.toList());

        schedule.setLessons(lessons);
        scheduleRepository.save(schedule);
    }
}
