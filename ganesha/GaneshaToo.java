/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganesha;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nishant
 */
public class GaneshaToo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int noofvertices = 29;
        int ganeshat[] = {2, 18, 10, 15, 29, 20, 1, 16 ,22,14};
        int lakeat[] = {23,26,28};
        int nodeMatrix[][] = {{1,3,4},{2,3,5},{3,2,6,4},{4,1,3,7,10},{5,2,6,8,12,11},
                            {6,3,7,8,5},{7,4,6,9},{8,6,9,13,5},{9,7,8,10,14},{10,4,9,15},
                            {11,5,12,16},{12,13,18,5,11},{13,12,8,14,19},{14,9,15,13},{15,10,14,22}, 
                            {16,11,17,23,24},{17,16,18,24},{18,17,12,19,25},{19,18,13,20},
                            {20,19,21,26},{21,20,22,27},{22,21,29,15},{23,16,24},{24,23,16,17,25},
                            {25,24,18,26},{26,25,20,27},{27,21,29,26,28},{28,27,29},{29,28,22,27}};
        int weightMatrix[][] = {{4,1},{4,2},{4,1,4},{1,4,1,4},{2,4,4,3,3},{1,4,1,4},{1,4,1},{1,4,3,4},
                               {1,3,4,2},{4,3,1},{3,2,4},{1,1,3,2},{1,3,4,1},{2,3,4},{1,3,1},{4,2,3,3},
                               {2,3,3},{3,1,1,3},{1,1,2},{2,2,4},{2,3,4},{3,1,1},{3,2},{2,3,3,2},{2,3,2},
                               {2,4,3},{4,4,3,3},{3,4},{4,1,4}};
//        int balancingThres = 2;
        
//        for(int i=0;i<29;i++){
//            if(nodeMatrix[i].length-1 != weightMatrix[i].length)
//                System.out.println("Error "+i+weightMatrix[i][0]+" "+weightMatrix[i][1]);
//        }

        Graph cityMap = new Graph(noofvertices);
        for(int i:ganeshat)
        {
            cityMap.setGanesha(i-1);
        }
        for(int i:lakeat)
        {
            cityMap.setLake(i-1);
        }
        for(int i=0;i<noofvertices;i++){
//            int k = 0;
            for(int j=1;j<nodeMatrix[i].length;j++){
                cityMap.addEdge(nodeMatrix[i][0]-1, nodeMatrix[i][j]-1, weightMatrix[i][j-1]);
            }
        }
        //TODO clustering
        HeapNode allDist[][] = new HeapNode[lakeat.length][noofvertices];
        DijkstraUsingMinHeap dijkstraUsingMinHeap = new DijkstraUsingMinHeap();
        int k=0;
        for(int i:lakeat)
        {
            allDist[k++] = dijkstraUsingMinHeap.dijkstra_GetMinDistances(cityMap, i-1);
//            for(HeapNode j:allDist[k-1])
//            {
//                System.out.println((i-1)+"\t"+j.vertex+" \t"+j.distance);
//            }
        }
        
        Object clusters[]=new Object[lakeat.length];
        for(int i=0;i<lakeat.length;i++)
            clusters[i] = new LinkedList<HeapNode>();
        
//        List<Object> ls=new LinkedList<Object>();
        
//        HeapNode clusters[][]=new HeapNode[lakeat.length][ganeshat.length+1];
        int eachClusterSize[] = new int[lakeat.length];
//        synchronized(eachClusterSize) {
        for(int j = 0;j<noofvertices;j++){
            int minIsAt = 0;
            int minDistValue = Integer.MAX_VALUE;
            for(int i = 0;i < lakeat.length; i++){
                if(allDist[i][j].distance < minDistValue && cityMap.getFlag(j) == 1){
                    minIsAt = i;
                    minDistValue = allDist[i][j].distance;
                }
                if(cityMap.getFlag(j)==1){
                    System.out.println("Ganesha "+(j+1)+" distance with lake: "+lakeat[i]+" is at "+allDist[i][j].distance);
                }
            }
                if(cityMap.getFlag(j)==1){
                  List ls=(LinkedList)clusters[minIsAt];
                  ls.add(allDist[minIsAt][j]);
                  eachClusterSize[minIsAt]++;
                  System.out.println("\nGanesha "+(j+1)+" goes to lake: "+lakeat[minIsAt]+" with dist "+allDist[minIsAt][j].distance+"\n");
                  cityMap.setLakeId(allDist[minIsAt][j].vertex, lakeat[minIsAt]);
                }
//            }
        }
        
        boolean blockedVertices[] = new boolean[noofvertices];
        
