FROM openjdk:11

MAINTAINER afrilins.net

# Service name
ENV SERVICE_NAME archive

# Context path
ENV CONTEXT_PATH archive

# WAR filename
ENV WAR_FILENAME ${SERVICE_NAME}.jar

#COPY ${SERVICE_NAME}/target/${SERVICE_NAME}.jar ${WAR_FILENAME}

COPY ./entrypoint.sh /

RUN chmod 755 entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

# Expose necessary port
EXPOSE 8080

# HEALTHCHECK
HEALTHCHECK --retries=3 CMD curl --fail http://localhost:8080/${CONTEXT_PATH}/manage/health || exit 1

