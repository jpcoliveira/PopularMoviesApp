package com.example.android.popularmoviesapp.activities;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.views.MainView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentHome fragmentHome = new FragmentHome();
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentHome);
        fragmentTransaction.commit();

//        ImageView img1 = (ImageView) findViewById(R.id.img_1);
//        ImageView img2 = (ImageView) findViewById(R.id.img_2);
//        ImageView img3 = (ImageView) findViewById(R.id.img_3);
//        ImageView img4 = (ImageView) findViewById(R.id.img_4);
//
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(img1);
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(img2);
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(img3);
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(img4);
    }

}
