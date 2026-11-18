package com.tecnocomfenalco.edurag.eduoptimaai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Historial de asignaciones óptimas. Cada ejecución de la optimización
 * guarda una fila por asignación, agrupadas por runId.
 */
@Entity
public class Assigment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String runId;              // Identifica una ejecución completa de la optimización
    private LocalDateTime executedAt;  // Fecha y hora de la ejecución
    private Long studentId;
    private String studentName;
    private Long tutorId;
    private String tutorName;
    private String topic;
    private int score;                 // priority × academicRisk

    public Assigment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRunId() { return runId; }
    public void setRunId(String runId) { this.runId = runId; }
    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }
    public String getTutorName() { return tutorName; }
    public void setTutorName(String tutorName) { this.tutorName = tutorName; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
