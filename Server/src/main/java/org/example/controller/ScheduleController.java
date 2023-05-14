package org.example.controller;

import org.example.model.Schedule;
import org.example.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    public final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Schedule> getScheduleByGroup(@PathVariable Long id){
        return  new ResponseEntity<>(service.getByGroupId(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addSchedule(@RequestBody Schedule schedule, @RequestParam Long groupId, @RequestParam List<Long> lessonIds){
        service.insert(schedule, groupId, lessonIds);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("{id}/lessons/{lessonId}")
    public ResponseEntity<HttpStatus> addScheduleLesson(@PathVariable Long id, @PathVariable Long lessonId){
        service.insertLesson(id, lessonId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateSchedule(@RequestBody Schedule schedule, @PathVariable Long id){
        service.update(schedule, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/lessons")
    public ResponseEntity<HttpStatus> updateScheduleLesson(@PathVariable Long id, @RequestParam Long oldId, @RequestParam Long newId){
        service.updateLesson(id, oldId, newId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long id){
        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/lessons/{lessonId}")
    public ResponseEntity<HttpStatus> deleteScheduleLesson(@PathVariable Long id, @PathVariable Long lessonId){
        service.deleteLesson(id, lessonId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
