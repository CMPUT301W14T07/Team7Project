package ca.ualberta.team7project.network;

/*
 * Represents part of a response from ElasticSearch.
 * Taken from https://github.com/zjullion/PicPosterComplete
 * Taken from https://github.com/rayzhangcl/ESDemo
 */

public class ElasticSearchResponse<T> {
	String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;
    public T getSource() {
        return _source;
    }
}
