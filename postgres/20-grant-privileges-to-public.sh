#!/usr/bin/env bash

set -e

export PGPASSWORD=postgres
psql -U postgres -d camunda -c "GRANT ALL PRIVILEGES ON SCHEMA public TO program;"
