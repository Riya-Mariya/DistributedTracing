package com.example;

public class Edge {
    char start;
    char end;
    int latency;

    public Edge(char start, char end, int latency) {
        this.start = start;
        this.end = end;
        this.latency = latency;
    }
}

