from typing import List

import pulp
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="EduOptima AI - Optimizer API")


# ── Modelos Pydantic (mismos nombres camelCase que serializa Jackson) ──
class Student(BaseModel):
    id: int
    name: str
    topic: str          # Área temática — debe coincidir con specialty del tutor
    academicRisk: int   # Escala 1-5
    priority: int       # Escala 1-10


class Tutor(BaseModel):
    id: int
    name: str
    specialty: str      # Especialidad — debe coincidir con topic del estudiante
    maxCapacity: int    # Máximo de estudiantes que puede atender


class OptimizationRequest(BaseModel):
    students: List[Student]
    tutors: List[Tutor]


def compatible(student: Student, tutor: Tutor) -> bool:
    return student.topic.strip().lower() == tutor.specialty.strip().lower()


@app.get("/")
def health():
    return {"service": "eduoptima-optimizer", "status": "up"}


@app.post("/optimize")
def optimize(request: OptimizationRequest):
    students = request.students
    tutors = request.tutors

    # Problema de maximización
    problem = pulp.LpProblem("TutorAssignment", pulp.LpMaximize)

    # Variables binarias x[student_id, tutor_id]
    x = {}
    for student in students:
        for tutor in tutors:
            name = f"x_{student.id}_{tutor.id}"
            x[(student.id, tutor.id)] = pulp.LpVariable(name, cat="Binary")

    # Función objetivo: suma de (prioridad × riesgo) solo para pares compatibles
    problem += pulp.lpSum(
        student.priority * student.academicRisk * x[(student.id, tutor.id)]
        for student in students
        for tutor in tutors
        if compatible(student, tutor)
    )

    # R1: cada estudiante asignado a máximo un tutor
    for student in students:
        problem += pulp.lpSum(x[(student.id, tutor.id)] for tutor in tutors) <= 1

    # R2: cada tutor no supera su capacidad máxima
    for tutor in tutors:
        problem += pulp.lpSum(x[(student.id, tutor.id)] for student in students) <= tutor.maxCapacity

    # R3: cero en asignaciones sin compatibilidad temática
    for student in students:
        for tutor in tutors:
            if not compatible(student, tutor):
                problem += x[(student.id, tutor.id)] == 0

    # Resolver con CBC
    problem.solve(pulp.PULP_CBC_CMD(msg=False))

    assignments = []
    for student in students:
        for tutor in tutors:
            value = pulp.value(x[(student.id, tutor.id)])
            if value is not None and value > 0.5:
                assignments.append({
                    "studentId": student.id,
                    "studentName": student.name,
                    "tutorId": tutor.id,
                    "tutorName": tutor.name,
                    "topic": student.topic,
                    "score": student.priority * student.academicRisk,
                })

    return {
        "status": pulp.LpStatus[problem.status],
        "objectiveValue": pulp.value(problem.objective) or 0.0,
        "assignments": assignments,
    }
