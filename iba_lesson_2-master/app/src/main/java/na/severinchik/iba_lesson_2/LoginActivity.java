package na.severinchik.iba_lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText userNameET;
    EditText passwordET;
    Button singIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameET = findViewById(R.id.userName_ET);
        passwordET = findViewById(R.id.password_ET);
        singIn = findViewById(R.id.signIn_btn);

        singIn.setOnClickListener(v->{
            String userName = userNameET.getText().toString();
            String password = passwordET.getText().toString();

            User user = new User(userName,password);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("SignIn",user);
            startActivity(intent);
        });
    }
}