#this script tests for getting updated content after 12 seconds

java AggregationServer &
AGRE=$!
sleep 1
java ContentServer 0 1 10 &
CS=$!
sleep 1
java GETClient 12 &
GET=$!
sleep 15
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $CS
kill $GET