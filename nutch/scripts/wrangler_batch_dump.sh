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

FULL_DUMP_PATH=/data2/USCWeaponsStatsGathering/nutch/full_dump
IMG_DUMP_PATH=/data2/USCWeaponsStatsGathering/nutch/image_dump
VID_DUMP_PATH=/data2/USCWeaponsStatsGathering/nutch/video_dump

while read p; do
  echo $p
  cd $2
  ./bin/nutch dump -segment $p -outputDir $FULL_DUMP_PATH -reverseUrlDirs
  ./bin/nutch dump -segment $p -mimetype image/gif image/jpeg image/png image/svg+xml image/tiff image/vnd.microsoft.icon image/webp image/x-ms-bmp -outputDir $IMG_DUMP_PATH -reverseUrlDirs
  ./bin/nutch dump -segment $p -mimetype video/x-msvideo video/mp4 video/x-ms-wmv video/quicktime video/x-m4v -outputDir $VID_DUMP_PATH -reverseUrlDirs 
done <$1

#./generate_stats.sh
