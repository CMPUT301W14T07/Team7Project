package ca.ualberta.team7project.network;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Represents a search response from ElasticSearch.
 * <p>
 * Taken from https://github.com/zjullion/PicPosterComplete
 * which is taken from https://github.com/rayzhangcl/ESDemo
 */
public class ElasticSearchSearchResponse<T>
{

	int took;
	boolean timed_out;
	transient Object _shards;
	Hits<T> hits;
	boolean exists;

	public Collection<ElasticSearchResponse<T>> getHits()
	{
		if(hits != null)
			return hits.getHits();
		else
			return new ArrayList<ElasticSearchResponse<T>>();
	}

	public Collection<T> getSources()
	{

		Collection<T> out = new ArrayList<T>();
		for (ElasticSearchResponse<T> essrt : getHits())
		{
			out.add(essrt.getSource());
		}
		return out;
	}

	public String toString()
	{

		return (super.toString() + ":" + took + "," + _shards + "," + exists
				+ "," + hits);
	}
}
