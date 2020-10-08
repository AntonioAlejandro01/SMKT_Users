#!/bin/sh
# wait-for-eureka-server

set -e
  
host="$1"
shift
cmd="$@"
sleep 40
until [ "$(curl -s -o /dev/null -w "%{http_code}" http://$host:8761/eureka)" != "200" ]
do
  >&2 echo "eureka is unavailable - sleeping"
  sleep 2
done
>&2 echo "Eureka is up - executing command"
sleep 5
exec $cmd