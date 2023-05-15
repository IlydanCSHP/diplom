package org.example.controller;

import org.example.model.Lesson;
import org.example.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    public final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> getLessons() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("teacher/{id}")
    public ResponseEntity<List<Lesson>> getLessonsByTeacher(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByTeacherId(id), HttpStatus.OK);
    }

    @GetMapping("room/{id}")
    public ResponseEntity<List<Lesson>> getLessonsByRoom(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByRoomId(id), HttpStatus.OK);
    }

    @GetMapping("subject/{id}")
    public ResponseEntity<List<Lesson>> getLessonsBySubject(@PathVariable Long id) {
        return new ResponseEntity<>(service.getBySubjectId(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addLesson(
            @RequestBody Lesson lesson,
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            @RequestParam Long roomId) {
        service.insert(lesson, subjectId, teacherId, roomId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateLesson(@RequestBody Lesson lesson, @PathVariable Long id) {
        service.update(lesson, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/teacher")
    public ResponseEntity<Lesson> updateLessonTeacher(@RequestParam Long teacherId, @PathVariable Long id){
        service.updateTeacher(id, teacherId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/room")
    public ResponseEntity<HttpStatus> updateLessonRoom(@PathVariable Long id, @RequestParam Long roomId){
        service.updateRoom(id, roomId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/subject")
    public ResponseEntity<HttpStatus> updateLessonSubject(@PathVariable Long id, @RequestParam Long subjectId){
        service.updateSubject(id, subjectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable Long id) {
        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
