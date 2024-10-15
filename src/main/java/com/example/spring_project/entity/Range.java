package com.example.spring_project.entity;

public class Range{
    private final Long first;
    private final Long second;

    public Range(Long first, Long second) {
        this.first = first;
        this.second = second;
    }

    public Long getFirst() {
        return first;
    }

    public Long getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Result:{" + "StartIndex=" + first + ", EndIndex=" + second + '}';
    }
}
