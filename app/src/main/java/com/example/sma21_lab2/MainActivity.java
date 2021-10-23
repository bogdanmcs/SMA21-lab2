package com.example.sma21_lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bClick = findViewById(R.id.bClick);
        TextView tName = findViewById(R.id.tName);
        EditText eName = findViewById(R.id.eName);

        bClick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                String inputText = eName.getText().toString();

                // if no name -> do nothing
                if(inputText.isEmpty())
                {
                    CharSequence text = "Please enter your name";
                    Toast.makeText(context, text, duration).show();
                    tName.setText("");
                }
                else // pop-up
                {
                    String message = "Hello, " + eName.getText();
                    tName.setText(message);

                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage(message)
                            .setPositiveButton("Hi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    CharSequence text = "Hi!";
                                    Toast.makeText(context, text, duration)
                                            .show();
                                }
                            })
                            .setNegativeButton("Bye", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    CharSequence text = "Rude";
                                    Toast.makeText(context, text, duration)
                                            .show();
                                }
                            })
                            .show();

                }
            }
        });

        // spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_choices, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // change button text color based on spinner item choice
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Context context = getApplicationContext();

                switch(spinner.getSelectedItem().toString())
                {
                    case "Red":
                        bClick.setTextColor(ContextCompat.getColor(context, R.color.red));
                        break;
                    case "Green":
                        bClick.setTextColor(ContextCompat.getColor(context, R.color.green));
                        break;
                    case "Blue":
                        bClick.setTextColor(ContextCompat.getColor(context, R.color.blue));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });

        // bShare & bSearch
        FloatingActionButton bShare = findViewById(R.id.bShare);
        FloatingActionButton bSearch = findViewById(R.id.bSearch);

        bShare.setOnClickListener( view -> {
            String inputName = eName.getText().toString();

            if(inputName.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        "Please enter name before sharing",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                // add user input text to extra
                intent.putExtra(Intent.EXTRA_TEXT, inputName);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        bSearch.setOnClickListener( view -> {
            String inputName = eName.getText().toString();

            if(inputName.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        "Please enter name before searching",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else // search google.com based on name input
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + inputName));
                startActivity(intent);
            }
        });

    }
}