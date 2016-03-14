#!/bin/bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#
for i in {1..7}
do
  past_day=$(date -d "-$i day" '+%Y-%m-%d')
  echo "Fetching & Ingesting for $past_day"
  outfile=$past_day"weap.out"

  #cd path/to/dark/script
  cd ~/darkWeapons/dark_Cron_production
  source dark.env
  python es_allSearch.py wea.txt outFiles/$outfile weapDir 2000000 _type $past_day

  updates=$past_day"Updates.csv"
  python refactoredMover.py --inCSV outFiles/$outfile --outCSV updates/$updates --dumpPath /data2/USCWeaponsStatsGathering/nutch/full_dump

  lineCtStr=$(wc -l updates/$updates)
  lineCt=($lineCtStr)  
  if [ $lineCt -eq 1 ]
  then
    echo "Skipping Empty file"  
    continue
  fi

  source ~/jdk8.sh
  cd ~/imagecat/tmp/parser-indexer
  java -cp target/nutch-tika-solr-1.0-SNAPSHOT.jar edu.usc.cs.ir.cwork.files.DarkDumpPoster -nutch ~/darkWeapons/dark_Cron_production/nutch/runtime/local -solr http://localhost:8983/solr/imagecatdev -timeout 70000 -list ~/darkWeapons/dark_Cron_production/updates/$updates
done
echo "JOB COMPLETE"