package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class StatsActivity extends AppCompatActivity {

    private TextView tvStats;
    private ArrayList<Integer> ages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvStats = findViewById(R.id.tvStats);

        // Sample data (ages of users)
        ages = new ArrayList<>();
        ages.add(22);
        ages.add(30);
        ages.add(25);
        ages.add(35);
        ages.add(28);
        ages.add(40);
        ages.add(18);
        ages.add(33);

        // Display statistical calculations
        displayStatistics();
    }

    private void displayStatistics() {
        double average = calculateAverage(ages);
        double median = calculateMedian(ages);
        double stdDeviation = calculateStandardDeviation(ages);

        String stats = "Average Age: " + average + "\n" +
                "Median Age: " + median + "\n" +
                "Standard Deviation: " + stdDeviation + "\n\n" +
                "Data Aggregation:\n" +
                aggregateDataByAgeGroup() + "\n\n" +
                "Filtered Data (Age > 30):\n" +
                filterDataByAge(30);

        tvStats.setText(stats);
    }

    private double calculateAverage(ArrayList<Integer> data) {
        double sum = 0;
        for (int age : data) {
            sum += age;
        }
        return sum / data.size();
    }

    private double calculateMedian(ArrayList<Integer> data) {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            return (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }

    private double calculateStandardDeviation(ArrayList<Integer> data) {
        double mean = calculateAverage(data);
        double sum = 0;
        for (int age : data) {
            sum += Math.pow(age - mean, 2);
        }
        return Math.sqrt(sum / data.size());
    }

    private String aggregateDataByAgeGroup() {
        int young = 0, middleAged = 0, old = 0;

        // Group users by age
        for (int age : ages) {
            if (age <= 25) {
                young++;
            } else if (age <= 40) {
                middleAged++;
            } else {
                old++;
            }
        }

        return "Young (<=25): " + young + "\n" +
                "Middle-aged (26-40): " + middleAged + "\n" +
                "Old (>40): " + old;
    }

    private String filterDataByAge(int ageLimit) {
        ArrayList<Integer> filteredData = new ArrayList<>();
        for (int age : ages) {
            if (age > ageLimit) {
                filteredData.add(age);
            }
        }

        return filteredData.toString();
    }
}
