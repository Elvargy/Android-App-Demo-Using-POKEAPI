package sporter.pokeapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Shane on 31/12/2017.
 */

public interface Pokeapi {

    String URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon/?limit=949&offset=1")
    Call<Data> getPokemonNameAndPic();

}
