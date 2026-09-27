FROM mysql:8.4

# Import the world database when a fresh database container initializes.
COPY world.sql /docker-entrypoint-initdb.d/world.sql