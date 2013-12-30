package com.connorstack.rankit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by connorstack on 12/30/13.
 */
public class Ranking {
    private static Ranking instance = null;

    private ArrayList<String> mOptions;
    private ArrayList<String> mFactors;
    private Map<String,Map<String, Double>> mScores;

    protected Ranking() {}

    public static Ranking getInstance() {
        if (instance == null) {
            instance = new Ranking();
        }
        return instance;
    }

    public void setOptions(ArrayList<String> options) {
        mOptions = options;
    }

    public void setFactors(ArrayList<String> factors) {
        mFactors = factors;
    }

    public void setScores(Map<String,Map<String,Double>> scores) {
        mScores = scores;
    }

    public ArrayList<String> getOptions() {
        return mOptions;
    }

    public ArrayList<String> getFactors() {
        return mFactors;
    }

    public Map<String, Double> calculateFinalScores() {
        Map<String, Double> finalScores = new HashMap<String, Double>();
        for (String option : mOptions) {
            double score = 0;
            for (String factor : mFactors) {
                score += mScores.get(option).get(factor);
            }
            score /= mFactors.size();
            finalScores.put(option, score);
        }
        return finalScores;
    }
}
