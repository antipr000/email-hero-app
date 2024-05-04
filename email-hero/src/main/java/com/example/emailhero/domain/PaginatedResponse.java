package com.example.emailhero.domain;

import java.util.List;

public class PaginatedResponse<T> {
    List<T> data;
    int nextPageOffset;
    int numRecords;

    public PaginatedResponse(List<T> data, int nextPageOffset, int numRecords) {
        this.data = data;
        this.nextPageOffset = nextPageOffset;
        this.numRecords = numRecords;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getNextPageOffset() {
        return nextPageOffset;
    }

    public void setNextPageOffset(int nextPageOffset) {
        this.nextPageOffset = nextPageOffset;
    }

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }
}
