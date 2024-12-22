# API de Conversión de Divisas

Esta es una API REST desarrollada para convertir divisas. El servicio está implementado con Java 11 y Spring Boot, y puede ser desplegado utilizando Docker Compose. Utiliza como fuente de información la pagina [Exchange Rate](https://exchangerate.host/).

## Requisitos

- Docker
- Docker Compose

## Instrucciones para levantar el Servicio

1. Clona el repositorio en tu máquina local.
2. Navega al directorio raíz del proyecto.
3. Ejecuta el siguiente comando para levantar el servicio utilizando Docker Compose:

   ```bash
   docker-compose up

## Endpoints
### Conversión de Divisas
Permite convertir un valor de una divisa a otra.

* **URL**: GET /api/v1/exchange 
* **Parámetros**:
  - from (string): La divisa de origen (ej. USD, EUR). 
  - to (string): La divisa de destino (ej. ARS, BTC). 
  - value (float): El valor a convertir.
* **Ejemplo de request**:

```bash
  curl --location --request GET 'http://localhost:8080/api/v1/exchange?from=USD&to=ARS&value=1'
```
* **Ejemplo de respuesta**:
```json
{
    "from": "USD",
    "to": "ARS",
    "value": 1,
    "result": 1017.898212
}
```

### Listado de Divisas
Permite listar todas las divisas posibles de convertir.

* **URL**: GET /api/v1/exchange/currencies
* **Ejemplo de request**:

```bash
  curl --location --request GET 'http://localhost:8080/api/v1/exchange/currencies'
```
* **Ejemplo de respuesta**:
```json
{
   "values": [
      "USD",
      "ARS",
      "BTC",
      "EUR"
   ]
}
```