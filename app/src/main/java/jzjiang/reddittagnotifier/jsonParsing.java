import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class jsonParsing {
	JsonArray posts; // A JsonArray of of JsonObject thread files.
	HashSet<String> old_id = new HashSet<String>(); // id of the threads that we have checked.
	String sURL;
	String keyword;

	jsonParsing(String subreddit, String key) {
		sURL = "http://www.reddit.com/r/" + subreddit + ".json";
		keyword = key;
	}

	// update() updates the info in the jsonfile
	// This should be called every 2 seconds through check.
	void update() throws IOException {
		URL url = new URL(sURL);
		System.setProperty("http.agent", "HipHopHacks"); // unique user-agent.

		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader(
				(java.io.InputStream) request.getContent()));
		JsonObject rootobj = root.getAsJsonObject();
		request.disconnect();

		JsonObject data = rootobj.getAsJsonObject("data");
		posts = data.get("children").getAsJsonArray();
	}

	// check() checks if there are any new threads and returns the url of a new
	// thread.
	String check() throws IOException {
		JsonObject post;
		String url;
		String id;
		this.update();
		for (JsonElement postelement : posts) {
			post = postelement.getAsJsonObject().get("data").getAsJsonObject();
			id = post.get("id").getAsString();
			url = post.get("permalink").getAsString();
			
			if (!old_id.contains(id) && (url.indexOf(this.keyword) >= 0)){
				old_id.add(id);
				return url;
			}
		}
		return "";
	}

}