//        int smallClusters[] = new int[lakeat.length];
        int min = Integer.MAX_VALUE;
        int minisat = Integer.MAX_VALUE;
        for(int i = 0;i<lakeat.length;i++){
            if(eachClusterSize[i]<min){
                min = eachClusterSize[i];
                minisat = i;
            }
        }
        int orginalmin = minisat;
        int max = Integer.MIN_VALUE;
        int maxisat = Integer.MIN_VALUE;
        for(int i = 0;i<lakeat.length;i++){
            if(eachClusterSize[i] >max){
//                System.out.println("hello");
                max = eachClusterSize[i];
                maxisat = i;
            }
        }
        
        
        System.out.println("Before Balancing: "+minisat);
        for(int i = 0;i<lakeat.length;i++){
//            for(int j=0;j<eachClusterSize[i];j++)
                System.out.println("Lake: "+lakeat[i]+" Contains: "+eachClusterSize[i]);
        }
        System.out.println("");
        
        for(int i = 0;i<lakeat.length;i++){
//            for(int j=0;j<eachClusterSize[i];j++)
//            System.out.println(""+eachClusterSize[i]);
            LinkedList <HeapNode> lpt = (LinkedList < HeapNode >)clusters[i];
//            while(lpt.getLast())
               HeapNode hn=lpt.getLast();
               int index=0;
               System.out.println("Cluster "+lakeat[i]+" contains");
              while(lpt.get(index)!=hn)
              {
//                  System.out.println("Here");
                cityMap.setLakeId(lpt.get(index).vertex, lakeat[i]);
                lpt.get(index).clusterid = i;
                if(minisat == i){
                    System.out.println("Block: "+(lpt.get(index).vertex+1));
                    blockedVertices[lpt.get(index).vertex] = true;
                }
                System.out.println("Ganesha "+(lpt.get(index).vertex+1)+" at distance "+lpt.get(index).distance);    
                    index++;
              }              
            lpt.getLast().clusterid = i;
            if(minisat == i){
                    System.out.println("Block: "+(lpt.get(index).vertex+1));
                    blockedVertices[lpt.get(index).vertex] = true;
            }
            cityMap.setLakeId(lpt.getLast().vertex, lakeat[i]);
            
            System.out.println("Ganesha "+(lpt.get(index).vertex+1)+" at distance "+lpt.get(index).distance+'\n');
            clusters[i] = lpt;
        }
        
        
        //TODO balancing
        
//        System.out.println("Min is at "+minisat+" val = "+eachClusterSize[minisat]);
        System.out.println("Balancing the  Clusters if needed");
        //Balancing
        int TotalLossIs = 0;
        
        while(max > 2*min){
            //TODO swap in these max and min
            
            int tempmin = Integer.MAX_VALUE;
            int tempminat = -1;
            int minorginclust = -1;
//            for(int j=0;j<lakeat.length;j++){
//                    if(j == minisat)
//            System.out.println("No of "+noofvertices);
                        for(int i=0;i<noofvertices;i++){
//                            System.out.println("Clust id "+allDist[minisat][i].clusterid);
//                            if(allDist[minisat][i].distance < tempmin && allDist[minisat][i].clusterid != lakeat[minisat] && allDist[minisat][i].clusterid > 0){
                            if(allDist[minisat][i].distance < tempmin && cityMap.getLakeId(i) != lakeat[minisat] &&
                                     blockedVertices[i] != true&& cityMap.getLakeId(i) > 0){
//                               System.out.println("Heap Val "+(allDist[minisat][i].vertex+1));                
                               tempmin = allDist[minisat][i].distance;
                               tempminat = i;
                               minorginclust = cityMap.getLakeId(i);
                        }
                    }
//            }
            if(tempminat == -1)
                break;
            
            if(minisat == orginalmin)
                blockedVertices[tempminat] = true;
            System.out.println("\nMove: "+(allDist[minisat][tempminat].vertex+1)+" from "+minorginclust+" to "+lakeat[minisat]);
            int minorginindex = -1;
            for(int i=0;i<lakeat.length;i++)
                if(minorginclust == lakeat[i]){
                    minorginindex = i;
                    break;
               }
            LinkedList li=(LinkedList)clusters[minorginindex];
            int indmin = -1;
            for(indmin = li.indexOf(li.getFirst());indmin <= li.indexOf(li.getLast());indmin++)
                if(((HeapNode)li.get(indmin)).vertex == allDist[minisat][tempminat].vertex)
                    break;
//            System.out.println("MInOrgin "+minorginindex+" ind min "+indmin);
            eachClusterSize[minorginindex]--;
            HeapNode tempnode = (HeapNode)li.get(indmin);
            li.remove(indmin);
            li = (LinkedList)clusters[minisat];
            li.add((HeapNode)tempnode);
            eachClusterSize[minisat]++;
            TotalLossIs += tempmin;
            
            cityMap.setLakeId(allDist[minisat][tempminat].vertex, lakeat[minisat]);
            
            min = Integer.MAX_VALUE;
            for(int ki = 0;ki<lakeat.length;ki++){
            if(eachClusterSize[ki]<min){
                min = eachClusterSize[ki];
                minisat = ki;
            }
        }
            
            int max2 = max;
            max = Integer.MIN_VALUE;
            for(int ki = 0;ki<lakeat.length;ki++){
                if(eachClusterSize[ki] >max){
    //                System.out.println("hello");
                    max = eachClusterSize[ki];
                    maxisat = ki;
                }
            }
            if(max2 == max){
                minisat = minorginindex;
                min = eachClusterSize[minisat];
            }
            
        System.out.println("In Balancing: ");
        for(int i = 0;i<lakeat.length;i++){
//            for(int j=0;j<eachClusterSize[i];j++)
                System.out.println("Lake: "+lakeat[i]+" Contains: "+eachClusterSize[i]);
        }
        System.out.println("");
        }
        
