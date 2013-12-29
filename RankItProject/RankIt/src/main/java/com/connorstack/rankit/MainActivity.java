package com.connorstack.rankit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView optionListView = (ListView) findViewById(R.id.optionListView);

        final ArrayList<String> optionList = new ArrayList<String>();

        final ArrayAdapter<String> optionListAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionList);

        optionListView.setAdapter(optionListAdapter);

        final EditText optionEditText = (EditText) findViewById(R.id.optionEditText);
        final Button addOptionButton = (Button) findViewById(R.id.addOptionButton);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newOption = optionEditText.getText().toString();
                if (!newOption.isEmpty()) {
                    optionEditText.getText().clear();
                    optionList.add(0, newOption);
                    optionListAdapter.notifyDataSetChanged();
                }
            }
        });
    }


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

}
