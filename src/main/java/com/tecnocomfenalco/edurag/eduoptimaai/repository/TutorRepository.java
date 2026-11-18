package com.tecnocomfenalco.edurag.eduoptimaai.repository;

import com.tecnocomfenalco.edurag.eduoptimaai.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    List<Tutor> findBySpecialty(String specialty);

}