//        if(max != Integer.MIN_VALUE){
//            System.out.println("hello "+max+" "+min);
//            int tempmin =0;    
//            while(tempmin <= max/2){
//                System.out.println("hello" + tempmin);
//            
//                tempmin = Integer.MAX_VALUE;
//                int tempminat = -1;
//                LinkedList li=(LinkedList)clusters[maxisat];
//                int i=0;
//                for(i=0;i<eachClusterSize[maxisat];i++){
//                   if(allDist[minisat][i].distance<((HeapNode)li.get(i)).distance){
//                       tempmin = allDist[minisat][i].distance;
//                       tempminat = i;
//                   }
//                }
//                if(tempminat == -1)
//                    break;
//                //Swap the ganesha to other cluster
//                
//                eachClusterSize[maxisat]--;
//                HeapNode tempnode = (HeapNode)li.get(tempminat);
//                li.remove(tempminat);
//                li = (LinkedList)clusters[minisat];
//                li.add((HeapNode)tempnode);
//                eachClusterSize[minisat]++;
//                TotalLossIs += tempmin;     
//            }
//        }

        for(int i = 0;i<lakeat.length;i++){
//            for(int j=0;j<eachClusterSize[i];j++)
            System.out.println(""+eachClusterSize[i]);
            LinkedList <HeapNode> lpt = (LinkedList < HeapNode >)clusters[i];
//            while(lpt.getLast())
               HeapNode hn=lpt.getLast();
               int index=0;
               System.out.println("Cluster "+(i+1)+" contains");
              while(lpt.get(index)!=hn)
              {
                cityMap.setLakeId(lpt.get(index).vertex, lakeat[i]);
                lpt.get(index).clusterid = i;
                System.out.println("Ganesha "+(lpt.get(index).vertex+1)+" at distance "+lpt.get(index).distance);    
                    index++;
              }              
            lpt.getLast().clusterid = i;

            cityMap.setLakeId(lpt.getLast().vertex, lakeat[i]);
            
            System.out.println("Ganesha "+(lpt.get(index).vertex+1)+" at distance "+lpt.get(index).distance+'\n');
            clusters[i] = lpt;
        }
        System.out.println("Total Balancing loss is "+TotalLossIs);
        
        //Finding Route
        HeapNode distForAll[][] = new HeapNode[ganeshat.length+1][noofvertices+1];        
        Graph res[] = new Graph[lakeat.length];
        for(int lk=0;lk<lakeat.length;lk++){
            k=0;
            int src;
            int nowat = lakeat[lk]-1;
            res[lk] = new Graph(noofvertices);
            int orderofganeshavisit[] = new int[ganeshat.length+1];
            orderofganeshavisit[0] = nowat;
            System.out.println("Cluster Size: "+eachClusterSize[lk]);
            while(k<eachClusterSize[lk]){
                cityMap.setVisited(nowat);
                //on lake
    //            distForAll[k++] = dijkstraUsingMinHeap.dijkstra_GetMinDistances(cityMap, nowat);

        //            while(lpt.getLast())
                distForAll[k++] = dijkstraUsingMinHeap.dijkstra_GetMinDistances(cityMap, nowat);
                int minGanesha[] = findMinInDistForAll(cityMap,distForAll,k,lakeat[lk]);

                nowat = distForAll[minGanesha[0]][minGanesha[1]].vertex;
                orderofganeshavisit[k] = nowat;
                src = orderofganeshavisit[minGanesha[0]];
//                System.out.println("Src is "+src+" now at "+nowat);
    //            System.out.println("To add this "+distForAll[minGanesha[0]][minGanesha[1]].vertex);
                res[lk].addEdge(src, nowat, distForAll[minGanesha[0]][minGanesha[1]].distance);
                distForAll[minGanesha[0]][minGanesha[1]].distance = Integer.MAX_VALUE; 
        //        cityMap.setVisited( distForAll[minGanesha[0]][minGanesha[1]].vertex);
            }

            Graph.printGraph(res[lk]);
        }
    }

    private static int[] findMinInDistForAll(Graph cityMap, HeapNode[][] distForAll, int k, int lk) {
        int min[] = new int[2];
        int minDistValue = Integer.MAX_VALUE;
        
        for(int i = 0;i<k;i++){
            for(int j = 0;j<distForAll[k-1].length;j++){
                if(cityMap.getLakeId(distForAll[i][j].vertex)==lk && !cityMap.getVisited(distForAll[i][j].vertex))
//                    min[0]=i;
//                    min[1]=j;
                    if(distForAll[i][j].distance > 0 && distForAll[i][j].distance < minDistValue){
                        min[0] = i;
                        min[1] = j;
                        minDistValue = distForAll[i][j].distance;
                    }
            }
        }
        return min;
    }
    
}


// 1. A
// 2. B
// 3. C
// 4. D
// 5. D
