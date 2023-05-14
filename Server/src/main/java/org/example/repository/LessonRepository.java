package org.example.repository;

import org.example.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByRoomId(Long id);

    List<Lesson> findByTeacherId(Long id);

    List<Lesson> findBySubjectId(Long id);
}
