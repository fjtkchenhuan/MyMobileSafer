package app.com.example.android.mymobilesafer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.example.android.mymobilesafer.R;
import app.com.example.android.mymobilesafer.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new HomeFragment()).commit();
        }
    }
}
