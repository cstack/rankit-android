package com.connorstack.rankit;

import android.os.Bundle;

public class PickOptionsActivity extends PickActivity {

    @Override
    protected void saveProgress(Bundle progress) {
        progress.putStringArrayList(EXTRA_OPTIONS, mList);
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
