package gr.zcat.pass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	
	private final Hashtable<String, String> dict = new Hashtable<String, String>(160);
	private final char[] allowedSymbols = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '[',
		']', '}', '|', ':', ';', '/', '?', '>', '.', ',', '<', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
		'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private final char[] bannedSymbols = {'\\', '\"', '\''};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDict();
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void getPass(View view) {
    	EditText editText = (EditText) findViewById(R.id.editText2);
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			editText.setText(new String(md.digest(editText.getText().toString().getBytes())));
		} catch (NoSuchAlgorithmException e) {
			editText.setText("no such algo");
			e.printStackTrace();
		}
//    	EditText editText1 = (EditText) findViewById(R.id.editText1);
    	
		
    }
    
    private void initializeDict() {
    	for(int i = 0; i < 256; i++) {
    		if(i < 10) {
    			dict.put("0" + Integer.toHexString(i), String.valueOf(allowedSymbols[i % 81]));
    		} else {
    			dict.put(Integer.toHexString(i), String.valueOf(allowedSymbols[i % 81]));
    		}
    	}
    }
    
}
