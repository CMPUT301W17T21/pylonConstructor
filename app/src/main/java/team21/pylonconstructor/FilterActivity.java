package team21.pylonconstructor;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//TODO: implement
public class FilterActivity extends AppCompatActivity {
    Button happyButton;
    Button sadButton;
    Button angryButton;
    Button confusedButton;
    Button disgustedButton;
    Button scaredButton;
    Button surpriseButton;
    Button shamefulButton;
    Button filter;
    Button cancel;
    Toast toast;
    Context context;
    private boolean searchPastWeek = false;

    /**
     * Dummy mood to catch exceptions of no mood chosen and
     * searh term too long
     */
    Mood mood = new Mood();

    private EditText triggerEditText;
    private CheckBox pastWeekCheckBox;

    private String feeling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        /* Set Custom App bar title, centered */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mood_feed_layout);


        filter = (Button) findViewById(R.id.filter);
        cancel = (Button) findViewById(R.id.cancel);
        triggerEditText = (EditText) findViewById(R.id.message);


        happyButton = (Button) findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.happy_button_label);
                mood.setEmoji(getString(R.string.happy_button_label));

            }

        });

        sadButton = (Button) findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.sad_button_label);
                mood.setEmoji(getString(R.string.sad_button_label));

            }
        });

        angryButton = (Button) findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.angry_button_label);
                mood.setEmoji(getString(R.string.angry_button_label));

            }
        });

        confusedButton = (Button) findViewById(R.id.confused_button);
        confusedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.confused_button_label);
                mood.setEmoji(getString(R.string.confused_button_label));


            }
        });

        disgustedButton = (Button) findViewById(R.id.disgusted_button);
        disgustedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.disgusted_button_label);
                mood.setEmoji(getString(R.string.disgusted_button_label));



            }
        });

        scaredButton = (Button) findViewById(R.id.scared_button);
        scaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.scared_button_label);
                mood.setEmoji(getString(R.string.scared_button_label));


            }
        });

        surpriseButton = (Button) findViewById(R.id.surprised_button);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.surprised_button_label);
                mood.setEmoji(getString(R.string.surprised_button_label));

            }
        });

        shamefulButton = (Button) findViewById(R.id.shameful_button);
        shamefulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.shameful_button_label);
                mood.setEmoji(getString(R.string.shameful_button_label));

            }
        });

        pastWeekCheckBox = (CheckBox) findViewById(R.id.checkBox3);
        pastWeekCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchPastWeek = true;
                } else {
                    searchPastWeek = false;
                }
            }
        });




        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filter = (Button) findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String triggerStr = triggerEditText.getText().toString();
                boolean validMood = true;

                if (mood.getEmoji() == null) {
                    Log.d("STATE", "NULL");
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "No mood selected. Please select a mood";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


                try {
                    mood.setTrigger(triggerStr);
                } catch (ReasonTooLongException e) {
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "Trigger message is too long..";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if (mood.getTrigger() == null) {
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "Please enter a filter term";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


                if (validMood) {
                    //TODO: JOSH, at this point filter settings are valid, so filter feed FILTERBY()
                    // using searchPastWeek, emotion, and
                    String emotion = mood.getEmoji();

                    boolean useSearchTerm = true;
                    String searchTerm = mood.getTrigger();
                    if (searchTerm.length() == 0) {
                        useSearchTerm = false;
                    }

                    //Log.d("SEARCHTERM IS", searchTerm);
                    //Log.d("EMOTION IS", emotion);
                    //Log.d("BOOL IS", Boolean.toString(searchPastWeek));
                    finish();

                }


            }

        });
    }

};


