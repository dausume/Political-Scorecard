#!/bin/bash

echo "Inside startup.sh";

# Check if ENVIRONMENT variable is set, it should have been set in either dockerfile or docker-compose file.
if [ -z "$ENVIRONMENT" ]; then
    echo "Please specify the environment (dev or prod) using ENVIRONMENT variable, the environment will be set to dev by default and should NOT be used for a real website, do the work to configure your own security certificates and enable prod for a real world application."
    export ENVIRONMENT=dev
fi

# Define configuration file paths based on environment with CONFIG_FILE
# Define the actual line command for starting up keycloak itself for the given environment using STARTUP.
if [ "$ENVIRONMENT" = "dev" ]; then
    CONFIG_FILE="./keycloak_environments/kc_dev.conf"
    STARTUP="bin/kc.sh dev-start"
    echo "Running in dev mode"
elif [ "$ENVIRONMENT" = "prod" ]; then
    CONFIG_FILE="/keycloak_environments/kc_prod.conf"
    STARTUP="bin/kc.sh start"
    echo "Running in prod mode"
else
    echo "Invalid environment specified. Please specify either 'dev' or 'prod'"
    exit 1
fi

# Start Keycloak
# Use 'exec' to replace the current process with the new one to avoid creating a new process
exec "$STARTUP" --config-file="$CONFIG_FILE"