package com.connorstack.rankit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ResultsActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final Map<String, Double> finalScores = Ranking.getInstance().calculateFinalScores();
        final ValueComparator comparator =  new ValueComparator(finalScores);
        final Map<String,Double> sortedScores = new TreeMap<String,Double>(comparator);
        sortedScores.putAll(finalScores);

        String winner = null;
        ArrayList<String> runnersUp = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : sortedScores.entrySet()) {
            if (winner == null) {
                winner = entry.getKey() + " ("+Double.toString(entry.getValue())+")";
            } else {
                runnersUp.add(entry.getKey()+" ("+Double.toString(entry.getValue())+")");
            }
        }

        final TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        winnerTextView.setText(winner);

        final ArrayAdapter<String> runnersUpAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, runnersUp);
        final ListView runnersUpListView = (ListView) findViewById(R.id.runnersUpListView);
        runnersUpListView.setAdapter(runnersUpAdapter);

    }

    private class ValueComparator implements Comparator<String> {
        Map<String, Double> base;
        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
