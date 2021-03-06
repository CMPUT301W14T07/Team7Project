package ca.ualberta.team7project.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Class for posting and pulling ThreadModel's to/from the Elastic Search server
 * <p>
 * Reference zjullion at:<br>
 * https://github.com/zjullion/PicPosterComplete
 */
public class ElasticSearchOperation
{

	// change the url to test
	private final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t07/comments/";
	private final String LOG_TAG = "ElasticSearch";

	private Gson GSON = null;
	private Collection<ThreadModel> buffer;

	/**
	 * Constructs the ElasticSearchOperation and sets up the Gson builder
	 */
	public ElasticSearchOperation()
	{

		super();
		constructGson();
	}

	/**
	 * Sends a ThreadModel representing a Topic to the server
	 * <p>
	 * Uses an HttpPut request to create or update the document with index
	 * matching our uniqueID.
	 * <p>
	 * This call should be synchronous, but might not actually block properly when run
	 * <p>
	 * Does nothing if the request fails.
	 * 
	 * @param model a ThreadModel representing a single comment to be json serialized 
	 * 		and pushed to the server
	 * @param refresh an object with an onRefresh() method to call afterwards, can be null 
	 */
	public void pushThreadModel(final ThreadModel model, final ThreadListener refresh)
	{

		if (GSON == null)
		{
			constructGson();
		}

		Thread thread = new Thread()
		{

			@Override
			public void run()
			{

				String id = model.getUniqueID().toString();
				HttpClient client = new DefaultHttpClient();
				HttpPut request = new HttpPut(SERVER_URL + id);

				try
				{
					request.setEntity(new StringEntity(GSON.toJson(model)));
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
					Log.e(LOG_TAG, LOG_TAG + "Error encoding the model: "
							+ e.getMessage().toString());
					return;
				}

				HttpResponse response;
				try
				{
					response = client.execute(request);
					Log.e(LOG_TAG, LOG_TAG + "Response: "
							+ response.getStatusLine().toString());
				} catch (IOException e)
				{
					e.printStackTrace();
					Log.e(LOG_TAG, LOG_TAG + "Error sending the model: "
							+ e.getMessage().toString());
				}

				if (refresh != null)
				{
					refresh.onRefresh();
				}

				// TODO: check http response message

				// TODO: verify if actually pushed
			}
		};

		thread.start();

		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Queries the Elastic Search server for comments by some search method
	 * <p>
	 * The caller provides a full search string.
	 * The simplest search string would be "_search"
	 * @param searchString the full search string, which is passed directly to Elastic Search
	 * @return list of comments/topics that match the search string
	 */
	public Collection<ThreadModel> searchThreads(final String searchString, final String searchEntity)
	{

		if (GSON == null)
		{
			constructGson();
		}

		Thread thread = new Thread()
		{

			@Override
			public void run()
			{

				// TODO Auto-generated method stub
				super.run();

				try
				{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost searchRequest = new HttpPost(SERVER_URL + searchString);
					searchRequest.setHeader("Accept", "application/json");
					
					if(searchEntity != null)
					{
						//add search entity
						try
						{
							searchRequest.setEntity(new StringEntity(searchEntity));
						} catch (UnsupportedEncodingException exception)
						{
							Log.w(LOG_TAG, "Error encoding search query: " + exception.getMessage());
							return;
						}
					}

					HttpResponse response;
					response = httpclient.execute(searchRequest);
					String status = response.getStatusLine().toString();

					System.out.println(status);
					BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
					String output;
					System.err.println("Output from Server -> ");
					String json = "";
					while ((output = br.readLine()) != null)
					{
						System.err.println(output);
						json += output;
					}

					Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<ThreadModel>>(){}.getType();
					ElasticSearchSearchResponse<ThreadModel> esResponse = GSON.fromJson(json, elasticSearchSearchResponseType);
					System.err.println(esResponse);
					System.err.println("" + esResponse.getSources().size());
					buffer = esResponse.getSources();
				} catch (ClientProtocolException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		thread.start();
		// current thread is blocked until the http thread finish what it is
		// doing
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// searchRequest.releaseConnection();

		return buffer;
	}

	/**
	 * Sets up the Gson builder
	 */
	private void constructGson()
	{

		GsonBuilder builder = new GsonBuilder();
		// builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}
}
