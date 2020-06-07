package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */
    UserData empty;
    Button enter;
    EditText name;
    EditText pw;
    String Name;
    String Pw;
    Button cancel;
    MyDBHandler db= new MyDBHandler(this, null, null, 1);
    ArrayList<Integer> score = new ArrayList<>();
    ArrayList<Integer> level = new ArrayList<>();
    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        enter = findViewById(R.id.create);
        cancel = findViewById(R.id.cancel);
        name = findViewById(R.id.newname);
        pw =findViewById(R.id.newpassword);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString();
                Pw = pw.getText().toString();

                UserData use= db.findUser(Name);
                if(use == empty){
                    UserData user = new UserData(Name,Pw,score,level);
                    db.addUser(user);
                    db.loadnew(user);
                    Intent activityName = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(activityName);
                    Toast.makeText(getApplicationContext(), "Account is created successfully", Toast.LENGTH_SHORT).show();



                }
                else{
                    Toast.makeText(getApplicationContext(), "User already exist please try again", Toast.LENGTH_SHORT).show();
                }






            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent go = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(go);

            }
        });

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */
    }

    protected void onStop() {
        super.onStop();
        finish();
    }
        /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */

}

