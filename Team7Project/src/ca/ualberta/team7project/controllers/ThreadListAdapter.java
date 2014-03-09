package ca.ualberta.team7project.controllers;

import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import android.content.Context;
import android.widget.ArrayAdapter;

public class ThreadListAdapter extends ArrayAdapter<ThreadModel>
{

	public ThreadListAdapter(Context context, int resource,
			ThreadListModel listModel)
	{

		super(context, resource);
		// TODO Auto-generated constructor stub
	}


}
