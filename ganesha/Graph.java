/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganesha;

import java.util.LinkedList;

/**
 *
 * @author Nishant
 */
class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

   
    public class Graph {
        private int vertices;
        private int flag[];
        private int lakeid[];
        private boolean visited[];
        LinkedList<Edge>[] adjacencylist;
        
        int getNoOfVericies(){
            return vertices;
        }
        
        void setGanesha(int v){
            flag[v] = 1;
        }
        void setLakeId(int v,int lakei){
            lakeid[v] = lakei;
        }
        int getLakeId(int v){
            return lakeid[v];
        }
        void setVisited(int v){
            visited[v] = true;
        }
        boolean getVisited(int v){
            return visited[v];
        }
        
        void setLake(int v){
            flag[v] = 2;
        }
        
        int getFlag(int v){
            return flag[v];
        }
        
        Graph(int vertices) {
            this.vertices = vertices;
            flag = new int[vertices];
            lakeid = new int[vertices];
            visited = new boolean[vertices];
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
                visited[i] = false;
            }
        }

        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge); //for undirected graph
        }
        
        static void printGraph(Graph graph) 
        {        
            for(int v = 0; v < graph.vertices; v++) 
            { 
                if(graph.adjacencylist[v].size()>0){
                    System.out.println("Adjacency list of vertex "+ (v+1)); 
    //                System.out.print("head"); 
                    for( Edge pCrawl: graph.adjacencylist[v]){ 
                        System.out.print(" -> "+(pCrawl.destination+1)+"("+pCrawl.weight+")"); 
                    } 
                    System.out.println("\n"); 
                }
            } 
        } 
        
    }
