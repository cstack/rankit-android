package com.connorstack.rankit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RankActivity extends ActionBarActivity {

    private static final Double STEAL_PERCENTAGE = 0.2;
    Map<String, Map<String, Double>> mScores;
    private Button mOption1Button;
    private Button mOption2Button;
    private TextView mFactorTextView;
    private QuestionGenerator mQuestionGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        mOption1Button = (Button) findViewById(R.id.option1button);
        mOption2Button = (Button) findViewById(R.id.option2button);
        mFactorTextView = (TextView) findViewById(R.id.factorTextView);

        mScores = new HashMap<String, Map<String, Double>>();
        for (String option : Ranking.getInstance().getOptions()) {
            final Map<String, Double> scoreMap = new HashMap<String, Double>();
            for (String factor : Ranking.getInstance().getFactors()) {
                scoreMap.put(factor, 100.0);
            }
            mScores.put(option, scoreMap);
        }

        mOption1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQuestionAnswered(true);
            }
        });
        mOption2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQuestionAnswered(false);
            }
        });

        findViewById(R.id.resultsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ranking.getInstance().setScores(mScores);
                final Intent intent = new Intent(RankActivity.this, ResultsActivity.class);
                startActivity(intent);
            }
        });

        mQuestionGenerator =
                new QuestionGenerator(Ranking.getInstance().getOptions(), Ranking.getInstance().getFactors());
        displayQuestion(mQuestionGenerator.getNext());
    }

    private void onQuestionAnswered(boolean pickedFirst) {
        // Update scores
        final Question question = mQuestionGenerator.getCurrent();
        final String factor = question.mFactor;
        final String winner = pickedFirst ? question.mOption1 : question.mOption2;
        final String loser = pickedFirst ? question.mOption2 : question.mOption1;
        final Map<String, Double> winnerScores = mScores.get(winner);
        final Map<String, Double> loserScores = mScores.get(loser);
        final Double winnings = loserScores.get(factor) * STEAL_PERCENTAGE;
        winnerScores.put(factor, winnerScores.get(factor)+winnings);
        loserScores.put(factor, loserScores.get(factor)-winnings);

        // Move to next question
        displayQuestion(mQuestionGenerator.getNext());
    }

    private void displayQuestion(Question question) {
        mOption1Button.setText(question.mOption1);
        mOption2Button.setText(question.mOption2);
        mFactorTextView.setText(question.mFactor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Question {
        String mOption1;
        String mOption2;
        String mFactor;

        public Question(String option1, String option2, String factor) {
            mOption1 = option1;
            mOption2 = option2;
            mFactor = factor;
        }
    }

    private class QuestionGenerator {
        private ArrayList<String> mOptions;
        private ArrayList<String> mFactors;
        private Question mCurrent;

        public QuestionGenerator(ArrayList<String> options, ArrayList<String> factors) {
            mOptions = options;
            mFactors = factors;
        }

        public Question getNext() {
            Collections.shuffle(mOptions);
            Collections.shuffle(mFactors);
            mCurrent = new Question(mOptions.get(0), mOptions.get(1), mFactors.get(0));
            return mCurrent;
        }

        public Question getCurrent() {
            return mCurrent;
        }
    }
}
