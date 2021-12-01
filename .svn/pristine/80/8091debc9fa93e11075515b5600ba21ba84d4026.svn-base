# this script tests for multiple put and get
java AggregationServer &
AGRE=$!
sleep 1
java ContentServer 0 1 10 &
CS1=$!
sleep 1
java ContentServer 1 2 10 &
CS2=$!
sleep 1
java ContentServer 2 3 10 &
CS3=$!
sleep 1
java ContentServer 3 4 10 &
CS4=$!
sleep 1
java ContentServer 4 5 10 &
CS5=$!
sleep 1
java ContentServer 5 6 10 &
CS6=$!
sleep 1
java ContentServer 6 7 10 &
CS7=$!
sleep 1
java GETClient 10 &
GET=$!
sleep 3
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $CS1
kill $CS2
kill $CS3
kill $CS4
kill $CS5
kill $CS6
kill $CS7
kill $GET