# eApoyo

# 🚀 Plataforma Integral de Gestión de Apoyos para el Gobierno (MVP)

---

## 1. Resumen Ejecutivo

La presente propuesta define el marco de trabajo para el próximo **Hackatón del TecNM**, enfocándose en resolver uno de los desafíos operativos más críticos de la organización: la saturación y gestión manual en el **otorgamiento de apoyos gubernamentales**.

El objetivo es convocar talento técnico para desarrollar **prototipos funcionales** que:

- **Automaticen** la validación documental.
- **Optimicen** el flujo de evaluación de expertos.
- **Garantice** la trazabilidad de los recursos.

Esto busca reducir significativamente los tiempos de respuesta a la ciudadanía.

---

## 2. Antecedentes y Justificación del Problema

Actualmente, el proceso de gestión de apoyos sociales enfrenta **limitaciones operativas severas** ante volúmenes superiores a las 10,000 solicitudes por convocatoria.

Se han identificado las siguientes problemáticas críticas:

- **Cuellos de botella en la validación:** La revisión manual de documentos (INE, comprobantes) consume aproximadamente el **80% del tiempo** del personal administrativo, generando retrasos y fatiga operativa.
- **Dispersión en la evaluación:** La asignación de expedientes a evaluadores expertos carece de una **plataforma centralizada**, dificultando el seguimiento, la estandarización de criterios y el cumplimiento de plazos.
- **Riesgo de error humano:** El manejo manual incrementa la probabilidad de errores en la captura de datos y en la dictaminación final.
- **Falta de trazabilidad:** La ausencia de un sistema integrado dificulta informar al ciudadano sobre el **estatus real de su trámite** en tiempo real.

---

## 3. Objetivo General del Reto

Desarrollar una **Plataforma Integral de Gestión de Apoyos (MVP)** que permita la **digitalización del ciclo de vida completo de una solicitud**.

Esto incluye desde la captura por parte del ciudadano hasta la autorización del apoyo de que se trate, priorizando la **automatización de procesos repetitivos** mediante el uso de tecnología.

---

## 4. Estructura Técnica del Reto (Tracks)

Para garantizar soluciones abordables y especializadas, el reto se dividirá en **tres módulos** o líneas de trabajo sugeridas:

### Módulo A: Admisión inteligente y validación (Pre-filtro)

- **El Desafío:** Crear un sistema de ingesta que utilice tecnologías de **reconocimiento óptico (OCR) e Inteligencia Artificial** para validar la documentación en tiempo real.
- **Funcionalidad Esperada:** Lectura automática de documentos oficiales, cruce de datos contra el formulario de solicitud y **rechazo automático de expedientes incompletos o ilegibles** antes de la intervención humana.

### Módulo B: Flujo de evaluación y dictaminación

- **El Desafío:** Diseñar un **tablero de control (Dashboard)** para la gestión de evaluadores expertos.
- **Funcionalidad Esperada:** Asignación automática y equitativa de solicitudes a evaluadores, herramientas de calificación estandarizada (**rúbricas digitales**), y mecanismos de consenso para casos complejos (evaluación por pares).

### Módulo C: Seguimiento, dispersión y transparencia

- **El Desafío:** Desarrollar el ecosistema de comunicación con el beneficiario y la gestión final del apoyo.
- **Funcionalidad Esperada:** Sistema de **notificaciones automáticas por etapas** (validado, en evaluación, aprobado/rechazado) y generación de reportes de auditoría sobre la asignación de recursos (becas, cursos, apoyos económicos).

---

## 5. Entregables y Criterios de Evaluación

Los proyectos resultantes serán evaluados bajo una matriz ponderada que prioriza la **viabilidad operativa** sobre la complejidad teórica:

| Criterio de Evaluación                     | Ponderación | Descripción Clave                                                                                |
| :----------------------------------------- | :---------- | :----------------------------------------------------------------------------------------------- |
| **Impacto en la eficiencia**               | 30%         | Capacidad demostrable para reducir el tiempo de procesamiento manual (Estimado ideal: >60%).     |
| **Funcionalidad técnica / MVP**            | 30%         | Estabilidad del prototipo y uso efectivo de tecnologías de automatización (IA/OCR/Workflows).    |
| **Experiencia de usuario y accesibilidad** | 20%         | Facilidad de uso para el funcionario público y el ciudadano (considerando brechas digitales).    |
| **Seguridad y protección de datos**        | 20%         | Cumplimiento estricto con normativas de manejo de información sensible y datos personales (PII). |

---

## 6. Recursos Requeridos para el Evento

Para el éxito de este reto, la organización proveerá:

- **Set de Datos de Prueba:** 60 expedientes ficticios anonimizados para pruebas de estrés y validación.
- **Mentores Especializados:** Disponibilidad de gerentes de programa y operativos para resolver dudas de negocio durante el evento.
- **Infraestructura Base:** Acceso a APIs de prueba y documentación de las reglas de operación vigentes.

---

## 7. Conclusión

La implementación de este reto en el Hackatón TecNM no solo busca una solución tecnológica, sino **catalizar una transformación en la cultura de servicio**. Al automatizar la burocracia operativa, se permitirá que el capital humano se enfoque en evaluar el impacto cualitativo de los apoyos y atender mejor a la ciudadanía.

## 8. Recursos importantes para el proyecto

1. [Flujo de la solicitud y reglas de operación](docs/requerimientos.md)
2. [Documentación del API de solicitudes y apoyos](docs/api.md)
