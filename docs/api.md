# 🌐 Documentación de APIs de Gestión de Solicitudes y Apoyos

Este documento describe la estructura y el flujo de autenticación para interactuar con los servicios de Solicitudes (`/api/solicituds`) y Apoyos (`/api/apoyos`).

---

## 🔑 1. Autenticación (Obtención del Bearer Token)

Todas las consultas a los servicios de datos (`/api/solicituds` y `/api/apoyos`) requieren autenticación mediante un **Bearer Token**. Este token se obtiene a través del endpoint de autenticación.

### Endpoint: `POST /api/authenticate`

| Detalle           | Descripción                             |
| ----------------- | --------------------------------------- |
| **Método**        | `POST`                                  |
| **URL**           | `/api/authenticate`                     |
| **Headers**       | `Content-Type: application/json`        |
| **Cuerpo (Body)** | Objeto JSON con credenciales.           |
| **Respuesta**     | Objeto JSON con el token en `id_token`. |

#### Ejemplo de Petición

```json
{
  "username": "admin",
  "password": "admin",
  "rememberMe": true
}
```

#### Ejemplo de Respuesta

```json
{
  "id_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTc2NjcwMDc0MiwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNzY0MTA4NzQyLCJ1c2VySWQiOiJ1c2VyLTEifQ.o0z6fH8Gnz_vjLay8ZJKELW-NHAlg8gq7-_e03bBEdiUOsAayoRBeqy4tsxb83olbNh6lFHU231RD_y8RPnwOA"
}
```

### Uso del Token

El valor de `id_token` debe ser incluido en la cabecera `Authorization` de todas las peticiones subsiguientes:

| Cabecera          | Valor               |
| ----------------- | ------------------- |
| **Authorization** | `Bearer <id_token>` |
| **Content-Type**  | `application/json`  |

---

## 📋 2. Servicio de Solicitudes (`/api/solicituds`)

Este servicio gestiona el acceso a los datos de las solicitudes ciudadanas. Todos los endpoints requieren autenticación (Bearer Token).

### A. Consultar un Único Registro

| Detalle                | Descripción                                               |
| ---------------------- | --------------------------------------------------------- |
| **Método**             | `GET`                                                     |
| **URL**                | `/api/solicituds/{id}`                                    |
| **Parámetros de Ruta** | `{id}`: Identificador único de la solicitud (e.g., CURP). |
| **Headers**            | **Requiere** `Authorization: Bearer <token>`              |

#### Ejemplo de Respuesta (id = `AGUR990909HMNLLS19`)

```json
{
  "id": "AGUR990909HMNLLS19",
  "curp": "AGUR990909HMNLLS19",
  "nombre": "Sergio",
  "primerApellido": "Aguilar",
  "segundoApellido": "Ruiz",
  "genero": "Hombre",
  "desc": "Conocimientos en MySQL y JavaScript. Proactivo.",
  "keywords": "MySQL, JavaScript, proactividad",
  "ineUrl": "http://DOMINIO_DEL_PROYECTO/AGUR990909HMNLLS19_ine.pdf",
  "cvUrl": "http://DOMINIO_DEL_PROYECTO/AGUR990909HMNLLS19_cv.pdf",
  "estado": "ENVIADA",
  "apoyo": {
    "id": "ai-machine-learning",
    "nombre": null,
    "desc": null,
    "prerrequisitos": null,
    "keywords": null,
    "tipo": null
  }
}
```

### B. Consultar Varios Registros (Paginación)

| Detalle                    | Descripción                                                 |
| -------------------------- | ----------------------------------------------------------- |
| **Método**                 | `GET`                                                       |
| **URL**                    | `/api/solicituds`                                           |
| **Parámetros de Consulta** | `page`, `size`, `sort` (e.g., `page=0&size=20&sort=id,asc`) |
| **Headers**                | **Requiere** `Authorization: Bearer <token>`                |

#### Información de Paginación en Headers de Respuesta

La API proporciona detalles de navegación en las cabeceras HTTP de la respuesta:

- **`X-Total-Count`**: Número total de registros disponibles.
- **`Link`**: Contiene URLs para navegación paginada (próxima, última, primera).
  - Ejemplo: `<http://.../solicituds?sort=id%2Casc&page=1&size=20>; rel="next",<http://.../solicituds?sort=id%2Casc&page=2&size=20>; rel="last",<http://.../solicituds?sort=id%2Casc&page=0&size=20>; rel="first"`

#### Ejemplo de Respuesta

La respuesta es un **arreglo de objetos JSON** con la estructura de solicitud.

```json
[
    {
        "id": "AGUR990909HMNLLS19",
        ...
    },
    {
        "id": "ALVM030404MMCLRT28",
        ...
    },
    // ... más objetos hasta el límite 'size'
]
```

---

## 🎁 3. Servicio de Apoyos (`/api/apoyos`)

Este servicio proporciona la información detallada sobre los diferentes tipos de apoyos (cursos, becas, etc.). Todos los endpoints requieren autenticación (Bearer Token).

### A. Consultar un Único Registro

| Detalle                | Descripción                                                        |
| ---------------------- | ------------------------------------------------------------------ |
| **Método**             | `GET`                                                              |
| **URL**                | `/api/apoyos/{id}`                                                 |
| **Parámetros de Ruta** | `{id}`: Identificador único del apoyo (e.g., `ai-agentes-google`). |
| **Headers**            | **Requiere** `Authorization: Bearer <token>`                       |

#### Ejemplo de Respuesta (id = `ai-agentes-google`)

