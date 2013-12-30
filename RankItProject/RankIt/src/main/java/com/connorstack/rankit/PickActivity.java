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

    protected ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView promptTextView = (TextView) findViewById(R.id.promptTextView);
        promptTextView.setText(getPrompt());

        final ListView optionListView = (ListView) findViewById(R.id.optionListView);

        mList = new ArrayList<String>();

        final ArrayAdapter<String> optionListAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);

        optionListView.setAdapter(optionListAdapter);

        final EditText optionEditText = (EditText) findViewById(R.id.optionEditText);
        final Button addOptionButton = (Button) findViewById(R.id.addOptionButton);
        final Button continueButton = (Button) findViewById(R.id.continueButton);

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newOption = optionEditText.getText().toString();
                if (!newOption.isEmpty()) {
                    optionEditText.getText().clear();
                    mList.add(0, newOption);
                    optionListAdapter.notifyDataSetChanged();
                    updateContinueButton(continueButton);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProgress();
                final Intent intent = new Intent(PickActivity.this, getNextActivity());
                startActivity(intent);
            }
        });

        updateContinueButton(continueButton);
    }

    private void updateContinueButton(Button continueButton) {
        final int numNeeded = getMinimumNumberOfItems();
        if (mList.size() >= numNeeded) {
            continueButton.setClickable(true);
            continueButton.setText("Continue");
        } else {
            continueButton.setClickable(false);
            final String formatString =
                    numNeeded == 1 ? "Add at least %d item" : "Add at least %d items";
            continueButton.setText(String.format(formatString, numNeeded));
        }
    }

    protected abstract int getMinimumNumberOfItems();

    protected abstract void saveProgress();

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
