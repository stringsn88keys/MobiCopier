package com.tp.mobimover;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

public class MobiMoverActivity extends Activity {
	
	private void copyFile(String source, String destination) throws FileNotFoundException, IOException {
		InputStream in = new FileInputStream(source);
		OutputStream out = new FileOutputStream(destination);
		byte[] buf = new byte[1024];
		int len;
		while((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button loadListButton = (Button) findViewById(R.id.loadList);
        loadListButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		final EditText textListing = (EditText) findViewById(R.id.editText1);
        		StringBuilder sb = new StringBuilder("");
        		
        		try {
        			File[] mobiFiles = DirectoryTreeFilter.finderByExtension("/sdcard", ".mobi");
        			
	        		for(File mobiFile : mobiFiles) {
	        			File documentsFile = new File("/sdcard/Documents" + File.separator + mobiFile.getName());
	        			
	        			if(mobiFile.getPath().contains("/sdcard/Documents")) {
	        				continue;
	        			}
	        			if(documentsFile.exists()) {
	        				sb.append("File already in documents: " + mobiFile.getPath() + "\n");
	        			} else {    				
	        				try {
	        					copyFile(mobiFile.getPath(), documentsFile.getPath());
	        					sb.append("Copied to documents: " + mobiFile.getPath() + "\n");
	        				} catch (Exception ex) {
	        					sb.append(ex.toString() + " while dealing with " + mobiFile.getPath() + "\n");
	        				}
	        				
	        			}
	        		}
	        		textListing.setText(sb.toString());
        		} catch (Exception ex) {
        			sb.append(ex.toString() + "\n");
        		}
        	}
        });
    }
}