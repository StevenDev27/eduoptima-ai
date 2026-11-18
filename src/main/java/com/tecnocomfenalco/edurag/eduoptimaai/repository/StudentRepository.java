package com.tecnocomfenalco.edurag.eduoptimaai.repository;

import com.tecnocomfenalco.edurag.eduoptimaai.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTopic(String topic);
    List<Student> findByAcademicRiskGreaterThanEqual(int risk);


}
