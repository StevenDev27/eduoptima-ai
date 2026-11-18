package com.tecnocomfenalco.edurag.eduoptimaai.service;

import com.tecnocomfenalco.edurag.eduoptimaai.dto.AssignmentResult;
import com.tecnocomfenalco.edurag.eduoptimaai.model.Assigment;
import com.tecnocomfenalco.edurag.eduoptimaai.repository.AssigmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AssigmentService {

    private final AssigmentRepository repository;

    public AssigmentService(AssigmentRepository repository) {
        this.repository = repository;
    }

    /** Guarda todas las asignaciones de una ejecución bajo un mismo runId. */
    public void saveRun(List<AssignmentResult> results) {
        if (results == null || results.isEmpty()) {
            return;
        }
        String runId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        List<Assigment> rows = results.stream().map(r -> {
            Assigment a = new Assigment();
            a.setRunId(runId);
            a.setExecutedAt(now);
            a.setStudentId(r.getStudentId());
            a.setStudentName(r.getStudentName());
            a.setTutorId(r.getTutorId());
            a.setTutorName(r.getTutorName());
            a.setTopic(r.getTopic());
            a.setScore(r.getScore());
            return a;
        }).toList();
        repository.saveAll(rows);
    }

    public List<Assigment> findHistory() {
        return repository.findAllByOrderByExecutedAtDescStudentNameAsc();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
