

Usage

```
./wrangler_cron.sh > pipe_Output.txt
```

1. Expect fresh weekly segments in $WRANGLER_SEGS

2. Script/Cron Job should Run & Complete prior to the transition of dates (midnight)
   Reason: Document IDs are grepped from hadoop.log based on date of File Dump. Hence Nutch FileDumper portion of the Cron job should complete prior to the transition of dates

3. Stderr is piped to terminal
