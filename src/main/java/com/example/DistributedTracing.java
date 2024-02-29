package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.TracingService.*;

public class DistributedTracing {
    public static void main(String[] args) {
        try {
            List<Edge> edges = readEdgesFromFile("src/main/resources/input.txt");

            System.out.println("1. " + averageLatencyOfTrace(edges, "ABC"));
            System.out.println("2. " + averageLatencyOfTrace(edges, "AD"));
            System.out.println("3. " + averageLatencyOfTrace(edges, "ADC"));
            System.out.println("4. " + averageLatencyOfTrace(edges, "AEBCD"));
            System.out.println("5. " + averageLatencyOfTrace(edges, "AED"));
            System.out.println("6. " + numTracesWithMaxHops(edges, 'C', 'C', 3));
            System.out.println("7. " + numTracesWithExactHops(edges, 'A', 'C', 4));
            System.out.println("8. " + shortestTraceLatency(edges, 'A', 'C'));
            System.out.println("9. " + shortestTraceLatency(edges, 'B', 'B'));
            System.out.println("10. " + numTracesWithAvgLatencyLessThan(edges, 'C', 'C', 30));
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found.");
        }
    }

    public static List<Edge> readEdgesFromFile(String filePath) throws FileNotFoundException {
        List<Edge> edges = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNext()) {
            String[] line = scanner.next().split(",");
            char start = line[0].charAt(0);
            char end = line[0].charAt(1);
            int latency = Integer.parseInt(line[0].substring(2));
            edges.add(new Edge(start, end, latency));
        }
        scanner.close();
        return edges;
    }


}