```json
{
  "id": "ai-agentes-google",
  "nombre": "IA para crear agentes (Agentic AI con Agentspace de Google)",
  "desc": "Ruta para crear y desplegar agentes de IA empresariales (Agentspace/Gemini). Incluye laboratorios prácticos.",
  "prerrequisitos": "Conocimientos básicos de ML y programación (Python) recomendados.",
  "keywords": "Agentes IA, LLMs, orquestación, prompts, integración de APIs",
  "tipo": "CURSO"
}
```

### B. Consultar Varios Registros (Paginación)

| Detalle                    | Descripción                                                |
| -------------------------- | ---------------------------------------------------------- |
| **Método**                 | `GET`                                                      |
| **URL**                    | `/api/apoyos`                                              |
| **Parámetros de Consulta** | `page`, `size`, `sort` (e.g., `page=0&size=2&sort=id,asc`) |
| **Headers**                | **Requiere** `Authorization: Bearer <token>`               |

#### Ejemplo de Respuesta

La respuesta es un **arreglo de objetos JSON** con la estructura de Apoyo.

```json
[
    {
        "id": "ai-agentes-google",
        "nombre": "IA para crear agentes (Agentic AI con Agentspace de Google)",
        ...
    },
    {
        "id": "ai-fundamentos",
        "nombre": "Fundamentos de Inteligencia Artificial e Ingeniero Asociado de IA",
        ...
    }
]
```

# 💻 Ejemplos de Peticiones API con cURL

## 1. Obtención del Token de Autenticación (`/api/authenticate`)

Esta es la primera petición y la única que **no requiere** la cabecera `Authorization`.

| **Detalle**  | **Valor**                        |
| ------------ | -------------------------------- |
| **Método**   | `POST`                           |
| **Endpoint** | `<BASE_URL>/api/authenticate`    |
| **Headers**  | `Content-Type: application/json` |

```bash
# Define la URL base de tu API
BASE_URL="http://DOMINIO_DEL_PROYECTO:8080"

# Petición cURL para autenticación
curl -X POST "$BASE_URL/api/authenticate" \
  -H "Content-Type: application/json" \
  -d '{
        "username": "user",
        "password": "PASSWORD_SEGURO_AQUI",
        "rememberMe": true
      }'
```

**Salida Esperada:**

```json
{"id_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsI..."}
# -> Guarda este valor (el JWT) para usarlo en las siguientes peticiones.
```

---

## 2. Consultas del Servicio Solicitudes (`/api/solicituds`)

Todas las siguientes peticiones **requieren** la cabecera `Authorization: Bearer <ID_TOKEN>`.

### A. Consultar un Único Registro (Por ID)

| **Detalle**  | **Valor**                          |
| ------------ | ---------------------------------- |
| **Método**   | `GET`                              |
| **Endpoint** | `/api/solicituds/{id}`             |
| **Headers**  | `Authorization: Bearer <ID_TOKEN>` |

```bash
# Reemplaza con tu token JWT obtenido
ID_TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsI..."
# Reemplaza con una CURP válida
ID_SOLICITUD="AGUR990909HMNLLS19"
BASE_URL="http://DOMINIO_DEL_PROYECTO:8080"

# Petición cURL para obtener una solicitud específica
curl -X GET "$BASE_URL/api/solicituds/$ID_SOLICITUD" \
  -H "Authorization: Bearer $ID_TOKEN"
```

### B. Consultar Varios Registros (Paginación)

| **Detalle**  | **Valor**                                             |
| ------------ | ----------------------------------------------------- |
| **Método**   | `GET`                                                 |
| **Endpoint** | `/api/solicituds?page=<page>&size=<size>&sort=<sort>` |
| **Headers**  | `Authorization: Bearer <ID_TOKEN>`                    |

```bash
# Consulta la página 0 con 5 registros, ordenados por 'id' ascendente
PARAMS="page=0&size=5&sort=id,asc"
BASE_URL="http://DOMINIO_DEL_PROYECTO:8080"

# Petición cURL para obtener la primera página de solicitudes
curl -i -X GET "$BASE_URL/api/solicituds?$PARAMS" \
  -H "Authorization: Bearer $ID_TOKEN"
# Nota: La opción '-i' muestra las cabeceras de respuesta, donde se encuentra X-Total-Count y Link.
```

---

## 3. Consultas del Recurso Apoyo (`/api/apoyos`)

Estas peticiones también **requieren** la cabecera `Authorization: Bearer <ID_TOKEN>`.

### A. Consultar un Único Registro (Por ID)

| **Detalle**  | **Valor**                          |
| ------------ | ---------------------------------- |
| **Método**   | `GET`                              |
| **Endpoint** | `/api/apoyos/{id}`                 |
| **Headers**  | `Authorization: Bearer <ID_TOKEN>` |

```bash
# Reemplaza con el ID de un apoyo, ej. 'ai-agentes-google'
ID_APOYO="ai-agentes-google"
BASE_URL="http://DOMINIO_DEL_PROYECTO:8080"

# Petición cURL para obtener un apoyo específico
curl -X GET "$BASE_URL/api/apoyos/$ID_APOYO" \
  -H "Authorization: Bearer $ID_TOKEN"
```

### B. Consultar Varios Registros (Paginación)

| **Detalle**  | **Valor**                                         |
| ------------ | ------------------------------------------------- |
| **Método**   | `GET`                                             |
| **Endpoint** | `/api/apoyos?page=<page>&size=<size>&sort=<sort>` |
| **Headers**  | `Authorization: Bearer <ID_TOKEN>`                |

```bash
# Consulta la primera página con 2 registros, ordenados por 'id' ascendente
PARAMS_APOYO="page=0&size=2&sort=id,asc"
BASE_URL="http://DOMINIO_DEL_PROYECTO:8080"

# Petición cURL para obtener la lista paginada de apoyos
curl -X GET "$BASE_URL/api/apoyos?$PARAMS_APOYO" \
  -H "Authorization: Bearer $ID_TOKEN"
```
