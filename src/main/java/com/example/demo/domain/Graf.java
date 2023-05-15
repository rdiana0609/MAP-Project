package com.example.demo.domain;
import java.util.*;
public class Graf {
    protected Map<String,HashSet<String>> adjMap;
    protected Map<String,Boolean> visited= new HashMap<>();

    public Graf(Map<String, HashSet<String>> adjMap) {
        this.adjMap=adjMap;
        for(String uid :adjMap.keySet()){
            for(String fid:adjMap.get(uid)) {
               visited.put(uid,false);
               visited.put(fid,false);
            }//parcurgem prietenii,nodurile din matrice nu sunt vizitate init 0
        }
    }

private void resetVisited(){
    visited.replaceAll((k,v)->false);//reinit mat
}
private int visitNextVertex(String vertex){
        int maxPathLength=0;
        visited.put(vertex,true);//vizit un nod
        for(String adjVertex:adjMap.get(vertex)){
            if(!visited.get(adjVertex)){
                int pathLength=visitNextVertex(adjVertex);//face dfs
                maxPathLength=Math.max(maxPathLength,pathLength);//calc nr noduri
            }
        }
        return maxPathLength+1;
}//DFS
public int getConnectedComponentsCount(){
        int count=0;
        resetVisited();//reinit mat
        for(String vertex:visited.keySet()){
            if(!visited.get(vertex)){visitNextVertex(vertex);count++;}
        }
        return count;
}
    private void makeSubgraph(String vertex, Collection<String> connectedComponent) {
        connectedComponent.add(vertex);
        visited.put(vertex, true);
        for (String adjVertex : adjMap.get(vertex)) {
            if (!visited.get(adjVertex)) {
                makeSubgraph(adjVertex, connectedComponent);
            }//subgraful comp conexe==o comunitate
        }
    }

    public Iterable<String> getConnectedComponentWithLongestRoad() {
        resetVisited();
        int maxPathLength = 0;
        String startVertex = "-1";
        for (String vertex : visited.keySet()) {
            if (!visited.get(vertex)) {
                int pathLength = visitNextVertex(vertex);
                if (maxPathLength < pathLength) {
                    maxPathLength = pathLength;
                    startVertex = vertex;
                }
            }
        }
        Collection<String> connectedComponent = new ArrayList<>();
        resetVisited();
        makeSubgraph(startVertex, connectedComponent);
        return connectedComponent;
    }
}