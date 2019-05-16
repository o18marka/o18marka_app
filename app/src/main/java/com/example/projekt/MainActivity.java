package com.example.projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static String  EXTRA_MESSAGE = " ";
    private String[] Names = {" "};
    private ArrayList<String> listData;
    private ArrayAdapter<Minklass> minAdapter;
    public String extended_fab_label="Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        new FetchData().execute();//Hämtar datan
        listData=new ArrayList<>(Arrays.asList(Names)); //Listar den hämtade datan i mountainNames fältet
        minAdapter=new ArrayAdapter<Minklass>(this,R.layout.content_main,R.id.my_item);

        ListView my_listview=(ListView) findViewById(R.id.my_listview); //Skapar en listview
        my_listview.setAdapter(minAdapter); //Listview ska innehålla data från mountainAdapter

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Toast som bestämmer vilken information som ska printas beroende på variabeln i (senare skapad for-loop)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = minAdapter.getItem(i).info();
                Intent intent = new Intent(MainActivity.this, infoactivity.class);
                intent.putExtra(EXTRA_MESSAGE, temp);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = "\t\t\t\t\t\t\t\t\t\t\tAbout this App\n\nThank you for using ShipKnowledge! This app is for you who want easy access to knowledge about historical warships and be able to study on the go.\n\nTry your best to memorise these facts and have fun challenging yourself!";
                Intent intent = new Intent(MainActivity.this, about.class);
                intent.putExtra(EXTRA_MESSAGE, temp);
                startActivity(intent);
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

        if(id == R.id.action_refresh){
            minAdapter.clear(); //Rensar listan vid refresh så gamla versioner rensas
            new FetchData().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<Void,Void,String> {
        @Override

        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/o18marka/JSONforAndroid/getdataasjson.json?type=brom"); //URL

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            Log.d("brom","DataFetched:"+o);

            try {
                JSONArray toastArray = new JSONArray(o);
                for (int i = 0; i < toastArray.length(); i++) { //for-loop som hämtar alla berg i php dokumentet som sedan läggs till i strängen m som lägggs till i adaptern som listas i listview
                    Log.d("brom", "element 0:" + toastArray.get(i).toString());
                    JSONObject container = toastArray.getJSONObject(i);
                    //loggar data om bergen i objectet container
                    Log.d("brom", container.getString("name"));
                    Log.d("brom", container.getString("location"));
                    Log.d("brom", "" + container.getInt("size"));
                    Log.d("brom", "" + container.getString("category"));
                    Log.d("brom", "" + container.getInt("company"));
                    Log.d("brom", "" + container.getString("cost"));
                    Log.d("brom", "" + container.getString("auxdata"));
                    Minklass m = new Minklass(container.getString("name"), container.getString("location"), container.getInt("size"), container.getString("category"),container.getInt("company"),container.getString("cost"),container.getString("auxdata"));
                    Log.d("brom", m.toString());
                    minAdapter.add(m);
                }
            }
            catch (JSONException e){
                Log.e("brom","E:"+e.getMessage());
            }
        }
    }
}



