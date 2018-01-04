package sporter.pokeapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;


public class AuthActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    FingerPrintAuthHelper mFingerPrintAuthHelper;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private EditText mName;
    private ImageButton btnSubmit;
    private CheckBox mCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);

        mName = (EditText) findViewById(R.id.favPokemon);
        btnSubmit = (ImageButton) findViewById(R.id.submitButton);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);


        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox.isChecked()) {
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    String name = mName.getText().toString();
                    mEditor.putString(getString(R.string.name), name);
                    mEditor.commit();


                }else{

                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.name), "");
                    mEditor.commit();

                }
            }
        });


    }



    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String name = mPreferences.getString(getString(R.string.name), "Favourite Pokémon?");

        mName.setText(name);

        if(checkbox.equals("True")) {
            mCheckBox.setChecked(true);
        }
        else {
            mCheckBox.setChecked(false);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "Device does not have finger print scanner", Toast.LENGTH_SHORT).show();
            }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(this, "There are no finger prints registered on this device", Toast.LENGTH_SHORT).show();
           }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Intent myIntent = new Intent(AuthActivity.this, MainActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        AuthActivity.this.startActivity(myIntent);
        Toast.makeText(this, "Access Granted", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                Toast.makeText(this, "Cannot recognize the fingerprint scanned", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}