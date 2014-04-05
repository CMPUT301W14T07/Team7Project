package ca.ualberta.team7project.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * ThreadTagModel for ThreadModel.
 * <p>
 * Holds and ArrayList of tags that are associated which each individual thread.
 * <p>
 * Includes is formatting methods for returning to a view.
 * <p>
 * Tags are ALWAYS sorted alphabetically.
 * @author raypold
 *
 */
public class ThreadTagModel
{
	
	private ArrayList<String> innerTags;
	
	public ThreadTagModel()
	{
		this.innerTags = new ArrayList<String>();
	}
	
	/**
	 * Parse a string and set the current Model's tags after trimming whitespace
	 * @param tags to be parsed
	 * @param delimiter the character sequence between tokens
	 */
	public void parseAndSet(String tags, String delimiter)
	{
		setTags(sort(parseTags(tags.trim(), delimiter)));
	}
	
	/**
	 * Parse string tags and append it to the list of tags
	 * @param tags A string to be parsed by regex
	 */
	public void parseAndAppend(String tags)
	{
		String[] tokens = tags.split(",|\\s");
		
		for(String token : tokens)
			addTag(token);
	}
	
	/**
	 * Retrieve how many tags are contained in the model
	 * @return the number of tags in the model
	 */
	public int tagCount()
	{
		return this.innerTags.size();
	}
	
	/**
	 * Create an ArrayList of tokens from a string of words
	 * @param tags the string of tokens to be parsed
	 * @param delimiter the character sequence between tokens
	 * @return A formatted ArrayList of tags
	 */
	public ArrayList<String> parseTags(String tags, String delimiter)
	{		
		String[] tokens = tags.split(delimiter);
		ArrayList<String> parsed = new ArrayList<String>(Arrays.asList(tokens));
		return parsed;
	}
	
	public void addTag(String tag)
	{
		this.innerTags.add(tag);
		this.innerTags = sort(this.innerTags);
	}
	
	public void removeTag(String tag)
	{
		this.innerTags.remove(tag);
	}
	
	/**
	 * Searches for a thread tag within the model
	 * @param tag to search for
	 * @return whether the tags is contained in the model
	 */
	public boolean contains(String tag)
	{
		return this.innerTags.contains(tag);
	}
	
	/**
	 * Sort alphabetically
	 * @param tags ArrayList<String> of tags to be sorted
	 * @return Sorted ArrayList of tags
	 */
	public ArrayList<String> sort(ArrayList<String> tags)
	{
		Collections.sort(tags);
		return tags;
	}
	
	/**
	 * Takes a delimiter and returns a formatted string of tags
	 * <p>
	 * A more complicated for loop is used so as to not append an extra delimiter
	 * at the end of the StringBuilder.
	 * @param delimiter the sequence of characters between string tags
	 * @return a formatted string of tags
	 */
	public String tagFormatter(String delimiter)
	{
		StringBuilder builder = new StringBuilder();
				
		int size = this.innerTags.size();
		
		if(size > 0)
		{
			int i = 0;
			
			for(i = 0; i < size - 1; i++)
			{
				builder.append(this.innerTags.get(i));
				builder.append(delimiter);
			}
			
			builder.append(this.innerTags.get(i));
		}
		
		return builder.toString();
	}
	
	/**
	 * Builds a string of comma separated tags
	 * @return comma separated string of tags
	 */
	public String commaFormatTag()
	{
		return tagFormatter(",");
	}
	
	/**
	 * Builds a string of space separated tags
	 * @return comma separated string of tags
	 */
	public String spaceFormatTag()
	{		
		return tagFormatter(" ");
	}
	
	/**
	 * Returns a formatted string of tags based on the delimiter
	 * @param delimiter a sequence of characters that separates tags
	 * @return A formatted string of tags
	 */
	public String customFormatTag(String delimiter)
	{
		return tagFormatter(delimiter);
	}
	
	public ArrayList<String> getTags()
	{
	
		return innerTags;
	}
	
	public void setTags(ArrayList<String> tags)
	{
	
		this.innerTags = sort(tags);
	}
	
}
