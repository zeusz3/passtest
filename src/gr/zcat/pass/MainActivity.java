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
	
	private final Hashtable<Byte, String> dict = new Hashtable<Byte, String>(256);
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
		EditText editText1 = (EditText) findViewById(R.id.logicETX1);
		EditText editText2 = (EditText) findViewById(R.id.logicETX2);
		EditText editText3 = (EditText) findViewById(R.id.logicETX3);
		EditText editText4 = (EditText) findViewById(R.id.logicETX4);
		EditText editText5 = (EditText) findViewById(R.id.logicETX5);
		EditText passLogicETX = (EditText) findViewById(R.id.passLogicETX);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[][] hash = new byte[5][];
			hash[0] = md.digest(editText1.getText().toString().getBytes());
			hash[1] = md.digest(editText2.getText().toString().getBytes());
			hash[2] = md.digest(editText3.getText().toString().getBytes());
			hash[3] = md.digest(editText4.getText().toString().getBytes());
			hash[4] = md.digest(editText5.getText().toString().getBytes());
			StringBuilder sb = new StringBuilder(hash[0].length);
			for(int i = 0; i < hash[0].length && i < 12*(hash[0].length/12); i+=(hash[0].length/(12))) {
                int b = 0;
                for (int j = 0; j < 5; j++) {
                    b ^= hash[j][i];
                }
                sb.append(dict.get((byte)b));
            }
			passLogicETX.setText(sb.toString());
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
	    		dict.put((byte)i, String.valueOf(allowedSymbols[i % 81]));
    		} else {
    			int j = i;
    			while(bannedSymbols.contains(String.valueOf(allowedSymbols[j % 81]))) {
    				j++;
    			}
	    		dict.put((byte)j, String.valueOf(allowedSymbols[j % 81]));
    		}
    	}
    }
    
}
