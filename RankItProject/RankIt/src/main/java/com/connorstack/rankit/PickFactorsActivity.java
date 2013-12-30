package com.connorstack.rankit;

import android.os.Bundle;

/**
 * Created by connorstack on 12/29/13.
 */
public class PickFactorsActivity extends PickActivity {

    @Override
    protected void saveProgress(Bundle progress) {
        progress.putStringArrayList(EXTRA_FACTORS, mList);
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
