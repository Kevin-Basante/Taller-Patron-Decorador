# 🐎 HorseCare

HorseCare is a web application built with **Java and Spring Boot** that creates care plans for horses. It combines three design patterns:

| Pattern | Type | What it does in HorseCare |
|---|---|---|
| **Decorator** | Structural | Adds optional services (premium food, bath, training, veterinary check) on top of a base care. |
| **Factory Method** | Creational | Creates the right **base care** for each horse type (leisure, sport, foal, senior). |
| **Builder** | Creational | Assembles the complete **care plan** (profile, base care, extras, schedule and recommendations) step by step, with a Director. |

> **Credits.** The original project (Decorator pattern) was created by another team of the course. As part of the project exchange of the *Software Patterns* course, **Kevin Basante** and **Arley Riascos** added the **Factory Method** and **Builder** patterns, reorganized the project into a standard Maven structure, and deployed it to the cloud.

---

## 🔄 How the three patterns work together

```text
 Customer request: "Thunder", sport horse, Competition package
        │
        ▼
 CarePlanService ──► chooses SportHorseCareCreator      (Factory Method)
        │        ──► chooses CompetitionCarePlanBuilder (Builder)
        ▼
 CarePlanDirector.buildCarePlan()
        ├── buildHorseProfile()     name, type, package
        ├── buildBaseCare()         creator.createCare()  ──► SportHorseCare
        ├── buildExtraServices()    new PremiumFoodDecorator(...)
        │                           new TrainingDecorator(...)          (Decorator)
        │                           new VeterinaryDecorator(...)
        ├── buildSchedule()         5 sessions per week
        └── buildRecommendations()
        ▼
 CarePlan  ──►  JSON  ──►  frontend
```

The frontend shows this chain in the **"¿Cómo se construyó este plan?"** panel for every plan it creates.

---

## 🧩 Decorator pattern (original project)

| Role | Class |
|---|---|
| Component | `HorseService` |
| Concrete components | `BasicHorseCare`, `SportHorseCare`, `FoalCare`, `SeniorHorseCare` |
| Base decorator | `HorseServiceDecorator` |
| Concrete decorators | `PremiumFoodDecorator`, `BathDecorator`, `TrainingDecorator`, `VeterinaryDecorator` |

Each decorator wraps another `HorseService` and adds its own description and price.

---

## 🏭 Factory Method pattern (added)

**Problem:** every kind of horse needs a different base care. If the code that builds the plan used `new SportHorseCare()` directly, adding a new horse type would mean changing that code.

**Solution:** an abstract creator declares the factory method and each subclass decides which product to create.

| Role | Class |
|---|---|
| Product | `HorseService` |
| Concrete products | `BasicHorseCare`, `SportHorseCare`, `FoalCare`, `SeniorHorseCare` |
| Creator | `HorseCareCreator` (abstract method `createCare()`) |
| Concrete creators | `LeisureHorseCareCreator`, `SportHorseCareCreator`, `FoalCareCreator`, `SeniorHorseCareCreator` |

```java
public abstract class HorseCareCreator {
    public abstract HorseService createCare();   // factory method
}

public class SportHorseCareCreator extends HorseCareCreator {
    @Override
    public HorseService createCare() {
        return new SportHorseCare();
    }
}
```

The builders only know the abstract `HorseCareCreator`, never the concrete classes.

---

## 🧱 Builder pattern (added)

**Problem:** a care plan is a complex object with many parts (profile, base care, extra services, weekly sessions, recommendations), and each package fills those parts differently.

**Solution:** a builder interface with one method per step, one concrete builder per package, and a director that runs the steps in order.

| Role | Class |
|---|---|
| Product | `CarePlan` |
| Builder | `CarePlanBuilder` (interface) and `AbstractCarePlanBuilder` (shared steps) |
| Concrete builders | `CustomCarePlanBuilder`, `CompetitionCarePlanBuilder`, `WellnessCarePlanBuilder`, `HealthCheckCarePlanBuilder` |
| Director | `CarePlanDirector` |

```java
CarePlanBuilder builder = new CompetitionCarePlanBuilder("Thunder", HorseType.SPORT, new SportHorseCareCreator());
CarePlanDirector director = new CarePlanDirector(builder);

director.buildCarePlan();
CarePlan plan = builder.getCarePlan();
```

### Packages

| Package | Builder | Extra services | Sessions/week |
|---|---|---|---:|
| Personalizado | `CustomCarePlanBuilder` | The ones the customer selects | 3 |
| Competencia | `CompetitionCarePlanBuilder` | Premium food, training, veterinary check | 5 |
| Bienestar | `WellnessCarePlanBuilder` | Premium food, bath | 2 |
| Control de salud | `HealthCheckCarePlanBuilder` | Bath, veterinary check | 1 |

