package com.connorstack.rankit;

/**
 * Created by connorstack on 12/29/13.
 */
public class PickFactorsActivity extends PickActivity {
    @Override
    protected Class getNextActivity() {
        return RankActivity.class;
    }

    @Override
    protected String getPrompt() {
        return "Based on what factors?";
    }
}
