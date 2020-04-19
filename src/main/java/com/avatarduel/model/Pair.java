package com.avatarduel.model;

/**
 * creates a pair 
 */
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

    /**
     * @param a 
     * @param b
     * @return true if the pair consist of a and b 
     */
    public boolean equals(int a, int b){
        return (this.first == a && this.second == b);
    }

    /**
     * @param other 
     * @return true if the pair equals to other 
     */
    public boolean equals(Pair other){
        return (this.first == other.first && this.second == other.second);
    }
}
