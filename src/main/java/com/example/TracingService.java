package com.example;

import java.util.*;

public class TracingService {
    public static String averageLatencyOfTrace(List<Edge> edges, String trace) {
        int totalLatency = 0;
        int hops = trace.length() - 1;
        for (int i = 0; i < hops; i++) {
            char start = trace.charAt(i);
            char end = trace.charAt(i + 1);
            Edge matchingEdge = findEdge(edges, start, end);
            if (matchingEdge == null) {
                return "NO SUCH TRACE";
            }
            totalLatency += matchingEdge.latency;
        }
        return String.valueOf(totalLatency);
    }

    static Edge findEdge(List<Edge> edges, char start, char end) {
        for (Edge edge : edges) {
            if (edge.start == start && edge.end == end) {
                return edge;
            }
        }
        return null;
    }

    public static int numTracesWithMaxHops(List<Edge> edges, char start, char end, int maxHops) {
        Set<Edge> visited = new HashSet<>();
        return dfs(edges, start, end, maxHops, 0, visited);
    }

    static int dfs(List<Edge> edges, char current, char end, int maxHops, int hops, Set<Edge> visited) {
        int count = 0;
        if (current == end && hops <= maxHops && !visited.isEmpty()) {
            count++;
            hops = 0;
        }
        if (hops >= maxHops) {
            return count;
        }
        for (Edge edge : edges) {
            if (edge.start == current && !visited.contains(edge)) {
                visited.add(edge);
                count += dfs(edges, edge.end, end, maxHops, hops + 1, visited);
            }
        }
        return count;
    }

    public static int numTracesWithExactHops(List<Edge> edges, char start, char end, int maxHops) {
        Set<Edge> visited = new HashSet<>();
        return dfsExact(edges, start, end, maxHops, 0, visited);
    }

    static int dfsExact(List<Edge> edges, char current, char end, int maxHops, int hops, Set<Edge> exactVisited) {
        int count = 0;
        char startEdge = '\0';
        char endEdge = '\0';
        if (hops == 0) {
            startEdge = current;
            endEdge = end;
        }
        if (current == end && hops == maxHops && !exactVisited.isEmpty()) {
            count++;
            hops = 0;
            current = startEdge;
            end = endEdge;
        }
        if (hops == maxHops) {
            return count;
        }
        for (Edge edge : edges) {
            if (edge.start == current && !exactVisited.contains(edge)) {
                if (edge.start == startEdge) {
                    exactVisited.add(edge);
                }
                count += dfsExact(edges, edge.end, end, maxHops, hops + 1, exactVisited);
            }
        }
        return count;
    }

    public static int shortestTraceLatency(List<Edge> edges, char start, char end) {
        Map<Character, Integer> distances = new HashMap<>();
        List<Integer> distanceList = new ArrayList<>();
        Queue<Character> queue = new LinkedList<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            char current = queue.poll();
            for (Edge edge : edges) {
                if (edge.start == current) {
                    char next = edge.end;
                    int newDistance = distances.get(current) + edge.latency;
                    if (next == end) {
                        distanceList.add(newDistance);
                    }
                    if (!distances.containsKey(next) || newDistance < distances.get(next)) {
                        distances.put(next, newDistance);
                        queue.add(next);
                    }
                }
            }
        }

        return Collections.min(distanceList);
    }


    public static int numTracesWithAvgLatencyLessThan(List<Edge> edges, char source, char destination, int threshold) {
        int count = 0;
        Stack<Character> stack = new Stack<>();
        Stack<Integer> sumStack = new Stack<>();
        Stack<List<Character>> traceStack = new Stack<>();

        stack.push(source);
        sumStack.push(0);
        traceStack.push(new ArrayList<>(List.of(source)));

        while (!stack.isEmpty()) {
            char current = stack.pop();
            int sum = sumStack.pop();
            List<Character> currentTrace = traceStack.pop();

            if (sum < threshold && current == destination && currentTrace.size() > 1) {
                count++;
            }

            for (Edge edge : edges) {
                if (edge.start == current) {
                    int newSum = sum + edge.latency;
                    if (newSum < threshold) {
                        List<Character> newTrace = new ArrayList<>(currentTrace);
                        newTrace.add(edge.end);
                        stack.push(edge.end);
                        sumStack.push(newSum);
                        traceStack.push(newTrace);
                    }
                }
            }
        }

        return count;
    }
}
