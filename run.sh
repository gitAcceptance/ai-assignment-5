#!/bin/bash

chmod +x gradlew

DEST_PATH="src/main/resources/$1"

if [ "$1" = "" ]; then
    echo Default Environment Mode
    ./gradlew build
    java -jar build/libs/q-learning.jar input.txt
else
    echo User-supplied Environment Mode
    cp $1 $DEST_PATH
    ./gradlew build
    java -jar build/libs/q-learning.jar $1

fi