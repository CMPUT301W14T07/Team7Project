package ca.ualberta.team7project.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.TopicListModel;
import ca.ualberta.team7project.models.TopicModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Basic ElasticSearchOperation for posting TopicModel to server and pulling
 * TopicModel from server Reference zjullion at
 * https://github.com/zjullion/PicPosterComplete
 */

public class ElasticSearchOperation
{

	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/pic_poster/";
	public static final String LOG_TAG = "ElasticSearch";

	private static Gson GSON = null;

	/**
	 * Sends a TopicModel to the server. Does nothing if the request fails.
	 * 
	 * @param model
	 *            a TopicModel
	 */

	public static void pushPicPostModel(final TopicModel model)
	{

		if (GSON == null)
			constructGson();

		Thread thread = new Thread()
		{

			@Override
			public void run()
			{

				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL);

				try
				{
					request.setEntity(new StringEntity(GSON.toJson(model)));
				} catch (UnsupportedEncodingException exception)
				{
					Log.w(LOG_TAG,
							"Error encoding PicPostModel: "
									+ exception.getMessage());
					return;
				}

				HttpResponse response;
				try
				{
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
				} catch (IOException exception)
				{
					Log.w(LOG_TAG,
							"Error sending PicPostModel: "
									+ exception.getMessage());
				}
			}
		};

		thread.start();
	}

	/**
	 * This method has big issue, not finished. it has to be checked line by
	 * line
	 * 
	 * @param searchTerm
	 * @param model
	 * @param activity
	 */
	public static void searchForPicPostModels(final String searchTerm,
			final TopicListModel model, final MainActivity activity)
	{

		if (GSON == null)
			constructGson();

		Thread thread = new Thread()
		{

			@Override
			public void run()
			{

				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "_search");
				String query = "{\"query\": {\"query_string\": {\"default_field\": \"text\",\"query\": \"*"
						+ searchTerm + "*\"}}}";
				String responseJson = "";

				try
				{
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception)
				{
					Log.w(LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}

				try
				{
					HttpResponse response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());

					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent()));

					String output = reader.readLine();
					while (output != null)
					{
						responseJson += output;
						output = reader.readLine();
					}
				} catch (IOException exception)
				{
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}

				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<PicPostModel>>()
				{
				}.getType();
				final ElasticSearchSearchResponse<PicPostModel> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);

				Runnable updateModel = new Runnable()
				{

					@Override
					public void run()
					{

						model.clear();
						model.addPicPostCollection(returnedData.getSources());
					}
				};

				activity.runOnUiThread(updateModel);
			}
		};

		thread.start();
	}

	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for
	 * Bitmaps.
	 */
	private static void constructGson()
	{

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}

}
