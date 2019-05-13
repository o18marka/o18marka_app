package com.example.projekt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String[] allaobjekt = {"Testobjekt", "Testobjekt2"};
    private String[] allinfo = {"Testobjekt3", "Testobjekt4"};
    private int[] allinfo2 ={};
    private ArrayList<String> listData=new ArrayList<>(Arrays.asList(allaobjekt));
    private ArrayList<Minklass> minAdapter=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,R.layout.content_main,R.id.my_item, listData);
        ListView my_listview=(ListView) findViewById(R.id.my_listview); //Skapar en listview
        my_listview.setAdapter(adapter);

        minAdapter.add(new Minklass("Matterhorn","Alps",4478)); //här
        minAdapter.add(new Minklass("Mont Blanc","Alps",4808)); //här
        minAdapter.add(new Minklass("Denali","Alaska",6190)); //här

        ArrayAdapter<Minklass> adapter2=new ArrayAdapter<>(this,R.layout.content_main,R.id.my_item,minAdapter);

        listData=new ArrayList<>(Arrays.asList(allaobjekt)); //Listar datan i allaobjekt fältet

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
