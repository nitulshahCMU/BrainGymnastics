package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// A Java program to implement greedy algorithm for graph coloring
import java.io.*;
import java.util.*;
import java.util.LinkedList;

// This class represents an undirected graph using adjacency list
class Graph
{
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency List

    //Constructor
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    //Function to add an edge into the graph
    void addEdge(int v,int w)
    {
        adj[v].add(w);
        adj[w].add(v); //Graph is undirected
    }

    // Assigns colors (starting from 0) to all vertices and
    // prints the assignment of colors
    int greedyColoring()
    {
        int result[] = new int[V];
        String resultstring = "";
        int numberOfColors = 0;
        // Initialize all vertices as unassigned
        Arrays.fill(result, -1);

        // Assign the first color to first vertex
        result[0] = 0;
        resultstring="0";
        numberOfColors++;
        // A temporary array to store the available colors. False
        // value of available[cr] would mean that the color cr is
        // assigned to one of its adjacent vertices
        boolean available[] = new boolean[V];

        // Initially, all colors are available
        Arrays.fill(available, true);

        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < V; u++)
        {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            //check if adj[u-1] exists
            if((u-1)>=0) {
                Iterator<Integer> prev = adj[u - 1].iterator();
                while (prev.hasNext()) {
                    //check size of 'it', ensure no color is repeated in its members
                    int i = prev.next();
                    if (result[i] != -1)//vertex is assigned a color, make its color available as false
                        available[result[i]] = false;
                }
            }

            Iterator<Integer> curr = adj[u].iterator() ;
            while (curr.hasNext()) {
                //check size of 'it', ensure no color is repeated in its members
                int i = curr.next();
                if (result[i] != -1)//vertex is assigned a color, make its color available as false
                    available[result[i]] = false;
            }
            if((u+1)<V) {//check if adj[u+1] exists
                Iterator<Integer> next = adj[u + 1].iterator();
                while (next.hasNext()) {
                    //check size of 'it', ensure no color is repeated in its members
                    int i = next.next();
                    if (result[i] != -1)//vertex is assigned a color, make its color available as false
                        available[result[i]] = false;
                }
            }
            // Find the first available color
            Integer cr;
            for (cr = 0; cr < V; cr++){
                if (available[cr])
                    break;
            }

            if (!resultstring.contains(cr.toString()))
            {
                resultstring+=cr.toString();
                numberOfColors++;
            }

            result[u] = cr; // Assign the found color


            // Reset the values back to true for the next iteration
            Arrays.fill(available, true);
        }

        // print the result
        for (int u = 0; u < V; u++)
            System.out.println("Field " + u + " ---> Grass "
                    + result[u]);

        return numberOfColors;
    }
    private static void writeUsingFileWriter(Integer data) {
        File file = new File("/Users/nitulshah/Documents/MSSM Sem 2/IntroAI/planting.out.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Driver method
    public static void main(String args[])
    {
        long startTime = System.currentTimeMillis();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/nitulshah/Documents/MSSM Sem 2/IntroAI/Untitled.in.txt"));
            String line = br.readLine();
            Integer numberOfFields = new Integer(line);
            line = br.readLine();

            Graph g1 = new Graph(numberOfFields);
            while (line != null) {
                String [] path = line.split(" ", 2);
                g1.addEdge(new Integer(path[0])-1, new Integer(path[1])-1);
                line = br.readLine();
            }
            System.out.println("Grass selection for fields");
            int numberOfColors = g1.greedyColoring();

            System.out.println("\n"+numberOfColors+"\n");
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time take to run the program "+ totalTime + " miliiseconds");
            writeUsingFileWriter(numberOfColors);
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}