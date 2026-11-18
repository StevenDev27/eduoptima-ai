# EduOptima AI

Spring Boot + PostgreSQL + FastAPI/PuLP — asignación óptima de tutores a estudiantes.

## Requisitos
- JDK 21 y Maven 3.9+
- Python 3.10+
- PostgreSQL en el puerto 5432

## 1. Base de datos (todos los sistemas)
Crea la base una sola vez (pgAdmin o `psql`):
```sql
CREATE DATABASE eduoptima_db;
```
Ajusta usuario y clave en `src/main/resources/application.properties`.

## 2. Microservicio Python (puerto 8000)
Ejecuta los comandos **uno por uno**.

### Windows (PowerShell)
```powershell
cd optimizer-api
python -m venv venv
.\venv\Scripts\Activate.ps1
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```
Si `Activate.ps1` falla por políticas de ejecución:
```powershell
Set-ExecutionPolicy -Scope CurrentUser RemoteSigned
```

### macOS
```bash
cd optimizer-api
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

### Linux
```bash
cd optimizer-api
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

Swagger: http://127.0.0.1:8000/docs

## 3. Aplicación Spring Boot (puerto 8080)
Abre **otra terminal** en la raíz del proyecto (el microservicio debe seguir corriendo).

### Windows (PowerShell)
```powershell
mvn spring-boot:run
```

### macOS
```bash
mvn spring-boot:run
```

### Linux
```bash
mvn spring-boot:run
```

App: http://localhost:8080
Cada optimización exitosa se guarda en la tabla `assigment`; el historial está en http://localhost:8080/history
(También puedes ejecutar `EduoptimaAiApplication` desde IntelliJ IDEA o VS Code.)

## 4. Prueba rápida del microservicio
Desde la raíz del proyecto, con el microservicio corriendo:

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri http://127.0.0.1:8000/optimize -Method Post -ContentType 'application/json' -Body (Get-Content optimizer-api\sample.json -Raw)
```

### macOS
```bash
curl -X POST http://127.0.0.1:8000/optimize -H 'Content-Type: application/json' -d @optimizer-api/sample.json
```

### Linux
```bash
curl -X POST http://127.0.0.1:8000/optimize -H 'Content-Type: application/json' -d @optimizer-api/sample.json
```

Resultado esperado: `status: Optimal` y `objectiveValue: 107`.
