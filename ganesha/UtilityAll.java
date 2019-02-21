/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganesha;
import java.util.LinkedList;
import java.util.Scanner; //Scanner Function to take in the Input Values 

/**
 *
 * @author Nishant
 */
public class UtilityAll {
    
}  
class HeapNode{
        int vertex;
        int distance;
        int clusterid;
    }
class DijkstraUsingMinHeap {
    Graph city;
    LinkedList<Edge>[] adjacencylist;
    int vertices;
    int dist[];
            
    
        public HeapNode[] dijkstra_GetMinDistances(Graph city, int sourceVertex){
            this.city = city;
            this.adjacencylist = city.adjacencylist;
            int INFINITY = Integer.MAX_VALUE;
            vertices = city.getNoOfVericies();
            boolean[] SPT = new boolean[vertices];

//          //create heapNode for all the vertices
            HeapNode [] heapNodes = new HeapNode[vertices];
            for (int i = 0; i <vertices ; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].distance = INFINITY;
            }

            //decrease the distance for the first index
            heapNodes[sourceVertex].distance = 0;

            //add all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(vertices);
            for (int i = 0; i <vertices ; i++) {
                minHeap.insert(heapNodes[i]);
            }
            //while minHeap is not empty
            while(!minHeap.isEmpty()){
                //extract the min
                HeapNode extractedNode = minHeap.extractMin();

                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                SPT[extractedVertex] = true;

                //iterate through all the adjacent vertices
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i <list.size() ; i++) {
                    Edge edge = list.get(i);
                    int destination = edge.destination;
                    //only if  destination vertex is not present in SPT
                    if(SPT[destination]==false ) {
                        ///check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance
                        int newKey =  heapNodes[extractedVertex].distance + edge.weight ;
                        int currentKey = heapNodes[destination].distance;
                        if(currentKey>newKey){
                            decreaseKey(minHeap, newKey, destination);
                            heapNodes[destination].distance = newKey;
                        }
                    }
                }
            }
            //print SPT
//            printDijkstra(heapNodes, sourceVertex);
            return getAllDistance(heapNodes);
        }

        public void decreaseKey(MinHeap minHeap, int newKey, int vertex){

            //get the index which distance's needs a decrease;
            int index = minHeap.indexes[vertex];

            //get the node and update its value
            HeapNode node = minHeap.mH[index];
            node.distance = newKey;
            minHeap.bubbleUp(index);
        }
        
        public HeapNode[] getAllDistance(HeapNode[] resultSet){
            return resultSet;
        }
        
        public void printDijkstra(HeapNode[] resultSet, int sourceVertex){
            System.out.println("Dijkstra Algorithm: (Adjacency List + Min Heap)");
            for (int i = 0; i <vertices ; i++) {
                System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
                        " distance: " + resultSet[i].distance);
            }
        }
    
    class MinHeap{
        int capacity;
        int currentSize;
        HeapNode[] mH;
        int [] indexes; //will be used to decrease the distance


        public MinHeap(int capacity) {
            this.capacity = capacity;
            mH = new HeapNode[capacity + 1];
            indexes = new int[capacity];
            mH[0] = new HeapNode();
            mH[0].distance = Integer.MIN_VALUE;
            mH[0].vertex=-1;
            currentSize = 0;
        }

        public void display() {
            for (int i = 0; i <=currentSize; i++) {
                System.out.println(" " + mH[i].vertex + "   distance   " + mH[i].distance);
                
            }
            System.out.println("________________________");
        }

        public void insert(HeapNode x) {
            currentSize++;
            int idx = currentSize;
            mH[idx] = x;
            indexes[x.vertex] = idx;
            bubbleUp(idx);
        }

        public void bubbleUp(int pos) {
            int parentIdx = pos/2;
            int currentIdx = pos;
            while (currentIdx > 0 && mH[parentIdx].distance > mH[currentIdx].distance) {
                HeapNode currentNode = mH[currentIdx];
                HeapNode parentNode = mH[parentIdx];

                //swap the positions
                indexes[currentNode.vertex] = parentIdx;
                indexes[parentNode.vertex] = currentIdx;
                swap(currentIdx,parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx/2;
            }
        }

        public HeapNode extractMin() {
            HeapNode min = mH[1];
            HeapNode lastNode = mH[currentSize];
//            update the indexes[] and move the last node to the top
            indexes[lastNode.vertex] = 1;
            mH[1] = lastNode;
            mH[currentSize] = null;
            sinkDown(1);
            currentSize--;
            return min;
        }

        public void sinkDown(int k) {
            int smallest = k;
            int leftChildIdx = 2 * k;
            int rightChildIdx = 2 * k+1;
            if (leftChildIdx < heapSize() && mH[smallest].distance > mH[leftChildIdx].distance) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heapSize() && mH[smallest].distance > mH[rightChildIdx].distance) {
                smallest = rightChildIdx;
            }
            if (smallest != k) {

                HeapNode smallestNode = mH[smallest];
                HeapNode kNode = mH[k];

                //swap the positions
                indexes[smallestNode.vertex] = k;
                indexes[kNode.vertex] = smallest;
                swap(k, smallest);
                sinkDown(smallest);
            }
        }

        public void swap(int a, int b) {
            HeapNode temp = mH[a];
            mH[a] = mH[b];
            mH[b] = temp;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public int heapSize(){
            return currentSize;
        }
    }
}


class DijkstraPath
{
	static Scanner scan; // scan is a Scanner Object 
	public static void main(String[] args) 
	{ 
		int[] preD = new int[5]; 
		int min = 999, nextNode = 0; // min holds the minimum value, nextNode holds the value for the next node. 
		scan = new Scanner(System.in); 
		int[] distance = new int[5]; // the distance matrix 
		int[][] matrix = new int[5][5]; // the actual matrix 
		int[] visited = new int[5]; // the visited array 

		System.out.println("Enter the cost matrix"); 

		for (int i = 0; i < distance.length; i++) 
		{ 
			visited[i] = 0; //initialize visited array to zeros 
			preD[i] = 0; 

			for (int j = 0; j < distance.length; j++) 
			{ 
				matrix[i][j] = scan.nextInt(); //fill the matrix 
				if (matrix[i][j]==0) 
					matrix[i][j] = 999; // make the zeros as 999 
			} 
		} 

		distance = matrix[0]; //initialize the distance array 
		visited[0] = 1; //set the source node as visited 
		distance[0] = 0; //set the distance from source to source to zero which is the starting point 

		for (int counter = 0; counter < 5; counter++) 
		{ 
			min = 999; 
			for (int i = 0; i < 5; i++) 
			{ 
				if (min > distance[i] && visited[i]!=1) 
				{ 
					min = distance[i]; 
					nextNode = i; 
				} 
			} 

			visited[nextNode] = 1; 
			for (int i = 0; i < 5; i++) 
			{ 
				if (visited[i]!=1) 
				{ 
					if (min+matrix[nextNode][i] < distance[i]) 
					{ 
						distance[i] = min+matrix[nextNode][i]; 
						preD[i] = nextNode; 
					} 

				} 

			} 

		} 

		for(int i = 0; i < 5; i++) 
			System.out.print("|" + distance[i]); 

		System.out.println("|"); 

		int j; 
		for (int i = 0; i < 5; i++) 
		{ 
			if (i!=0) 
			{ 

				System.out.print("Path = " + i); 
				j = i; 
				do
				{ 
					j = preD[j]; 
					System.out.print(" <- " + j); 
				} 
				while(j != 0); 
			} 
			System.out.println(); 
		} 
	}
}
