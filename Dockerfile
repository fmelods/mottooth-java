# Etapa de build
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

# Instala Maven diretamente
RUN apk add --no-cache maven

# Copia os arquivos necessários para o build
COPY pom.xml .
COPY src src

# Usa Maven instalado via apk
RUN mvn package -DskipTests

# Extrai dependências do JAR gerado
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Entrada da aplicação
ENTRYPOINT ["java","-cp","app:app/lib/*","br.com.fiap.mottooth.MottothApplication"]

# Segurança: adiciona usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring