# Advance Programming Techniques TP

## Description and objectives

Create a system for management of driving licenses test appointments, with the following functional requirements:

1. When starting the exam the user must answer if they use glasses.
    1. If so, the system should generate an eye appointment at a random time and start the exam.
    2. If not, the system should just start the exam.
2. The user must use a generated key to enter the exam, this key should expire after 1 hour and should not be usable if
   the exam is not finished during the appointment.
3. The driving test should consist of 10 multiple choice questions, each with only 1 correct option.
    1. The questions should be sorted in a random order at the start of the exam.
    2. The exam is approved with 8 questions answered correctly
        1. If the exam is failed, then the system should generate a random new appointment for retry.
    3. The exam can only be taken 3 times by the same user.
    4. The questions' and options' text must be modifiable by the admins
4. The exam results must only be kept for a week
    1. The admins should be able to get statistics of said results grouped by day with:
        1. Amount of exams
        2. Amount of passed exams
        3. Amount of failed exams
        4. Appointments with students as no-show


### Assignment in Spanish
#### Primera Entrega
Se requiere realizar un sistema de gestión para la asignación de turno y entrega de registro de conducción.

#### Objetivos

Objetivo: realizar el control de turnos y otorgamiento de licencias de conducir, tomando en consideración los siguientes
requerimientos funcionales.

1. El examen consta de dos etapas.
    1. La primera etapa incluye la pregunta si utiliza o no anteojos.
        1. Si responde que no automáticamente, deberá generarse el examen y permitirle responder el cuestionario.
        2. Si responde que si, se generará un turno random para que asista a una revisión. (La fecha deberá generarse de
           manera aleatoria).
2. El usuario debería generar una clave para poder acceder al examen, la cual tendrá un tiempo de expiración de una
   hora.
    1. La clave generada podrá utilizarse una sola vez.
    2. En caso de que no termine la evaluación no podrá volver a usarla.
3. El cuestionario constará de 10 preguntas precargas de opción múltiple.
    1. Las mismas deberás cargarse de forma aleatoria.
4. Aprueba el examen con un 8 preguntas respondidas correctamente.
5. En caso de reprobar el sistema deberá generar un nuevo turno aleatorio.
6. La misma persona solo podrá rendir un máximo de 3 veces el examen.
7. Los resultados del examen deberán guardarse durante 1 semana.
8. Las preguntas deberán poder ser editables por un administrador del sistema.
9. El administrador del sistema podrá acceder a una estadística diaria de cuantos rindieron el examen, cuantos
   aprobaron, reprobaron o estuvieron ausentes.

**Se pide:**

- Implementar un modelo cliente servidor.
- Implementar modelo API, desacoplar completamente el frontend del backend.
- Implementar el paradigma de objetos.
- Implementar al menos en la relación entre dos objetos inyección de dependencias.

#### Objetivo

Se pide documentar el plan de trabajo y las decisiones técnicas tomadas para la resolución del mismo. Se deberán
entregar diagramas y justificaciones. **NO** se requiere la entrega de código en la primera instancia.

#### Requerimientos

- Diagrama de clases del sistema.
- Modelo de datos.
- Detalle de las tecnologías a utilizar.
- Tipo de testeo y módulos que se planifican testear.
- Justificación de la elección de tecnología y consideraciones.

#### Condiciones De Entrega

Deberá entregarse un documento en formato PDF conteniendo todos los requerimientos expuestos.

**Fecha de entrega:** semana 8

En el caso de que no se pueda realizar la entrega en la fecha estipulada, deberá entregar todo junto en la fecha de
entrega del proyecto integrador. Si en esa instancia no alcanza a cumplir con los objetivos, podrá realizar la reentrega
en la instancia de recuperación correspondiente.

En el caso de que se realicen observaciones o requerir correcciones en el primer entregable, estos deberán entregarse
resueltos en la segunda entrega.

### Segunda Entrega

#### Objetivo

desarrollar y desplegar la solución planteada en el primer entregable.

#### Consideraciones

- El código puede ser desarrollado seleccionando alguna de los siguiente lenguajes: phyton, java, Javascript.
- Para la persistencia de la información se puede elegir bases de datos relacionales o no relacionales, justificando su
  elección.
- Se espera que se desplieguen los conceptos revisados durante la cursada (tales como la orientación a servicios), por
  lo que no serán consideradas correctas las soluciones monolíticas.
- Se deberá presentar la herramienta utilizada para los tests unitarios a partir de la tecnología de trabajo
  seleccionada y los tests escritos.

#### Entregable

- Link al repositorio de código en github o bitbucket según prefiera.
- Se deberá incluir todo el código desarrollo, un readme con la información para realizar el despliegue en un servidor y
  consideraciones.

#### Defensa

Esta instancia sincrónica consistirá en una demo de la solución funcionando, durante la cual se realizarán la preguntas
pertinentes que el docente considere sobre la implementación.

#### Evaluación

La nota final se compondrá del resultado de la evaluación sincrónica, más la revisión del código entregado.
