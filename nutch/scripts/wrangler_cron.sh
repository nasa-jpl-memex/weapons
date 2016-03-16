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

# Expect Weekly Segments to be in ~/wrangler_crawl/production
# Ensure Dependency Bash Scripts are enabled
cd /data2/USCWeaponsStatsGathering/nutch
find /usr/local/memex/wrangler_crawl/production -type d -name "segments" > current_scp_wrangler_segments.txt
# Dump Segments
./wrangler_batch_dump.sh current_scp_wrangler_segments.txt
mv /usr/local/memex/wrangler_crawl/production/* /usr/local/memex/wrangler_crawl/archive/

# Find delta updates/docIDs of fileDumper
cd runtime/local/logs
today=$(date +"%m-%d-%y")
updates=$today"DocIDs.txt"
cat hadoop.log | grep Writing | cut -d" " -f 8 | cut -d"[" -f 2 | cut -d"]" -f 1 > /usr/local/memex/wrangler_crawl/deltaUpdates/$updates

# Chunk Files
cd /usr/local/memex/wrangler_crawl/deltaUpdates/
mkdir partFiles
split -l 300000 $updates partFiles/parts

# Fire off parser-indexer
source /usr/local/memex/jdk8.sh
# Choose relevant timeout value for Tika Parsers default 1 min
ls partFiles/ | while read i; do  echo "sleep 5; echo $i; nohup java -jar /usr/local/memex/imagecat/tmp/parser-indexer/target/nutch-tika-solr-1.0-SNAPSHOT.jar postdump -list partFiles/$i -threads 1 -solr http://127.0.0.1:8983/solr/imagecatdev -batch 500 -timeout 60000 > outs/nohup-$i.out & " ; done > cmd.txt

cat cmd.txt | bash
echo "JOB COMPLETE"
# Dont reIndex old docIDs
rm -rf partFiles/