#this script tests for getting the backup startup file if ATOM server crashes

java AggregationServer &
AGRE=$!
sleep 1
java ContentServer 0 1 50 &
CS=$!
sleep 2
java GETClient 3 &
GET=$!
sleep 1

echo
echo "ATOM server has just crashed"
echo

kill $AGRE
sleep 4
echo
echo "ATOM server has just crashed"
echo
java AggregationServer &
AGRE2=$!
sleep 4
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE2
kill $CS
kill $GET