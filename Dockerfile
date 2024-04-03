# Utiliza una imagen de Gradle con JDK 11 como nuestra imagen base para el proceso de construcción
FROM gradle:7-jdk11 AS build

# Copia el código fuente del proyecto al contenedor, estableciendo el propietario como el usuario gradle
COPY --chown=gradle:gradle . /home/gradle/src

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /home/gradle/src

# Ejecuta el comando de Gradle para construir el fat JAR, sin iniciar el daemon de Gradle
RUN gradle buildFatJar --no-daemon

# Comienza una nueva etapa de construcción para crear una imagen más pequeña para la ejecución
FROM openjdk:11

# Expone el puerto 8080 para permitir el acceso al contenedor
EXPOSE 8080

# Crea un directorio /app en el contenedor para almacenar el archivo JAR
RUN mkdir /app

# Copia el archivo JAR del contenedor de construcción al contenedor de ejecución
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar

# Define el punto de entrada para la ejecución de la aplicación
# Aquí es donde añadimos la capacidad de pasar la propiedad del sistema `env` para controlar el entorno
# Este valor de propiedad se puede establecer usando la variable de entorno `ENV` en el contenedor
# Se asume que `ENV` es configurada en la plataforma (como Render.com) donde se despliega el contenedor
ENTRYPOINT ["sh", "-c", "java -Denv=$ENV -jar /app/ktor-docker-sample.jar"]
