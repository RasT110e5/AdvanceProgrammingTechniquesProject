# Advance Programming Techniques TP
## Primera Entrega
Se requiere realizar un sistema de gestión para la asignación de turno y entrega de registro de conducción.

### Objetivos
Objetivo: realizar el control de turnos y otorgamiento de licencias de conducir, tomando en consideración los siguientes requerimientos funcionales.
1. El examen consta de dos etapas.
    1. La primera etapa incluye la pregunta si utiliza o no anteojos.
        1. Si responde que no automáticamente, deberá generarse el examen y permitirle responder el cuestionario.
        2. Si responde que si, se generará un turno random para que asista a una revisión. (La fecha deberá generarse de manera aleatoria).
2. El usuario debería generar una clave para poder acceder al examen, la cual tendrá un tiempo de expiración de una hora.
    1. La clave generada podrá utilizarse una sola vez.
    2. En caso de que no termine la evaluación no podrá volver a usarla.
3. El cuestionario constará de 10 preguntas precargas de opción múltiple.  
   1. Las mismas deberás cargarse de forma aleatoria.
4. Aprueba el examen con un 8 preguntas respondidas correctamente.
5. En caso de reprobar el sistema deberá generar un nuevo turno aleatorio.
6. La misma persona solo podrá rendir un máximo de 3 veces el examen.
7. Los resultados del examen deberán guardarse durante 1 semana.
8. Las preguntas deberán poder ser editables por un administrador del sistema.
9. El administrador del sistema podrá acceder a una estadística diaria de cuantos rindieron el examen, cuantos aprobaron, reprobaron o estuvieron ausentes.

**Se pide:**
- Implementar un modelo cliente servidor.
- Implementar modelo API, desacoplar completamente el frontend del backend.
- Implementar el paradigma de objetos.
- Implementar al menos en la relación entre dos objetos inyección de dependencias.

### Objetivo
Se pide documentar el plan de trabajo y las decisiones técnicas tomadas para la resolución del mismo. Se deberán entregar diagramas y justificaciones. **NO** se requiere la entrega de código en la primera instancia.

### Requerimientos
- Diagrama de clases del sistema.
- Modelo de datos.
- Detalle de las tecnologías a utilizar.
- Tipo de testeo y módulos que se planifican testear.
- Justificación de la elección de tecnología y consideraciones.

### Condiciones De Entrega
Deberá entregarse un documento en formato PDF conteniendo todos los requerimientos expuestos.

**Fecha de entrega:** semana 8

En el caso de que no se pueda realizar la entrega en la fecha estipulada, deberá entregar todo junto en la fecha de entrega del proyecto integrador. Si en esa instancia no alcanza a cumplir con los objetivos, podrá realizar la reentrega en la instancia de recuperación correspondiente.

En el caso de que se realicen observaciones o requerir correcciones en el primer entregable, estos deberán entregarse resueltos en la segunda entrega.

## Segunda Entrega
### Objetivo
desarrollar y desplegar la solución planteada en el primer entregable.

### Consideraciones
- El código puede ser desarrollado seleccionando alguna de los siguiente lenguajes: phyton, java, Javascript.
- Para la persistencia de la información se puede elegir bases de datos relacionales o no relacionales, justificando su elección.
- Se espera que se desplieguen los conceptos revisados durante la cursada (tales como la orientación a servicios), por lo que no serán consideradas correctas las soluciones monolíticas.
- Se deberá presentar la herramienta utilizada para los tests unitarios a partir de la tecnología de trabajo seleccionada y los tests escritos.

### Entregable
- Link al repositorio de código en github o bitbucket según prefiera.
- Se deberá incluir todo el código desarrollo, un readme con la información para realizar el despliegue en un servidor y consideraciones.

### Defensa
Esta instancia sincrónica consistirá en una demo de la solución funcionando, durante la cual se realizarán la preguntas pertinentes que el docente considere sobre la implementación.

### Evaluación
La nota final se compondrá del resultado de la evaluación sincrónica, más la revisión del código entregado.
