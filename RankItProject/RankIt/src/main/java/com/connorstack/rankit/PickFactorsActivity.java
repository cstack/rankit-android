package com.connorstack.rankit;

/**
 * Created by connorstack on 12/29/13.
 */
public class PickFactorsActivity extends PickActivity {

    @Override
    protected void saveProgress() {
        Ranking.getInstance().setFactors(mList);
    }

    @Override
    protected Class getNextActivity() {
        return RankActivity.class;
    }

    @Override
    protected String getPrompt() {
        return "Based on what factors?";
    }
}
