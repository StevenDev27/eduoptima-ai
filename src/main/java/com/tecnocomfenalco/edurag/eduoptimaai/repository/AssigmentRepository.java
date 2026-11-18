package com.tecnocomfenalco.edurag.eduoptimaai.repository;

import com.tecnocomfenalco.edurag.eduoptimaai.model.Assigment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssigmentRepository extends JpaRepository<Assigment, Long> {
    List<Assigment> findAllByOrderByExecutedAtDescStudentNameAsc();
}
