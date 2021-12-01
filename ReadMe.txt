This is Alex Hu a1766749's submission for Assignment 2

###PLEASE MAKE SURE THE FILE NAME FOR THIS FOLDER IS assignment2 ###

To compile the files run 

javac *.java

This will create the runnable files AggregationServer, ContentServer and GETClient

The Aggregation server takes in 1 arg for future startup scenarioes

The ContentServer takes in 3 parameters being 
arg0 : The unique name and port number
arg1 : The first index of the first content the content server will server
arg2 : How frequently a PUT request is sent to the ATOM server

The ContentServer takes in 3 parameters being 
arg0 : How requestly the client will send a GET request to the server

please run this to be able to test

chmod +x test.sh
and 
chmod +x /test/*.sh

To run all the test files, run ./test.sh

Please be aware that test.sh takes about 2 minutes to run fully, and canceling it midst exectution will lead to leaked threads. 

You can see the outputed files in test/result, each test file also has a comment briefly explaing the test case and what should happen. 

For the test case 3Starts1Dies2Remains. There is some synchronisation issue with the content servers, that results in the order of CS1 and CS2 being flipped.
This has been patched and shouldnt happen, but if it does. Look at the functionality of the code, and it should be identical between the expected and result. 





