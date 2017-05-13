package customannotation.android.com.customannotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.java.processor.processor.BuilderAnnotation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
