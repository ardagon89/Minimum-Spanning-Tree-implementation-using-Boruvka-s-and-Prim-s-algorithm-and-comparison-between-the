README:
======

Short Project # 09: Minimum Spanning Tree implementation using Boruvka's and Prim's algorithm and comparison between them.


Authors :
------
1) Shariq Ali SXA190016
2) Bhushan Vasisht BSV180000


How to compile and run the code:
-------------------------------
The file MST.java, BinaryHeap.java, Graph.java should be placed inside the folder named as 'sxa190016' which is the package name.
Run the below commands sequentially to execute the program

1) The command prompt path should be in "sxa190016" directory
2) javac Graph.java 
2) javac BinaryHeap.java
2) javac MST.java
3) java -Xmx16g MST


Methods in Code:
----------------
The following methods are written for MST class:

MST		- Constructor to initialize member variables

MSTVertex	- Constructor to initialize member variables

make		- Method to get an MSTVertex from Graph.Vertex

putIndex	- Set the index of the MSTVertex

getIndex	- Get the index of the MSTVertex

compareTo	- Compare one MSTVertex with another

boruvka		- Find the MST using Boruvka's algorithm

countAndLabel	- Count the number of components and label them

addSafeEdges	- Find and add safe edges to F

getMSTWeight	- The total weight of the MST graph F and add MST edges to this.mst

prim2		- Make the MST using Prim's Algorithm

mst		- Find the MST using one of the algorithms based on choice

main		- main function to test the class


The main function:
-------------------
When you run the main function, it will
1. Choose the algorithm to test
2. Initialize scanner
3. Select algorithm based on cmd input
4. Construct the graph
5. Set start vertex as the first vertex
6. Initialize the timer
7. Initialize MST algorithm
8. Print the total weight of the MST
9. Print the time taken


Report:
-------------------
Note: 
# All the values are in milli-seconds
# M - million values

                      				Execution Time
		(V=10k & E=1M)	(V=100k & E=30M)	(V=100k & E=300M)	(V=1M & E=300M)
Boruvka:		911		29755			-			-
Prim:			303		11279			-			-

Note: I generated the last 2 input files but got heap error on them on the first try. After increasing the JVM heap space I executed the program for more than 1 hour but it is still processing. So I am not including the result of the last 2 files in this report in order to submit the project within the stipulated deadline. But I expect to get similar output for the last 2 files as for the first 2 files.


Summary:
-------------------
As we can see in the above table Prim's algorithm is much faster (almost 3x) as compared to Boruvka's algorithm, specially for large input sizes. This may be attributed to the countAndLabel sub-routine in Boruvka's algorithm, which has to be called after each iteration, and therefore adds to the total time. Also, as can be seen in the above table, the execution time of both algorithms increases almost linerly with the increase in input size.