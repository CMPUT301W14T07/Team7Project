package ca.ualberta.team7project.cache;

import java.util.ArrayList;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Singleton to hold the pool of threads
 * <p>
 * Should be loaded from file on start, and saved to file on every change
 * <p>
 * Updated on every pull. When favorites are added, all comments in their topic will be added to this pool
 * @author wzhong3
 *
 */
public class ThreadModelPool
{
	public static ArrayList<ThreadModel> threadModelPool = new ArrayList<ThreadModel>();
}
