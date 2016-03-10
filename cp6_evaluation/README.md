Memex UK Imagehack Challenge Problem #6
=======================================

@Contact lewis.j.mcgibbney@jpl.nasa.gov CC memex-jpl@googlegroups.com

# Challenge Problem 6

[Memex Wiki Link](https://memexproxy.com/wiki/pages/viewpage.action?pageId=14156679)

# Crawler Configuration

 * Domain discovery was undertaken using Apache Nutch 1.12-SNAPSHOT (March 10th 2016)
 * To replicate crawler configuration pull Nutch trunk and replace *bin* and *conf* directories with the directories present within this folder.
 * Seed Nutch with the URLs present within the *urls* directory. These represent the [top 25](https://memexproxy.com/wiki/pages/viewpage.action?pageId=14156679#Challenge5&6EvaluationCriteria-LISTOFSEEDSITES) sites.
 * Build Nutch and this will simulate the conditions under which CP6 was attempted.

# Evaluation Run

 * As with all Nutch crawl data the crawldb, linkdb and segments data structures contain all of the acquired crawl data. Stats are provided in subsections below.
 * We provide **readdb -topN metrics** (produced using the Nutch [CrawlDbReader](https://github.com/apache/nutch/blob/trunk/src/java/org/apache/nutch/crawl/CrawlDbReader.java) for every round of crawling. This aids us in determining which domains are relevant for domain discovery. Each file can be opened and the URL-score interpreted. 
 * We provide **host metrics** (produced using the Nutch [DomainStatistics](https://github.com/apache/nutch/blob/trunk/src/java/org/apache/nutch/util/domain/DomainStatistics.java) tool) which can be interpreted similar to the readdb -topN stats.
 * We provide **crawl completeness metrics** (produced using the Nutch [CrawlCompletionStats](nutch/src/java/org/apache/nutch/util/CrawlCompletionStats.java) tool) which can be interpreted similar to both readdb -topN and host stats.
 * Finally we provide our evaluation input for the CP6 challenge based on the [domain discovery evaluation](https://memexproxy.com/wiki/pages/viewpage.action?pageId=14156679#Challenge5&6EvaluationCriteria-EvaluationCriteria) documentation. More on this is provided below.

# Dataset

## Crawl Database Statistics
```
[lmcgibbn@crawl nutch]$ ./runtime/local/bin/nutch readdb cp6_evaluation/crawldb/ -stats
CrawlDb statistics start: cp6_evaluation/crawldb/
Statistics for CrawlDb: cp6_evaluation/crawldb/
TOTAL urls:     540105
retry 0:        539648
retry 1:        457
min score:      0.0
avg score:      0.27996606
max score:      1.0
status 1 (db_unfetched):        504977
status 2 (db_fetched):  32191
status 3 (db_gone):     478
status 4 (db_redir_temp):       833
status 5 (db_redir_perm):       1283
status 6 (db_notmodified):      4
status 7 (db_duplicate):        339
CrawlDb statistics: done
```

The dataset itself can be obtain from 




# Evaluation Results

The submitted list can be located at the [JPL contribution to CP#6 problem](https://memexproxy.com/wiki/display/MPM/CP+%235+and+%236+Output#CP#5and#6Output-JPL).
