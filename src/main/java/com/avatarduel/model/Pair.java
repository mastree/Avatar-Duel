package com.avatarduel.model;

public class Pair{
    public int first, second;

    public Pair(){
        this.first = 0;
        this.second = 0;
    }

    public Pair(int a, int b){
        this.first = a;
        this.second = b;
    }

    public boolean equals(int a, int b){
        return (this.first == a && this.second == b);
    }

    public boolean equals(Pair other){
        return (this.first == other.first && this.second == other.second);
    }
}
