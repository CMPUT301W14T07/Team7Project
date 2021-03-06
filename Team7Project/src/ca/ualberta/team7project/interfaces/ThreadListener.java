package ca.ualberta.team7project.interfaces;

import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Listener for the ThreadListView
 */
public interface ThreadListener
{
	public void onFavoriteClick(ThreadModel thread);
	public void onTagClick(ThreadModel thread);
	public void onReplyClick(ThreadModel thread);
	public void onCacheClick(ThreadModel thread);
	public void onPhotoClick(ThreadModel thread);
	public void onEditClick(ThreadModel thread);
	public void onThreadClick(ThreadModel thread);
	public void notifyListChange(ThreadListModel list);
	public void notifyThreadInserted(ThreadModel thread);
	public void onRefresh();
	public void onTagSearch(String searchText);
}
