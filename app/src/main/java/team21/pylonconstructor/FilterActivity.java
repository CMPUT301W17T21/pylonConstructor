package team21.pylonconstructor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


//TODO: implement

/**
 * This class will allow the user to filter moods in their feed by certain parameters.
 *
 * @see Mood
 *
 * @version 1.0
 */
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
    Profile profile;

    String filterEmotion;
    String filterTrigger;
    Date filterDate;

    ElasticSearch elasticSearch;

    private int filterChoice = 0;

    private RadioGroup radioGroup;
    private RadioButton byMood;
    private RadioButton byTrigger;
    private RadioButton byPastWeek;
    private RadioButton rb;


    private boolean searchPastWeek = false;

    /**
     * Dummy mood to catch exceptions of no mood chosen and
     * search term too long
     */
    Mood mood = new Mood();

    private EditText triggerEditText;
    private TextView selectedMood;

    /***
     * FACTORING
     * can be made local variable since not shared.
     */
    private CheckBox pastWeekCheckBox;

    private String feeling;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        /* Set Custom App bar title, centered */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mood_feed_appbar_title_layout);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        byMood = (RadioButton) findViewById(R.id.filter_mood_radio_button);
        byTrigger = (RadioButton) findViewById(R.id.filter_trigger_radio_button);
        byPastWeek = (RadioButton) findViewById(R.id.filter_week_radio_button);

        selectedMood = (TextView) findViewById(R.id.selected_mood);
        filter = (Button) findViewById(R.id.filter);
        cancel = (Button) findViewById(R.id.cancel);
        triggerEditText = (EditText) findViewById(R.id.message);

        happyButton = (Button) findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.happy_button_label);
                mood.setEmoji(getString(R.string.happy_button_label));
                selectedMood.setText(feeling);

            }

        });

        sadButton = (Button) findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.sad_button_label);
                mood.setEmoji(getString(R.string.sad_button_label));
                selectedMood.setText(feeling);

            }
        });

        angryButton = (Button) findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.angry_button_label);
                mood.setEmoji(getString(R.string.angry_button_label));
                selectedMood.setText(feeling);

            }
        });

        confusedButton = (Button) findViewById(R.id.confused_button);
        confusedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.confused_button_label);
                mood.setEmoji(getString(R.string.confused_button_label));
                selectedMood.setText(feeling);


            }
        });

        disgustedButton = (Button) findViewById(R.id.disgusted_button);
        disgustedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.disgusted_button_label);
                mood.setEmoji(getString(R.string.disgusted_button_label));
                selectedMood.setText(feeling);



            }
        });

        scaredButton = (Button) findViewById(R.id.scared_button);
        scaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.scared_button_label);
                mood.setEmoji(getString(R.string.scared_button_label));
                selectedMood.setText(feeling);


            }
        });

        surpriseButton = (Button) findViewById(R.id.surprised_button);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.surprised_button_label);
                mood.setEmoji(getString(R.string.surprised_button_label));
                selectedMood.setText(feeling);

            }
        });

        shamefulButton = (Button) findViewById(R.id.shameful_button);
        shamefulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = getString(R.string.shameful_button_label);
                mood.setEmoji(getString(R.string.shameful_button_label));

                selectedMood.setText(feeling);

            }
        });


        //TODO: IMPLEMENT DATE FILTER HERE
        byPastWeek = (RadioButton) findViewById(R.id.filter_week_radio_button);
        byPastWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Removed the if statement that is not needed.
                searchPastWeek = isChecked;
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
                boolean validFilter = true;


                if (filterChoice == 0) {
                    validFilter = false;
                    context = getApplicationContext();
                    CharSequence text = "No filter choice selected. Please select a choice";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if (filterChoice == 1) {
                    if (mood.getEmoji() == null) {
                        Log.d("STATE", "NULL");
                        validFilter = false;
                        context = getApplicationContext();
                        CharSequence text = "No mood selected. Please select a mood";
                        int duration = Toast.LENGTH_SHORT;
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

                if (filterChoice == 2) {

                    try {
                        mood.setTrigger(triggerStr);
                    } catch (ReasonTooLongException e) {
                        validFilter = false;
                        context = getApplicationContext();
                        CharSequence text = "Trigger message is too long..";
                        int duration = Toast.LENGTH_SHORT;
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    if (mood.getTrigger() == null || mood.getTrigger().equals("")) {
                        validFilter = false;
                        context = getApplicationContext();
                        CharSequence text = "Please enter a filter term";
                        int duration = Toast.LENGTH_SHORT;
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }


                if (validFilter) {
                    //TODO: JOSH, at this point filter settings are valid, so filter feed FILTERBY()
                    // using searchPastWeek, emotion, and


                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("filter_option", filterChoice);

                    if (filterChoice == 1) {
                        filterEmotion = mood.getEmoji();
                        resultIntent.putExtra("mood_filter", filterEmotion);

                    }

                    if (filterChoice == 2) {
                        filterTrigger = mood.getTrigger();
                        resultIntent.putExtra("mood_filter", filterTrigger);

                    }

                    if (filterChoice == 3) {
                        filterDate = mood.getDate();
                        //TODO: BY DATE
                        resultIntent.putExtra("mood_filter", filterDate);
                    }

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();


                    //Log.d("SEARCHTERM IS", searchTerm);
                    //Log.d("EMOTION IS", emotion);
                    //Log.d("BOOL IS", Boolean.toString(searchPastWeek));
                }


            }

        });
    }
    /*** REFACTORING
     * Remove unnecessary semicolen
      */

    public void rbClick(View v) {
        int rbId = radioGroup.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(rbId);


        if (rb.getText() == getResources().getString(R.string.filter_mood_select_filter)) {
            filterChoice = 1;
        }
        if (rb.getText() == getResources().getString(R.string.filter_trigger_select_filter)) {
            filterChoice = 2;
        }
        if (rb.getText() == getResources().getString(R.string.filter_week_select_filter)) {
            filterChoice = 3;
        }


    }
};



