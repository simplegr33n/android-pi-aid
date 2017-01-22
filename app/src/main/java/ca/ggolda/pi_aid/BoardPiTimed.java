package ca.ggolda.pi_aid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BoardPiTimed extends AppCompatActivity {

    private String mPi = "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679";
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

    private long timeRemaining = 0;

    private CountDownTimer countDown;

    private TextView fullGuess;

    private TextView calculateTextview;

    private TextView regularButton;
    private TextView playButton;


    private TextView timeleftTextview;

    private TextView messageTextview;
    private TextView scoreTextview;
    private TextView highscoreTextview;

    private RelativeLayout breakLayout;
    private RelativeLayout piInfo;

    SharedPreferences sharedPref;
    private int highScore;
    private int sleepTime;

    private int userScore = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_pi_timed);


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

        timeleftTextview = (TextView) findViewById(R.id.time_left);
        timeleftTextview.setText("seconds remaining: 10");

        fullGuess = (TextView) findViewById(R.id.fullguess);
        fullGuess.setVisibility(View.GONE);

        // get current high score and sleeptime from shared preferences
        sharedPref = getSharedPreferences("ggco_colormem_values", MODE_PRIVATE);
        highScore = sharedPref.getInt("highscore_pi", 0);
        sleepTime = sharedPref.getInt("sleep_time", 850);

        messageTextview = (TextView) findViewById(R.id.message);
        messageTextview.setTextSize(44);
        messageTextview.setTextColor(Color.parseColor("#FFFFFF"));
        messageTextview.setText("You will have 10 seconds once you start typing");


        calculateTextview = (TextView) findViewById(R.id.calculate_textview);

        scoreTextview = (TextView) findViewById(R.id.score);


        highscoreTextview = (TextView) findViewById(R.id.highscore);
        if (highScore != 0) {
            highscoreTextview.setText("High Score: " + String.valueOf(highScore));
        }

        breakLayout.setVisibility(View.VISIBLE);

        regularButton = (TextView) findViewById(R.id.timed_button);
        regularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardPiTimed.this, BoardPi.class);
                startActivity(intent);
            }
        });

        playButton = (TextView) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessPlay();
            }
        });


    }


    // function for testing and responding to guess
    private void guessPress(String press) {

        if (press.equals(String.valueOf(mPi.charAt(guessList.length())))) {

            guessList = guessList + press;
            calculateTextview.setText(guessList);

            if (guessList.length() == 1) {
                countDown = new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //do something in every tick

                        timeleftTextview.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timeleftTextview.setText("seconds remaining: 0");

                        //Disable buttons while break menu up
                        disableButtons();
                        breakLayout.setVisibility(View.VISIBLE);


                        String nextDigit = String.valueOf(mPi.charAt(guessList.length()));

                        messageTextview.setTextColor(Color.parseColor("#FF0000"));
                        messageTextview.setText("Next Digit: " + nextDigit );

                        scoreTextview.setTextColor(Color.parseColor("#FFFFFF"));
                        scoreTextview.setText(String.valueOf(userScore));

                        fullGuess.setVisibility(View.VISIBLE);
                        fullGuess.setText(guessList);


                        highscoreTextview = (TextView) findViewById(R.id.highscore);
                        if (highScore != 0) {
                            highscoreTextview.setText("High Score: " + String.valueOf(highScore));
                        }


                        breakLayout.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                guessList = "";
                                calculateTextview.setText("");
                                timeleftTextview.setText("seconds remaining: 10");
                                guessPlay();

                            }
                        });
                    }
                }.start();

            }



            scoreTextview.setTextColor(Color.parseColor("#FFFFFF"));

            userScore = guessList.length();

            // if new usesScore beats old high score, change value in shared preferences
            if (userScore >= highScore) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("highscore_pi", userScore);
                editor.apply();

                highScore = sharedPref.getInt("highscore_pi", 0);
            }

        } else {
            timeleftTextview.setText("");

            //When to cancel the CountDownTimer
            if (countDown != null) {
                countDown.cancel();
            }

            timeleftTextview.setText("seconds remaining: 10");

            //Disable buttons while break menu up
            disableButtons();

            String nextDigit = String.valueOf(mPi.charAt(guessList.length()));

            messageTextview.setTextColor(Color.parseColor("#FF0000"));
            messageTextview.setText("Next Digit: " + nextDigit );

            scoreTextview.setTextColor(Color.parseColor("#FFFFFF"));
            scoreTextview.setText(String.valueOf(userScore));

            fullGuess.setText(guessList);



            highscoreTextview = (TextView) findViewById(R.id.highscore);
            if (highScore != 0) {
                highscoreTextview.setText("High Score: " + String.valueOf(highScore));
            }

            breakLayout.setVisibility(View.VISIBLE);
            playButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    guessList = "";
                    calculateTextview.setText("");
                    guessPlay();

                }
            });

        }

    }

    private void guessPlay() {
        timeleftTextview.setText("seconds remaining: 10");
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
        Intent intent = new Intent(BoardPiTimed.this, BoardPi.class);
        startActivity(intent);

        return;
    }
}
