package com.PowerLog.Common.Object;

public class MeasureResult {
    int sum = 0, count = 0, max = 0, min = 0, average = 0, start = -1;

    public void putNumber(int entry) {
        count++;
        if(start == -1)
            start = min = average;
        sum += entry;
        average = sum / count;
        if (entry > max)
            max = entry;
        if (entry < min)
            min = entry;
    }

    public void putBase(int entry) {
        count++;
        sum += entry;
        average = sum / count;
        if (entry > max)
            max = entry;
    }

    public int getStart() {
        return start;
    }

    public int getAverage() {
        return average;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

}