/* CMSC401
 * Professor: Dr.Bulut
 * Student: Pedram Maleki
 * Assignment 3
 *
 * This file will find the shortest path between nodes 1 and 2 in the graph*/


import java.util.Scanner;
import java.util.LinkedList;

public class cmsc401{
	//Creating an edge class that has a city and a motel cost
    static class Edge{
        int city;
        int motelCost;

        public Edge(int city)
        {
            this.city = city;

        }

        public Edge(int inCityNum, int inMotelCost){
            this.city = inCityNum;
            this.motelCost = inMotelCost;

        }

    }


    public static void main(String[] args){
		
        int numCity;
        int directHwy;
		//scanner object to read the input
        Scanner in = new Scanner(System.in);

        numCity = in.nextInt();
        directHwy = in.nextInt();
		//using two array to store the motel price and city number...they will start
		//at one since the graph does not have a 0 node
        int[] motelPrice = new int[numCity+1];
        int[] cityNumber = new int[numCity+1];
		//givens
        motelPrice[0] =0;
        motelPrice[1] =0;
        motelPrice[2] =0;
        cityNumber[0] =0;
        cityNumber[1] =1;
        cityNumber[2] =2;
        /*here************************************************************************/
		//first edge 0 is empty
        Edge empty = new Edge(0,0);
		//two lists to keep track of the nodes processsed
        LinkedList<Edge> doneProcessing = new LinkedList<Edge>();
        LinkedList<Edge> needToProcess = new LinkedList<Edge>();

        //node 0 is empty
        needToProcess.add(empty);
        doneProcessing.add(empty);

        // storing city and motel costs
        for (int i=3; i<=numCity; i++){
            cityNumber[i] = in.nextInt();
            motelPrice[i] = in.nextInt();
        }

        // adding cities
        for (int i = 1; i <= numCity; i++){
            Edge temp = new Edge(cityNumber[i], motelPrice[i]);
            needToProcess.add(temp);
        }
		//this will store gas price for the cities
        int[][] gasCity = new int[numCity+1][numCity+1];
        for (int i=0;i<gasCity.length;i++){
            for(int j=0;j<gasCity.length;j++){
                gasCity[i][j]=0;
            }
        }
		
        int source;
        int dest;
        int gasCost;
		//using a for loop to read the edges and gas for each city
		//also adding the opposite since the gas is the same
        for (int j = 1; j <= directHwy; j++)      
        {
            source = in.nextInt();
            dest = in.nextInt();
            gasCost = in.nextInt();
            gasCity[dest][source]=gasCost;
            gasCity[source][dest]=gasCost;

        }
		//this array will store the cost of traveling
        int[][] travelCost = new int[numCity+1][numCity+1];
        for (int i = 1; i <=numCity; i++){
            for (int j = 1; j <=numCity; j++){
				//adding gas and motel price
                travelCost[i][j] = gasCity[i][j] + motelPrice[j];
            }
        }
        /*here************************************************************************/
		//this will store the shortest path to source
        int[] shortestFromSource = new int[numCity+1];
        for(int i=0;i<shortestFromSource.length;i++){
            shortestFromSource[i]=Integer.MAX_VALUE;
        }
		//0 is empty and 1 is the source and they are 0
        shortestFromSource[0]=0;
        shortestFromSource[1]=0;
        int curCity = 1;
		//keep track of finalized nodes in a bool array
        boolean[] blackNode = new boolean[numCity+1];
        for(int i=0; i<blackNode.length;i++){
            blackNode[i]=false;
        }

        blackNode[0] = true;
        int adjWhiteNde = Integer.MAX_VALUE;
		//using a while loop to start the processing
        while(doneProcessing.size() <= numCity){
            //iterating through the cities
            for(int i = 1; i <= numCity; i++){
                if(!blackNode[i]){
                    // check if distance is less
                    if(adjWhiteNde > shortestFromSource[i]){
                        curCity = i;
                        adjWhiteNde = shortestFromSource[i];
                    }
                }
            }
            adjWhiteNde = Integer.MAX_VALUE;
            doneProcessing.add(needToProcess.get(curCity));
            // looking at the adjacent nodes
            for(int f = 1; f <=numCity; f++){
                if(gasCity[curCity][f] > 0){
                    //relaxation
                    if(shortestFromSource[curCity] + travelCost[curCity][f] < shortestFromSource[f]){
                        shortestFromSource[f] = travelCost[curCity][f] + shortestFromSource[curCity];
                    }
                }
            }
			//finish the node, turn it black
            blackNode[curCity] = true;
        }
		//storing the result and print
        int shortestPath=shortestFromSource[2];
        System.out.println(shortestPath);
    }

}