# this script 2 stays alive and one dies
java AggregationServer &
AGRE=$!
sleep 1
java ContentServer 0 1 8 &
CS1=$!
sleep 1
java ContentServer 1 2 8 &
CS2=$!
sleep 1
java ContentServer 2 3 50 &
sleep 1
CS3=$!
sleep 3
java GETClient 9 &
GET=$!
sleep 8.5
echo
echo "CS 1 and 2 kept updating, while CS3 has died"
echo

sleep 1
rm faultTollerance.txt
touch faultTollerance.txt
kill $AGRE
kill $CS1
kill $CS2
kill $CS3
kill $GET