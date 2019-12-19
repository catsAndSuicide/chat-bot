package chatbot;

import java.io.IOException;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Search;

public class ImageSearcher {
	private String query;

	public ImageSearcher(String query) {
		this.query = query;
	}
	
	public String findImage(){
		try {
	        Customsearch customsearch = new Customsearch.Builder(
	        		new NetHttpTransport(), new JacksonFactory(), null)
	        		.setApplicationName("chat-bot").build();
	        Customsearch.Cse.List list = customsearch.cse().list(query);
	        list.setKey(System.getenv("GOOGLE_CUSTOM_SEARCH_API_KEY"));
	        list.setCx(System.getenv("GOOGLE_CUSTOM_SEARCH_ID"));
	        list.setSearchType("image");
	        list.setImgSize("xlarge");
	        list.setImgType("photo");

	        Search results = list.execute();
	        return results.getItems().get(0).getLink();

	    } catch (IOException e) {
	        System.out.println(e.toString());
	        return null;
	    }
	}
}
