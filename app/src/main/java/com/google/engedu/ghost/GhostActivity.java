package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static android.R.attr.keycode;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String COMPUTER_WINS = "Computer WINS";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    // private SimpleDictionary simpleDictionary;
    private boolean userTurn = false;
    private Random random = new Random();
     TextView ghosttext ;
     TextView gamestatus ;
    private Button bChallenge ;
    private Button bRestart ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        ghosttext = (TextView)findViewById(R.id.ghostText);
        gamestatus = (TextView)findViewById(R.id.gameStatus);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
            // simpleDictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        char unicodeChar = (char)event.getUnicodeChar();
        String tempGhostText = ((String) ghosttext.getText()).trim();

        if(unicodeChar>='a' && unicodeChar<='z' || unicodeChar>='A' && unicodeChar<='Z')
        {
            tempGhostText = tempGhostText + unicodeChar ;
            ghosttext.setText(tempGhostText);
            gamestatus.setText(COMPUTER_TURN);
            computerTurn();
            return super.onKeyUp(keyCode, event);


        }
        else
        {
            return super.onKeyUp(keyCode, event);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        //TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again

        String tempGhostText = (String) ghosttext.getText();
        int currentlength = tempGhostText.length();
        Toast.makeText(getApplicationContext(), dictionary.getAnyWordStartingWith(tempGhostText), Toast.LENGTH_SHORT).show();
        if(dictionary.isWord(tempGhostText))
        {
            gamestatus.setText(COMPUTER_WINS);
        }


        else if(dictionary.getAnyWordStartingWith(tempGhostText) == null)
        {
            gamestatus.setText(COMPUTER_WINS);
            //gamestatus.setText("THE COMPUTER WINS");
            Toast.makeText(getApplicationContext(), "THE COMPUTER WINS", Toast.LENGTH_SHORT).show();

        }
        else if(dictionary.getAnyWordStartingWith(tempGhostText) != null)
        {
            String tempo = dictionary.getAnyWordStartingWith(tempGhostText);
            Log.e("raju Koushik" , tempGhostText);
            tempGhostText = tempGhostText  + tempo.charAt(currentlength);
            Log.e("raju Koushik" , tempGhostText);
            ghosttext.setText(tempGhostText);
            gamestatus.setText(USER_TURN);
        }


        //gamestatus.setText(USER_TURN);
    }

    public void ghostText(View view) {
        //on click event handler for the ghostText TextView
    }

    public void clickChallenge(View view) {
        //on click event handler for the CHALLENGE button
        String tempGhostText = ((String) ghosttext.getText()).trim();
        if(tempGhostText.length()>=4 && dictionary.isWord(tempGhostText))
        {
            gamestatus.setText("VICTORY FOR THE USER");
        }
        else if(dictionary.getAnyWordStartingWith(tempGhostText) != null)
        {
            gamestatus.setText("VICTory FOR THE COMPUTER" + " " + dictionary.getAnyWordStartingWith(tempGhostText));
        }
        if(dictionary.getAnyWordStartingWith(tempGhostText) == null)
        {
            gamestatus.setText("VICTORY FOR THE USER");
        }


    }
}
