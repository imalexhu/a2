#this script tests for single PUT and GET


java AggregationServer &
AGRE=$!
sleep 2
java ContentServer 0 1 10 &
CS=$!
sleep 1
java GETClient 10 &
GET=$!
sleep 3
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $CS
kill $GET