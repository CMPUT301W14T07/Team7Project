package ca.ualberta.team7project.network;

import java.util.Collection;

/*
 * Represents part of a search response from ElasticSearch.
 * Taken from https://github.com/zjullion/PicPosterComplete
 * which is taken from https://github.com/rayzhangcl/ESDemo
 */

public class Hits<T>
{

	int total;
	double max_score;
	Collection<ElasticSearchResponse<T>> hits;

	public Collection<ElasticSearchResponse<T>> getHits()
	{

		return hits;
	}

	public String toString()
	{

		return (super.toString() + "," + total + "," + max_score + "," + hits);
	}
}
