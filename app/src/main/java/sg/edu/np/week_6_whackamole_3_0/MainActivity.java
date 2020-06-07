package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    Button enter;
    EditText lname;
    EditText lpw;
    String lName;
    String lPW;
    TextView register;
    MyDBHandler db = new MyDBHandler(this, null, null, 1);
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enter = findViewById(R.id.login);
        lname = findViewById(R.id.name);
        lpw = findViewById(R.id.password);
        register = findViewById(R.id.register);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lName = lname.getText().toString();
                lPW = lpw.getText().toString();

                boolean temp = isValidUser(lName, lPW);
                if (temp == false) {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent activityName = new Intent(MainActivity.this, Main3Activity.class);
                    activityName.putExtra("Username",lName);
                    startActivity(activityName);


                }


            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityName = new Intent(MainActivity.this, Main2Activity.class);

                startActivity(activityName);

            }
        });


        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */


    }

    protected void onStop() {
        super.onStop();
        finish();
    }
    public boolean isValidUser(String userName, String password) {
        Log.v("CHECK", userName);
        Log.v("CHECK", password);
        UserData user = db.findUser(userName);
        if (user != null) {
            String compare = user.getMyPassword();
            Log.v("CHECK", String.valueOf(compare.equals(password)));
            if (compare.equals(password)) {
                return true;
            } else {
                return false;
            }


        }
        else{
            return false;
        }


    }
}


