package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    MyDBHandler db = new MyDBHandler(this, null, null, 1);
    int score = 0;


    CountDownTimer countDownTimer;
    CountDownTimer moletimer;
    TextView displayscore;
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;
    Button back;
    String name;
    Integer level;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        countDownTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                Toast.makeText(getApplicationContext(), "Get ready " +millisUntilFinished/ 1000+ " seconds !", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_LONG).show();

                countDownTimer.cancel();
                newMolePlaceTimer(level);

            }
        };
        countDownTimer.start();


    }
    private void newMolePlaceTimer(final int level){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        Log.v(TAG,"Mole Timer running");
        int time = 11000 - (level *1000);
        moletimer = new CountDownTimer(time, 1000){
            public void onTick(long millisUntilFinished){

            }

            public void onFinish(){

                Log.v(TAG, "New Mole Location!");
                moletimer.cancel();
                setNewMole(level);
                moletimer.start();

            }
        };
        moletimer.start();
        setNewMole(level);

    }
    private static final int[] BUTTON_IDS = {
            R.id.hole1,R.id.hole2,R.id.hole3,R.id.hole4,R.id.hole5,R.id.hole6,R.id.hole7,R.id.hole8,R.id.hole9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
        Bundle receivingEnd = getIntent().getExtras();
        name = receivingEnd.getString("Username");
        level = Integer.valueOf(receivingEnd.getString("level"));
        readyTimer();
        displayscore = findViewById(R.id.score);
        back = findViewById(R.id.bgame);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityName = new Intent(Main4Activity.this, Main3Activity.class);
                activityName.putExtra("Username",name);
                startActivity(activityName);
                updateUserScore(score);
            }
        });
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button temp3 = findViewById(id);
            Log.v("WHAT", (String) temp3.getText());

            temp3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("WHAT"," Before Button:" + temp3.getText());
                    int check = doCheck(temp3);
                    Log.v("WHAT", "check: " + String.valueOf(check));
                    if(check == 1){
                        Log.v(TAG, "Hit, score added!");
                        score += 1;
                        displayscore.setText(Integer.toString(score));
                        Log.v("WHAT", "Current User Score: " + String.valueOf(score));
                    }
                    else if(check==2) {
                        Log.v(TAG, "Missed, point deducted!");
                        score-=1;
                        displayscore.setText(Integer.toString(score));
                        Log.v("WHAT", "Current User Score: " + String.valueOf(score));


                    }






                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();

    }
    private int doCheck(Button checkButton)
    {
        if (checkButton.getText().equals("*") ) {
            return 1;
        }
        else if (checkButton.getText() == "O") {
            return 2;
        }
        else{
            return 3;
        }

    }

    public void setNewMole(int level) {
    /* Hint:
        Clears the previous mole location and gets a new random location of the next mole location.
        Sets the new location of the mole.
     */
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i<= 8;i++){
            numbers.add(i);
        }
        Log.v("Test",numbers.toString());
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        int randomLocation2 = ran.nextInt(8);
        if(level <=5 ) {
            for (int i = BUTTON_IDS.length - 1; i >= 0; i--) {
                Button temp = findViewById(BUTTON_IDS[i]);

                if (i != randomLocation) {
                    temp.setText("O");
                } else {
                    temp.setText("*");

                }
            }
        }
        else{
            Integer pos1 = numbers.get(randomLocation);
            numbers.remove(randomLocation);
            Log.v("Test",numbers.toString());
            Integer pos2 =numbers.get(randomLocation2);
            Log.v("Test",pos1.toString());
            Log.v("Test",pos2.toString());
            for (int i = BUTTON_IDS.length - 1; i >= 0; i--) {
                Button temp = findViewById(BUTTON_IDS[i]);
                if(i != pos1 && i != pos2){
                    temp.setText("O");
                }
                else{
                    temp.setText("*");
                }}

        }
    }

    private void updateUserScore(Integer score)
    {

     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */
        try{
            countDownTimer.cancel();
            newMolePlaceTimer.cancel();
            db.updatescore(score,name,level);
        }
        catch(Exception e){
            countDownTimer.cancel();

            db.updatescore(score,name,level);
        }



    }

}
