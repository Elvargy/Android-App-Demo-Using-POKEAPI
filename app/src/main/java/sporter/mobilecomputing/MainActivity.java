package sporter.mobilecomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        Call<List<Pokemon>> call = pokeapi.getPokemonNameAndPic();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                List<Pokemon> pokemon = response.body();

                    String[] pokemonNames = new String[pokemon.size()];

                    for (int i = 0; i < pokemon.size(); i++) {

                        pokemonNames[i] = pokemon.get(i).getName();
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
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}


