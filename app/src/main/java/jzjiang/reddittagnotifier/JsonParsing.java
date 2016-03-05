package jzjiang.reddittagnotifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParsing {
	JsonArray posts; // A JsonArray of of JsonObject thread files.
	HashSet<String> old_id = new HashSet<String>(); // id of the threads that we have checked.
    LinkedList<String[]> total_thread_list = new LinkedList<String[]>();
	String sURL;
	String keyword;
	JsonParsing(String subreddit, String key) {
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
		try {
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader(
					(java.io.InputStream) request.getContent()));
			JsonObject rootobj = root.getAsJsonObject();
			request.disconnect();
			JsonObject data = rootobj.getAsJsonObject("data");
			posts = data.get("children").getAsJsonArray();
		} catch (Exception e) {
		}
	}
	// check() checks if there are any new threads and returns an LinkedList
	// of an array with [TYPE, ARTIST, TITLE, UPVOTES, URL]
	@SuppressWarnings("null")
	LinkedList<String[]> check() {
		JsonObject post;
		String url;
		String id;
		String title;
		String upvotes;
        LinkedList<String[]> thread_list = new LinkedList<String[]>();
		try {
			for (JsonElement postelement : posts) {
				post = postelement.getAsJsonObject().get("data")
						.getAsJsonObject();
				id = post.get("id").getAsString();
				url = post.get("permalink").getAsString();
				title = post.get("title").getAsString();
				upvotes = post.get("score").getAsString();
				if (!old_id.contains(id)
						&& (url.split("/")[5].split("_")[0].equals(keyword))) {
					old_id.add(id);
					String[] temp = { title.split("] ")[0] + "]",
							title.split("] ")[1].split(" - ")[0],
							title.split("] ")[1].split(" - ")[1], upvotes, url };
					thread_list.add(temp);
				}
			}
		} catch (Exception e) {
			return thread_list;
		}
		return thread_list;
	}

    // process() ensures thread_list stays under 10.
    void process(LinkedList<String[]> thread_list) {
        total_thread_list.addAll(thread_list);
        while (total_thread_list.size() > 10) {
            total_thread_list.remove();
        }
    }
}