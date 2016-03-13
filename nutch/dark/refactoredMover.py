#!/usr/bin/env python2.7
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

import argparse, csv, hashlib, os, shutil

def mover(inCSV, outCSV, dumpPath):

    with open(outCSV, "wb") as outF:
        writer = csv.writer(outF)
        writer.writerow(["onionDomain", "onionAdURL", "onionPageTitle", "oldPath", "FinalDumpPath"]) #"onionAdHashedFilePath"
                
        with open(inCSV, "rb") as inF:        
            reader = csv.reader(inF, delimiter=",")
            for row in reader:

                domain = row[1].strip()
                URL = row[3].strip()
                path = row[4].strip()
                record = [domain, URL, " ", " "]
                
                hashedAdURL = hashlib.sha256(URL).hexdigest().upper()

                #change filename inline to hashedAdURL to persist deDup
                new_filename = path.split("/")[0] + "/" + hashedAdURL
                try:
                    os.rename(path, new_filename)  #os.system("mv {0} {1}".format(path, new_filename))
                except:
                    continue
                #record.append(new_filename)

                #construct Dump filepath   #onion/swehackmzys2gpmb/www
                finalDumpPath = dumpPath.rstrip("/") + "/" + "/".join(reversed(domain.split("."))) 
                if not os.path.exists(finalDumpPath):                    
                    os.makedirs(finalDumpPath)
                shutil.move(new_filename, finalDumpPath + "/" + hashedAdURL)                
                record.append(finalDumpPath + "/" + hashedAdURL)

                writer.writerow(record)

if __name__ == "__main__":

    parser = argparse.ArgumentParser('dark Cron workflow')
    parser.add_argument('--inCSV', required=True)
    parser.add_argument('--outCSV', required=True)
    parser.add_argument('--dumpPath', required=True, help='absolute path to dump Dir')
    args = parser.parse_args()

    if args.inCSV and args.outCSV and args.dumpPath:
        mover(args.inCSV, args.outCSV, args.dumpPath)
        print "Finished moving fetched files to DeDup Dump Path", args.dumpPath
        print "Running parser-indexer now"
