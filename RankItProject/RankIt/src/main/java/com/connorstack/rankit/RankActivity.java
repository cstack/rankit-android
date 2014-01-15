package com.connorstack.rankit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RankActivity extends MenuActivity {

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


        /* Design stuff */
        mOption1Button = (Button) findViewById(R.id.option1button);
        mOption2Button = (Button) findViewById(R.id.option2button);
        mFactorTextView = (TextView) findViewById(R.id.factorTextView);
        final TextView viewResults = (TextView) findViewById(R.id.resultsButton);
        final TextView promptTextView = (TextView) findViewById(R.id.promptTextView);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        mOption1Button.setTypeface(tf);
        mOption2Button.setTypeface(tf);
        mFactorTextView.setTypeface(tf);
        viewResults.setTypeface(tf);
        promptTextView.setTypeface(tf);


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
        if (question == null) {
            Ranking.getInstance().setScores(mScores);
            final Intent intent = new Intent(RankActivity.this, ResultsActivity.class);
            startActivity(intent);
            return;
        }
        mOption1Button.setText(question.mOption1);
        mOption2Button.setText(question.mOption2);
        mFactorTextView.setText(question.mFactor);
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
        private final ArrayList<Question> mQuestions;
        private Question mCurrent;

        public QuestionGenerator(ArrayList<String> options, ArrayList<String> factors) {
            // Randomize order of questions
            final int numOptions = options.size();
            final int numFactors = factors.size();
            mQuestions = new ArrayList<Question>();
            for (int i = 0; i < numOptions; i++) {
                for (int j = i+1; j < numOptions; j++) {
                    for (int k = 0; k < numFactors; k++) {
                        mQuestions.add(new Question(options.get(i), options.get(j), factors.get(k)));
                    }
                }
            }
            Collections.shuffle(mQuestions);
        }

        public Question getNext() {
            if (mQuestions.size() == 0) {
                mCurrent = null;
            } else {
                mCurrent = mQuestions.get(0);
                mQuestions.remove(mCurrent);
            }
            return mCurrent;
        }

        public Question getCurrent() {
            return mCurrent;
        }
    }
}
