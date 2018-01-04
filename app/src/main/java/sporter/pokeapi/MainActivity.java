package sporter.pokeapi;

/**
 * Created by Shane on 04/01/2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Pokeapi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Pokeapi pokeapi = retrofit.create(Pokeapi.class);


        Call<Data> call = pokeapi.getPokemonNameAndPic();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("response", response.body().toString());
                Data data = response.body();

                String[] pokemonNames = new String[data.getResults().size()];

                for (int i = 0; i < data.getResults().size(); i++) {

                    pokemonNames[i] = data.getResults().get(i).getName();
                }
                for (String item : pokemonNames) {
                    Log.d("item", item);
                }
                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                pokemonNames
                        )
                );

            }


            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}