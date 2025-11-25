import csv
import json
import requests
import os
from dotenv import load_dotenv # Importar la función para cargar el archivo .env

# --- 0. CARGAR VARIABLES DE ENTORNO ---
# Carga las variables del archivo .env al entorno de ejecución.
load_dotenv()

# --- 1. ESPECIFICACIONES DE CONFIGURACIÓN ---

# URL del endpoint de destino para las solicitudes POST
API_URL = os.getenv("API_SOLICITUDES_URL", "http://localhost:8080/api/solicituds")

# Nombre del archivo CSV de entrada
CSV_FILE_PATH = "data/solicitudes.csv"

# Token de autenticación Bearer
# !! IMPORTANTE: REEMPLAZA ESTE VALOR CON TU TOKEN REAL !!
AUTH_BEARER_TOKEN = os.getenv("AUTH_BEARER_TOKEN")

# Definición de las cabeceras HTTP
HEADERS = {
    "Authorization": f"Bearer {AUTH_BEARER_TOKEN}",
    "Content-Type": "application/json"
}

# Definición de las columnas que forman parte del objeto "apoyo"
# Se asume que el resto de los campos de 'apoyo' (nombre, desc, etc.) serán null si solo se proporciona 'apoyo.id'.
APOYO_KEYS = ["nombre", "desc", "prerrequisitos", "keywords", "tipo"]

# --- 2. FUNCIÓN PRINCIPAL DE PROCESAMIENTO ---

def main():
    """
    Lee el CSV, convierte cada fila a JSON anidado (incluyendo 'apoyo')
    y la envía al endpoint de la API mediante solicitudes POST.
    """
    
    if not os.path.exists(CSV_FILE_PATH):
        print(f"❌ ERROR CRÍTICO: Archivo no encontrado. Asegúrate de que '{CSV_FILE_PATH}' exista en el directorio.")
        return

    print(f"--- INICIANDO INGESTA DE DATOS CON ESTRUCTURA ANIDADA ---")
    print(f"Endpoint: {API_URL}")
    print("-" * 35)

    registros_exitosos = 0
    registros_fallidos = 0

    try:
        with open(CSV_FILE_PATH, mode='r', encoding='utf-8') as csvfile:
            # csv.DictReader lee cada fila como un diccionario usando los encabezados como claves
            reader = csv.DictReader(csvfile)
            
            # Bucle principal para iterar sobre cada registro (fila) del CSV
            for i, row in enumerate(reader):
                numero_fila = i + 1
                registro_id = row.get('id', 'N/A')
                
                print(f"Procesando registro #{numero_fila} (ID: {registro_id})...", end=" ")
                
                # --- LÓGICA DE CONSTRUCCIÓN DEL PAYLOAD ---
                
                # 1. Creamos el diccionario principal con todos los campos (que ya están en 'row')
                payload = row.copy() # Usamos .copy() para no modificar directamente el diccionario de DictReader
                
                # 2. Extraemos el valor de la columna 'apoyo' del CSV
                apoyo_id_value = payload.pop("apoyo", None) # Extraemos y eliminamos la clave 'apoyo' de la raíz del payload

                # 3. Construimos el objeto JSON anidado 'apoyo'
                apoyo_object = {
                    "id": apoyo_id_value
                }
                # Añadimos el resto de los campos como null, según el formato requerido
                for key in APOYO_KEYS:
                    apoyo_object[key] = None
                
                # 4. Insertamos el objeto 'apoyo' anidado en el payload principal
                payload['apoyo'] = apoyo_object
                
                # El 'payload' ahora tiene la estructura anidada correcta:
                # {"id": "...", ..., "apoyo": {"id": "analisis-ibm", "nombre": null, ...}, "estado": "..."}
                
                # --- ENVÍO DE LA SOLICITUD POST ---
                
                try:
                    response = requests.post(API_URL, json=payload, headers=HEADERS)
                    
                    if response.status_code in [200, 201]:
                        print(f"✅ ÉXITO. Código: {response.status_code}")
                        registros_exitosos += 1
                    else:
                        error_response = response.text or "Sin contenido de error de la API."
                        print(f"❌ FALLO. Código: {response.status_code}")
                        print(f"    -> Respuesta de error: {error_response[:100]}...")
                        # Opcional: Imprimir el JSON fallido para depuración
                        # print(f"    -> JSON Enviado: {json.dumps(payload)}") 
                        registros_fallidos += 1

                except requests.exceptions.RequestException as e:
                    print(f"🛑 ERROR DE CONEXIÓN. Detalle: {e}")
                    registros_fallidos += 1
                    
    except Exception as e:
        print(f"\n❌ ERROR CRÍTICO al procesar el archivo CSV: {e}")
        
    finally:
        print("\n" + "=" * 35)
        print("RESUMEN FINAL DE LA CARGA")
        print(f"Total de registros cargados con éxito: {registros_exitosos}")
        print(f"Total de registros con fallos: {registros_fallidos}")
        print("=" * 35)

# --- 3. PUNTO DE EJECUCIÓN ---

if __name__ == "__main__":
    main()