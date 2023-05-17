package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Group;
import org.example.model.Lesson;
import org.example.model.Schedule;
import org.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            lesson.setSchedule(schedule);
        }

        schedule.setGroup(group);
        schedule.setLessons(lessons);

        scheduleRepository.save(schedule);
    }
    public void update(Schedule schedule, Long id) {
        getById(id);

        schedule.setId(id);
        scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        getById(id);

        scheduleRepository.deleteById(id);
    }

    public void deleteLesson(Long id, Long lessonId) {
        Schedule schedule = getById(id);
        Lesson lesson = lessonService.getById(lessonId);
        if (lesson.getSchedule() == schedule){
            lessonService.delete(lessonId);
        }
        scheduleRepository.save(schedule);
    }

    public void addLesson(Long id, List<Long> lessonIds) {
        Schedule schedule = getById(id);
        List<Lesson> lessons = lessonService.getAllById(lessonIds);

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            lesson.setSchedule(schedule);
            lessonService.update(lesson, lesson.getId());
            schedule.getLessons().add(lesson);
        }

        scheduleRepository.save(schedule);
    }
}
