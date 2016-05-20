## Prerequisites

Download nutch (version 1.11) from source
Download and Install Maven
Download and Install Firefox 28.0

This project has a dependency on nutch plugin **protocol-interactiveselenium** 

From nutch root directory, run the following commands.

```
ant
cd build/protocol-interactiveselenium 
mvn install:install-file -Dfile=protocol-interactiveselenium.jar \
     -DgroupId=org.apache.nutch \
     -DartifactId=<protocol-interactiveselenium \
     -Dversion=1.11 \
     -Dpackaging=jar
```

This will add protocol-interactiveselinium to your local maven repository.
Your dependency in pom.xml should look like this. It is already included in this project. 
```
        <dependency>
            <groupId>org.apache.nutch</groupId>
            <artifactId>protocol-interactiveselenium</artifactId>
            <version>1.11</version>
            <type>jar</type>
        </dependency>
```

## Running

**Configs in nutch**
Use the protocol-interactiveselenium plugin by including it in nutch-site.xml

```
<property>
  <name>plugin.includes</name>
  <value>protocol-interactiveselenium|urlfilter-regex|parse-(html|tika)|index-(basic|anchor)
  |indexer-solr|scoring-opic|urlnormalizer-(pass|regex|basic)</value>
</property>
```

Include your handlers by defining them in nutch-site.xml
For example,
```
<property>
    <name>interactiveselenium.handlers</name>
     <value>SearchHandler</value>
     <description></description>
</property>
```

Define your seed in $(NUTCH_HOME)/urls/seed.txt and other configs as usual.

You are all set!