---

## 💰 Prices

| Base care (Factory Method) | Price |
|---|---:|
| Caballo de paseo — `BasicHorseCare` | $80.000 |
| Caballo deportivo — `SportHorseCare` | $110.000 |
| Potro — `FoalCare` | $95.000 |
| Caballo mayor — `SeniorHorseCare` | $100.000 |

| Extra service (Decorator) | Price |
|---|---:|
| Premium food | +$30.000 |
| Bath and grooming | +$20.000 |
| Training | +$50.000 |
| Veterinary check-up | +$40.000 |

Prices are examples created for the academic demonstration.

---

## 📁 Project structure

```text
├── Dockerfile
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/horsecare/
    │   ├── HorseCareApplication.java
    │   ├── controller/
    │   │   └── HorseController.java            REST API
    │   ├── service/
    │   │   └── CarePlanService.java            client of the three patterns
    │   ├── model/                              Decorator component + Factory Method products
    │   │   ├── HorseService.java
    │   │   ├── HorseType.java
    │   │   ├── BasicHorseCare.java
    │   │   ├── SportHorseCare.java
    │   │   ├── FoalCare.java
    │   │   └── SeniorHorseCare.java
    │   ├── decorator/                          Decorator
    │   │   ├── HorseServiceDecorator.java
    │   │   ├── PremiumFoodDecorator.java
    │   │   ├── BathDecorator.java
    │   │   ├── TrainingDecorator.java
    │   │   └── VeterinaryDecorator.java
    │   ├── factory/                            Factory Method
    │   │   ├── HorseCareCreator.java
    │   │   ├── LeisureHorseCareCreator.java
    │   │   ├── SportHorseCareCreator.java
    │   │   ├── FoalCareCreator.java
    │   │   └── SeniorHorseCareCreator.java
    │   └── builder/                            Builder
    │       ├── CarePlan.java
    │       ├── CarePlanBuilder.java
    │       ├── AbstractCarePlanBuilder.java
    │       ├── CustomCarePlanBuilder.java
    │       ├── CompetitionCarePlanBuilder.java
    │       ├── WellnessCarePlanBuilder.java
    │       ├── HealthCheckCarePlanBuilder.java
    │       ├── CarePlanDirector.java
    │       ├── CarePackage.java
    │       └── ExtraServiceSelection.java
    └── resources/
        ├── application.properties
        └── static/
            ├── index.html
            ├── style.css
            └── script.js
```

---

## ▶️ How to run locally

Requirements: **Java 21** and **Maven 3.9+** (or an IDE with Maven support, such as IntelliJ IDEA).

```bash
mvn spring-boot:run
```

Then open http://localhost:8080.

To build and run the jar:

```bash
mvn clean package
java -jar target/horsecare-1.0.0.jar
```

---

## 🔌 REST API

| Method | Path | Description |
|---|---|---|
| GET | `/api/options` | Horse types and care packages for the selects |
| POST | `/api/care-plans` | Builds a care plan |

Example request:

```json
{
  "horseName": "Thunder",
  "horseType": "SPORT",
  "carePackage": "CUSTOM",
  "premiumFood": true,
  "bath": false,
  "training": true,
  "veterinary": false
}
```

Example response:

```json
{
  "horseName": "Thunder",
  "horseType": "Caballo deportivo",
  "carePackage": "Personalizado",
  "description": "Cuidado deportivo (ejercicio diario y control de cascos) + Alimentación premium + Entrenamiento",
  "price": 190000.0,
  "weeklySessions": 3,
  "recommendations": ["Revisar el plan con el cuidador cada mes."],
  "creator": "SportHorseCareCreator",
  "baseCare": "SportHorseCare",
  "builder": "CustomCarePlanBuilder",
  "decorators": ["PremiumFoodDecorator", "TrainingDecorator"]
}
```

Invalid requests (empty name, unknown horse type, etc.) return **HTTP 400** with `{"error": "..."}`.

---

## ☁️ Deployment (Render)

The project includes a multi-stage `Dockerfile`, and `application.properties` reads the port assigned by the platform (`server.port=${PORT:8080}`).

1. Sign in at https://render.com with GitHub.
2. **New → Web Service** → select this repository.
3. Render detects the `Dockerfile`. Choose the **Free** plan and create the service.
4. When the build finishes, Render gives a public URL like `https://horsecare.onrender.com`.

On the free plan the service sleeps after 15 minutes without traffic; the next visit takes about a minute to wake it up.

---

## 👥 Authors

- **Original project (Decorator):** course team that created HorseCare.
- **Factory Method, Builder and deployment:** Kevin Basante and Arley Riascos.
