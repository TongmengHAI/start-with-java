#!bin/bash

URL="http://localhost:8082/hello"

# ##======================[infinite loop request]======================
# while :
# do
#  curl -s -o /dev/null -I -w "%{http_code}" -X GET "$URL"
#  echo   echo "Request $i -> $code"
#  sleep 0.01   # adjust delay (0.01s)
# done


# ##======================[ loop request base on NUM LOOP]======================
NUM_LOOP=120
for ((i=1; i<=NUM_LOOP; i++))
do
    code=$(curl -s -o /dev/null -I -w "%{http_code}" -X GET "$URL")
    echo "Request $i -> $code"
    sleep 0.01   # adjust delay (0.01s)
done


# ##curl -x GET http://localhost:8082/hello
