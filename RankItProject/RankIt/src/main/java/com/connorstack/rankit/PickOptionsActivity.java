package com.connorstack.rankit;

public class PickOptionsActivity extends PickActivity {
    @Override
    protected Class getNextActivity() {
        return PickFactorsActivity.class;
    }

    @Override
    protected String getPrompt() {
        return "What do you want to decide between?";
    }
}
