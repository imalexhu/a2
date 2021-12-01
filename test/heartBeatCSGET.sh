#this script tests for getting getting removed content after 12 seconds


java ContentServer 0 1 12 &
CS=$!
sleep 1
java GETClient 12 &
GET=$!
sleep 6
java AggregationServer &
AGRE=$!
sleep 3
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $GET
kill $CS