import csv
import json
import requests
import os
from dotenv import load_dotenv # Importar la función para cargar el archivo .env

# --- 0. CARGAR VARIABLES DE ENTORNO ---
# Carga las variables del archivo .env al entorno de ejecución.
load_dotenv()

# --- 1. ESPECIFICACIONES DE CONFIGURACIÓN ---

# URL del endpoint de la API para las solicitudes POST
# Leer la API_URL (Si no está en .env, usa el valor por defecto)
API_URL = os.getenv("API_APOYOS_URL", "http://localhost:8080/api/apoyos")

# Token de autenticación Bearer
# **IMPORTANTE**: Reemplaza este valor con tu token real.
AUTH_TOKEN = os.getenv("AUTH_BEARER_TOKEN")

# Nombre del archivo CSV de entrada
CSV_FILE_PATH = "data/apoyos.csv"

# Definición de las cabeceras HTTP, incluyendo la autenticación y el tipo de contenido
HEADERS = {
    "Authorization": f"Bearer {AUTH_TOKEN}",
    "Content-Type": "application/json"
}

# --- 2. FUNCIÓN PRINCIPAL DE PROCESAMIENTO ---

def cargar_csv_a_api(csv_path: str, api_url: str):
    """
    Lee un archivo CSV fila por fila, convierte cada fila a JSON
    y la envía a un endpoint de API mediante una solicitud POST.

    Args:
        csv_path (str): Ruta al archivo CSV de entrada.
        api_url (str): URL completa del endpoint de la API.
    """
    
    if not os.path.exists(csv_path):
        print(f"❌ ERROR: No se encontró el archivo de entrada en la ruta: {csv_path}")
        print("Asegúrate de que 'datos_entrada.csv' exista en el mismo directorio.")
        return

    print(f"--- INICIANDO PROCESO DE CARGA DE DATOS ---")
    print(f"Endpoint: {api_url}")
    print(f"Archivo de origen: {csv_path}\n")

    filas_cargadas = 0
    filas_fallidas = 0

    try:
        with open(csv_path, mode='r', encoding='utf-8') as csvfile:
            # csv.DictReader lee cada fila como un diccionario con los encabezados como claves
            reader = csv.DictReader(csvfile)
            
            # Itera sobre cada fila del archivo CSV
            for i, row in enumerate(reader):
                # i+1 es el número de fila (excluyendo el encabezado)
                numero_fila = i + 1
                
                # La fila 'row' ya es un diccionario Python (la estructura del payload)
                # Ejemplo de payload: 
                # {"id": "101", "nombre": "Beca Universitaria", ...}
                payload = row

                print(f"Procesando fila #{numero_fila} (ID: {payload.get('id', 'N/A')})...")
                
                try:
                    # Envía la solicitud POST
                    # El módulo 'requests' serializa automáticamente el diccionario 'json=payload' a JSON
                    response = requests.post(api_url, json=payload, headers=HEADERS)
                    
                    # Manejo básico de errores: códigos de éxito 200 y 201
                    if response.status_code in [200, 201]:
                        print(f"   ✅ ÉXITO: Fila #{numero_fila} cargada. Código: {response.status_code}")
                        filas_cargadas += 1
                    else:
                        # Imprime el código de estado y el mensaje de error de la API (si lo hay)
                        error_message = response.text or "Sin mensaje de error del servidor."
                        print(f"   ❌ FALLO: Fila #{numero_fila} no cargada. Código: {response.status_code}. Respuesta: {error_message[:100]}...")
                        filas_fallidas += 1

                except requests.exceptions.RequestException as e:
                    # Manejo de errores de conexión (ej. API no disponible, timeout)
                    print(f"   🛑 ERROR DE CONEXIÓN: Fila #{numero_fila}. No se pudo conectar a la API. Detalle: {e}")
                    filas_fallidas += 1
                    # Opcional: break o raise para detener el proceso ante errores críticos de conexión.
                    
    except Exception as e:
        print(f"\n❌ ERROR CRÍTICO al procesar el archivo CSV: {e}")
        
    finally:
        print("\n--- RESUMEN DE LA CARGA ---")
        print(f"Total de filas cargadas con éxito: {filas_cargadas}")
        print(f"Total de filas fallidas: {filas_fallidas}")
        print("--- PROCESO FINALIZADO ---")

# --- 3. PUNTO DE ENTRADA ---

if __name__ == "__main__":
    cargar_csv_a_api(CSV_FILE_PATH, API_URL)