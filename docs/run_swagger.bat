echo 'Starting swagger on http://localhost:3000'
docker pull swaggerapi/swagger-ui
docker run -p 3000:8080 -e SWAGGER_JSON=/api.yaml -v %cd%/openapi.yaml:/api.yaml swaggerapi/swagger-ui
