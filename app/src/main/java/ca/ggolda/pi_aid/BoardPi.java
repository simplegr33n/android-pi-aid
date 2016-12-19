package ca.ggolda.pi_aid;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BoardPi extends AppCompatActivity {

    private String mPi;
    private String guessList = "";

    private String flashColor = "#f4f8fa";

    private TextView button0;
    private TextView button1;
    private TextView button2;
    private TextView button3;
    private TextView button4;
    private TextView button5;
    private TextView button6;
    private TextView button7;
    private TextView button8;
    private TextView button9;
    private TextView buttonDot;

    private TextView fullGuess;

    private TextView calculateTextview;

    private TextView messageTextview;
    private TextView scoreTextview;
    private TextView highscoreTextview;

    private TextView playButton;
    private TextView closeInfo;


    private RelativeLayout breakLayout;
    private RelativeLayout piInfo;

    SharedPreferences sharedPref;
    private int highScore;

    private int userScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_pi);

        mPi = getResources().getString(R.string.pi_ten_k);

        breakLayout = (RelativeLayout) findViewById(R.id.BreakLayout);

        button1 = (TextView) findViewById(R.id.one_button);
        button2 = (TextView) findViewById(R.id.two_button);
        button3 = (TextView) findViewById(R.id.three_button);
        button4 = (TextView) findViewById(R.id.four_button);
        button5 = (TextView) findViewById(R.id.five_button);
        button6 = (TextView) findViewById(R.id.six_button);
        button7 = (TextView) findViewById(R.id.seven_button);
        button8 = (TextView) findViewById(R.id.eight_button);
        button9 = (TextView) findViewById(R.id.nine_button);
        button0 = (TextView) findViewById(R.id.zero_button);
        buttonDot = (TextView) findViewById(R.id.dot_button);

        fullGuess = (TextView) findViewById(R.id.fullguess);

        // get current high score and sleeptime from shared preferences
        sharedPref = getSharedPreferences("ggco_pi_aid_values", MODE_PRIVATE);
        highScore = sharedPref.getInt("highscore", 0);

        messageTextview = (TextView) findViewById(R.id.message);
        scoreTextview = (TextView) findViewById(R.id.score);
        calculateTextview = (TextView) findViewById(R.id.calculate_textview);

        playButton = (TextView) findViewById(R.id.play_button);

        piInfo = (RelativeLayout) findViewById(R.id.pi_info);
        piInfo.setVisibility(View.GONE);

        closeInfo = (TextView) findViewById(R.id.close_info);
        closeInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                piInfo.setVisibility(View.GONE);
            }
        });

        highscoreTextview = (TextView) findViewById(R.id.highscore);
        if (highScore != 0) {
            highscoreTextview.setText("High Score: " + String.valueOf(highScore));
        }

        breakLayout.setVisibility(View.VISIBLE);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guessPlay();
            }
        });


    }


    // function for testing and responding to guess
    private void guessPress(String press) {

        if (press.equals(String.valueOf(mPi.charAt(guessList.length())))) {

            guessList = guessList + press;

            calculateTextview.setText(guessList);

            scoreTextview.setTextColor(Color.parseColor("#FFFFFF"));

            userScore = guessList.length();

            // if new usesScore beats old high score, change value in shared preferences
            if (userScore >= highScore) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("highscore", userScore);
                editor.apply();

                highScore = sharedPref.getInt("highscore", 0);
            }

        } else {
            //Disable buttons while break menu up
            disableButtons();

            piInfo.setVisibility(View.VISIBLE);

            String nextDigit = String.valueOf(mPi.charAt(guessList.length()));

            messageTextview.setTextColor(Color.parseColor("#FF0000"));
            messageTextview.setText("Next Digit: " + nextDigit );

            scoreTextview.setTextColor(Color.parseColor("#FFFFFF"));
            scoreTextview.setText(String.valueOf(userScore));

            fullGuess.setText(guessList);

            piInfo.setVisibility(View.VISIBLE);

            highscoreTextview = (TextView) findViewById(R.id.highscore);
            if (highScore != 0) {
                highscoreTextview.setText("High Score: " + String.valueOf(highScore));
            }

            breakLayout.setVisibility(View.VISIBLE);
            breakLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    guessList = "";
                    userScore = 0;
                    calculateTextview.setText("");
                    guessPlay();

                }
            });

        }

    }

    private void guessPlay() {
        breakLayout.setVisibility(View.GONE);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button1.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button1.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("1");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button2.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button2.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("2");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button3.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button3.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("3");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button4.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button4.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("4");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button5.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button5.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("5");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button6.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button6.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("6");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button7.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button7.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("7");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button8.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button8.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("8");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button9.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button9.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("9");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        button0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    button0.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    button0.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress("0");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });

        buttonDot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Make background on-pressed TODO: weird pink for now, pick better color
                    buttonDot.setBackgroundColor(Color.parseColor(flashColor));

                } else if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_CANCEL)// && flag==true)
                {
                    //Return button background on-released
                    buttonDot.setBackgroundColor(Color.parseColor("#c5cacc"));
                    // send guess to guessPress() function
                    guessPress(".");

                } else return false;

                //Must return true here in order to pick up ACTION_UP
                return true;
            }
        });


    }

    private void disableButtons() {
        //Disable color buttons
        button1.setOnTouchListener(null);
        button2.setOnTouchListener(null);
        button3.setOnTouchListener(null);
        button4.setOnTouchListener(null);
        button5.setOnTouchListener(null);
        button6.setOnTouchListener(null);
        button7.setOnTouchListener(null);
        button8.setOnTouchListener(null);
        button9.setOnTouchListener(null);
        button0.setOnTouchListener(null);
        buttonDot.setOnTouchListener(null);

    }



    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
