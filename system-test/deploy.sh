#!/bin/bash
set -e

docker-compose up -d rabbitMq

sleep 40s

docker-compose up -d payments tokens accounts
