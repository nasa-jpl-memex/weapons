#!/bin/bash

#Authors: Karanjeet Singh
#          Harshavardhan Manjunatha
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
ssh mdeploy@imagecat.dyndns.org
cd /data2/USCWeaponsStatsGathering/nutch
find /usr/local/memex/wrangler_crawl/production -type d -name "segments" > current_scp_wrangler_segments.txt
./wrangler_batch_dump.sh current_scp_wrangler_segments.txt
mv /usr/local/memex/wrangler_crawl/production/* /usr/local/memex/wrangler_crawl/archive/

# TODO: find delta updates docIDs of fileDumper
# TODO: fire off parser-indexer