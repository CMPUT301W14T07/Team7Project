package ca.ualberta.team7project.cache;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import ca.ualberta.team7project.models.ThreadModel;

import com.google.gson.Gson;

/**
 * Helper class, some Basic operations between main memory and file system<p>
 * it help to save threadModelpool from memory to file sytem
 * it help to load threadModelpool from file system to memory
 * @author wzhong3
 *
 */

public class MemoryToFileOperation{

	private static final String FILENAME = "threadmodelpool.sav";
	private Context context;
	
	public MemoryToFileOperation(Context context){
		this.context = context;
	}
	/**
	 * Load the threadmodelpool in the main memory to the file system
	 */
	public void saveInFile() {
		// build gson object
		Gson gson = new Gson();

		try {
			FileOutputStream fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			for (int i = 0; i < ThreadModelPool.threadModelPool.size(); i++) {
				fos.write((gson.toJson(ThreadModelPool.threadModelPool.get(i)) + "\n")
						.getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 *  Load everything from threadmodelpool.sav file
	 *   And store them in threadModelPool in main memory
	 */
	public void loadFromFile() {
		
		//build gson object
		Gson gson = new Gson();
		
		//clear the threadModelPool
		ThreadModelPool.threadModelPool.clear();
	
		ThreadModel threadModel;
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			// each line is a json object
			String line = in.readLine();
			while (line != null) {
				threadModel = gson.fromJson(line, ThreadModel.class);
				ThreadModelPool.threadModelPool.add(threadModel);
				line = in.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
