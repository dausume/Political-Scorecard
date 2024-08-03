# RUN THIS ONLY USING THE DOCKER COMPOSE FILE!
# Ensures the dependencies are installed properly
FROM quay.io/keycloak/keycloak:24.0.0

USER root

COPY startup.sh /opt/startup.sh

RUN chmod +rx /opt/startup.sh

ARG env=dev

ENV ENVIRONMENT=env

# Expose the port on which Keycloak runs (default is 8080)
EXPOSE 8080

CMD ["./startup.sh"];