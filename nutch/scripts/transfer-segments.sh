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

is_good_to_transfer(){
	for server in `squeue | grep -i NUTCH | awk '{print $8}'`
	do
		if ssh -qo "StrictHostKeyChecking no" ${server} ps -ef | grep -i copytolocal | grep -v "grep"; then exit 1; fi
	done
}

main(){
	is_good_to_transfer;
	scp -qr /work/03755/tg830544/wrangler/memex/crawling/production/* mdeploy@imagecat.dyndns.org:/usr/local/memex/wrangler_crawl/production/
	mv /work/03755/tg830544/wrangler/memex/crawling/production/* /work/03755/tg830544/wrangler/memex/crawling/archive/
}

main;

# Dump Segments
# Ensure Dependency Bash Scripts are enabled
ssh mdeploy@imagecat.dyndns.org
cd /data2/USCWeaponsStatsGathering/nutch
find /usr/local/memex/wrangler_crawl/production -type d -name "segments" > current_scp_wrangler_segments.txt
./wrangler_batch_dump.sh current_scp_wrangler_segments.txt
mv /usr/local/memex/wrangler_crawl/production/* /usr/local/memex/wrangler_crawl/archive/

# Find delta updates/docIDs of fileDumper
cd runtime/local/logs
today=$(date +"%m-%d-%y")
updates=$today"DocIDs.txt"
cat hadoop.log | grep Writing | cut -d" " -f 8 | cut -d"[" -f 2 | cut -d"]" -f 1 > /usr/local/memex/wrangler_crawl/deltaUpdates/$updates

# Fire off parser-indexer
source ~/jdk8.sh
cd ~/imagecat/tmp/parser-indexer
java -jar parser-indexer/target/nutch-tika-solr-1.0-SNAPSHOT.jar postdump -list /usr/local/memex/wrangler_crawl/deltaUpdates/$updates -solr http://localhost:8983/solr/imagecatdev -timeout 70000
echo "JOB COMPLETE"