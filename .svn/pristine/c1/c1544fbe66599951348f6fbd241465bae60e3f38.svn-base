#this script tests for getting getting removed content after 12 seconds

java AggregationServer &
AGRE=$!
sleep 1
java ContentServer 0 1 50 &
CS=$!
sleep 1
java GETClient 12 &
GET=$!
sleep 10
sleep 3 
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $GET
kill $CS