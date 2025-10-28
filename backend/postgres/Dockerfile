# Simple Postgres Dockerfile that extends the official image
# This Dockerfile copies any initialization scripts placed in ./initdb
# into the Postgres entrypoint directory so they run on first container start.

FROM postgres:16

LABEL org.opencontainers.image.source="local/chat-app/backend/postgres"

ENV LANG=C.UTF-8

# Copy initialization scripts (SQL/.sh) into the directory processed by the
# official postgres image at container startup. If you don't need extras you
# can leave initdb empty.
COPY ./initdb /docker-entrypoint-initdb.d

# Expose the default Postgres port (optional — compose handles ports)
EXPOSE 5432

# Entrypoint and CMD remain those of the base image
