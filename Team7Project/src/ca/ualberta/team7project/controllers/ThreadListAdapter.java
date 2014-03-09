package ca.ualberta.team7project.controllers;

import java.util.List;

import ca.ualberta.team7project.R;
import ca.ualberta.team7project.models.ThreadModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ThreadListAdapter extends ArrayAdapter<ThreadModel>
{

	public ThreadListAdapter(Context context, int resource,
			int textViewResourceId, List<ThreadModel> objects)
	{
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
}
