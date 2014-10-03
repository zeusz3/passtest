package gr.zcat.pass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.TreeSet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	
	private final Hashtable<String, String> dict = new Hashtable<String, String>(256);
	private final char[] allowedSymbols = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '[',
		']', '}', '|', ':', ';', '/', '?', '>', '.', ',', '<', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
		'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private final TreeSet<String> bannedSymbols = new TreeSet<String>();
	//{'\\', '\"', '\''};

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
    	EditText editText1 = (EditText) findViewById(R.id.editText1);
    	EditText editText2 = (EditText) findViewById(R.id.editText2);
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] hash = md.digest(editText1.getText().toString().getBytes());
			StringBuilder sb = new StringBuilder(hash.length * 2);
			Formatter formatter = new Formatter(sb); 
			for (byte b : hash) { 
				formatter.format("%02x", b);
			}
			editText2.setText(sb.toString());
			//editText2.setText(Integer.toHexString(hash[0]));
		} catch (NoSuchAlgorithmException e) {
			editText2.setText("no such algo");
			e.printStackTrace();
		}
    }
    
    private void initializeDict() {
    	bannedSymbols.add("\"");
    	bannedSymbols.add("\\");
    	bannedSymbols.add("\'");
    	for(int i = 0; i < 256; i++) {
    		if(!bannedSymbols.contains(String.valueOf(allowedSymbols[i % 81]))) {
	    		if(i < 10) {
	    			dict.put("0" + Integer.toHexString(i), String.valueOf(allowedSymbols[i % 81]));
	    		} else {
	    			dict.put(Integer.toHexString(i), String.valueOf(allowedSymbols[i % 81]));
	    		}
    		} else {
    			int j = i;
    			while(bannedSymbols.contains(String.valueOf(allowedSymbols[j % 81]))) {
    				j++;
    			}
    			if(i < 10) {
	    			dict.put("0" + Integer.toHexString(j), String.valueOf(allowedSymbols[j % 81]));
	    		} else {
	    			dict.put(Integer.toHexString(j), String.valueOf(allowedSymbols[j % 81]));
	    		}
    		}
    	}
    }
    
}
