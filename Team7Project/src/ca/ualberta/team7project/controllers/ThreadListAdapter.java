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
	
	@Override
	public View getView(int position, View view, ViewGroup parent)
	{

		if (view == null)
		{
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.single_row, null);
		}

		ThreadModel tp = this.getItem(position);

		if (tp != null)
		{

			TextView tv = (TextView) view.findViewById(R.id.comment_string);
			tv.setText(tp.getComment());

			ImageView im = (ImageView) view.findViewById(R.id.picture);
			if (im != null)
			{
				im.setImageBitmap(tp.getImage());
			}

			TextView ts = (TextView) view.findViewById(R.id.timestamp);
			ts.setText(tp.getTimestamp().toString());

			TextView an = (TextView) view.findViewById(R.id.author);
			an.setText(tp.getAuthorName());

		}
		return view;
	}
}
