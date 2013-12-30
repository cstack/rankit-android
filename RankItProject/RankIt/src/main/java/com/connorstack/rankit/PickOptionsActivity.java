package com.connorstack.rankit;

public class PickOptionsActivity extends PickActivity {

    @Override
    protected int getMinimumNumberOfItems() {
        return 2;
    }

    @Override
    protected void saveProgress() {
        Ranking.getInstance().setOptions(mList);
    }

    @Override
    protected Class getNextActivity() {
        return PickFactorsActivity.class;
    }

    @Override
    protected String getPrompt() {
        return "What do you want to decide between?";
    }
}
