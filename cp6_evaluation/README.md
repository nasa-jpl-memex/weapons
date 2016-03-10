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

 * Data contained within crawldb, linkdb, segments contains all of the crawl data acquired during the crawl. Stats are as follows
```

```
 * We provide *readdb -topN metrics* for every round of crawling. This aids us in determining which domains are relevant for domain discovery. Each file can be opened and the URL-score interpreted. 
 * We provide *host statistics* (produced using the Nutch domain statistics tool) which can be interpreted similar to the readdb -topN stats.
 * We provide *crawl completeness stats* (produced using the Nutch Crawl completeness tool) which can be interpreted similar to both readdb -topN and host stats.
 * Finally we provide our evaluation input based on the [domain discovery evaluation](https://memexproxy.com/wiki/pages/viewpage.action?pageId=14156679#Challenge5&6EvaluationCriteria-EvaluationCriteria) documentation. More on this is provided below.

# Evaluation Results

TODO

TODO
