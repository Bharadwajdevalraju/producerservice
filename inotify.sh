#!/bin/bash -eu
DIRECTORY_TO_OBSERVE="src"      # might want to change this
function block_for_change {
  inotifywait --recursive \
    --event modify,move,create,delete \
    $DIRECTORY_TO_OBSERVE
}
       # might want to change this too
function build {
fuser -k 3032/tcp;
lein uberjar;
java -jar target/uberjar/producerservice-0.1.0-SNAPSHOT-standalone.jar &
echo "restarted"
}
build
while block_for_change; do
  build
done
