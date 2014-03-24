package ca.ualberta.team7project.cache;

import java.util.ArrayList;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Singleton pattern
 * only one static variable threadModelPool, which is a list of ThreadModel
 * @author wzhong3
 *
 */
public class ThreadModelPool {
	
	public static ArrayList<ThreadModel> threadModelPool = new ArrayList<ThreadModel>();

}
