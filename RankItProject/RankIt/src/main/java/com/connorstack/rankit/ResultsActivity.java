package com.connorstack.rankit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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


        /* Design stuff */
        final TextView headerTextView = (TextView) findViewById(R.id.textView);
        final TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final Button newRankingButton = (Button) findViewById(R.id.newRankingButton);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        headerTextView.setTypeface(tf);
        winnerTextView.setTypeface(tf);
        textView3.setTypeface(tf);
        newRankingButton.setTypeface(tf);


        String winner = null;
        ArrayList<String> runnersUp = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : sortedScores.entrySet()) {
            if (winner == null) {
                winner = entry.getKey(); //+ " ("+Double.toString(entry.getValue())+")";
            } else {
                runnersUp.add(entry.getKey()); //+" ("+Double.toString(entry.getValue())+")");
            }
        }

        winnerTextView.setText(winner);

        final ArrayAdapter<String> runnersUpAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, runnersUp);
        final ListView runnersUpListView = (ListView) findViewById(R.id.runnersUpListView);
        runnersUpListView.setAdapter(runnersUpAdapter);

        findViewById(R.id.newRankingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(ResultsActivity.this, PickOptionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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
