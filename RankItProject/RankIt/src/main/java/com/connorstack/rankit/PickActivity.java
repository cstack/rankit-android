package com.connorstack.rankit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by connorstack on 12/29/13.
 */
public abstract class PickActivity extends ActionBarActivity {
    protected static final String EXTRA_OPTIONS = "options";
    protected static final String EXTRA_FACTORS = "factors";
    public static final String EXTRA_PROGRESS = "progress";

    protected ArrayList<String> mList;
    private Bundle mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PROGRESS)) {
            mProgress = intent.getBundleExtra(EXTRA_PROGRESS);
        } else {
            mProgress = new Bundle();
        }

        final TextView promptTextView = (TextView) findViewById(R.id.promptTextView);
        promptTextView.setText(getPrompt());

        final ListView optionListView = (ListView) findViewById(R.id.optionListView);

        mList = new ArrayList<String>();

        final ArrayAdapter<String> optionListAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);

        optionListView.setAdapter(optionListAdapter);

        final EditText optionEditText = (EditText) findViewById(R.id.optionEditText);
        final Button addOptionButton = (Button) findViewById(R.id.addOptionButton);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newOption = optionEditText.getText().toString();
                if (!newOption.isEmpty()) {
                    optionEditText.getText().clear();
                    mList.add(0, newOption);
                    optionListAdapter.notifyDataSetChanged();
                }
            }
        });

        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(PickActivity.this, getNextActivity());
                saveProgress(mProgress);
                intent.putExtra(EXTRA_PROGRESS, mProgress);
                startActivity(intent);
            }
        });
    }

    protected abstract void saveProgress(Bundle progress);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    protected abstract Class getNextActivity();

    protected abstract String getPrompt();
}
