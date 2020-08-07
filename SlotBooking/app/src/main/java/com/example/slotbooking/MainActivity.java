package com.example.slotbooking;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    Button b;
    String s,st;

    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Connect To Server Before Booking",Toast.LENGTH_SHORT).show();

        s1 = findViewById(R.id.spinner1);
        b = findViewById(R.id.button);

        Retrofit retrofit = RetroClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);

        ArrayAdapter ad1 = ArrayAdapter.createFromResource
                (this,R.array.user,
                        android.R.layout.simple_list_item_activated_1);
        s1.setAdapter(ad1);
        s1.setOnItemSelectedListener(this);
        st="@gmail.com";
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot(s,s.concat(st));
            }
        });
    }

    private void slot(final String username, String email){
        compositeDisposable.add(myAPI.slot(username,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("username")) {
                            Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                        }
//                        else
//                            Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_SHORT).show();
                    }
                })

        );
    }

    @Override
    public void onItemSelected
            (AdapterView<?> ad, View view, int i, long l) {

        if(ad.getId()==s1.getId())
        {
            Log.d("MainActivity","Spinner1");
            TextView tv = (TextView)view;
             s = tv.getText().toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,
                "Nothing selected",
                Toast.LENGTH_SHORT).show();
        Log.d("MainActivity","Nothing selected");

    }
}
