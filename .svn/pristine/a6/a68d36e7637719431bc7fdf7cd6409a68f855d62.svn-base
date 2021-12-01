echo "Basic Put Get Test" 
./test/PutGetTest.sh > test/result/PutGetTest.txt
if ! diff -q test/result/PutGetTest.txt test/expected/PutGetTest.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "Put and get with multiple content Servers" 
./test/multiplePutGetTest.sh > test/result/multiplePutGetTest.txt
if ! diff -q test/result/multiplePutGetTest.txt test/expected/multiplePutGetTest.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "Content for dead server expires while new content replaces old" 
./test/3Starts1Dies2Remains.sh > test/result/3Starts1Dies2Remains.txt
if ! diff -q test/result/3Starts1Dies2Remains.txt test/expected/3Starts1Dies2Remains.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "ATOM server remembers state on crash" 
./test/faultTolleranceStartup.sh > test/result/faultTolleranceStartup.txt
if ! diff -q test/result/faultTolleranceStartup.txt test/expected/faultTolleranceStartup.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "GETClient and ContentServer Retries on ATOM congestion" 
./test/heartBeatCSGET.sh > test/result/heartBeatCSGET.txt
if ! diff -q test/result/heartBeatCSGET.txt test/expected/heartBeatCSGET.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "Content is deleted after 12 seconds" 
./test/heartBeat.sh > test/result/heartBeat.txt
if ! diff -q test/result/heartBeat.txt test/expected/heartBeat.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "Contents reordered by lamport clock" 
./test/reorderByLamportClock.sh > test/result/reorderByLamportClock.txt
if ! diff -q test/result/reorderByLamportClock.txt test/expected/reorderByLamportClock.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
sleep 3
echo "Content is updated" 
./test/update.sh > test/result/update.txt
if ! diff -q test/result/update.txt test/expected/update.txt &>/dev/null; then   >&2 echo "Failed"; else echo "Success "; fi
