#!/bin/bash

while read p; do
  echo $p
  #ls $p
  ./dump_segments.sh $p
done <$1

./generate_stats.sh
