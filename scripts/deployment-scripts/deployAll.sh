#!/bin/bash

COMMAND=$1

for d in */; do
    echo "$d"
    ( cd $d && ./deploy.sh $COMMAND )
done
