#This script first populates the agre server with a start up file and then GETs the content
#The contents should be in reverse order, please loook at /startup/1.txt vs the log generated
#note, the in 1.txt the second line of each entry represents the lamport clock

java AggregationServer 1 &
AGRE=$!
sleep 2
java GETClient 10 &
GET=$!
sleep 3
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $GET