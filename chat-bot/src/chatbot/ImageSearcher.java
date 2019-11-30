package chatbot;

import java.io.IOException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result.Image;
import com.google.api.services.customsearch.model.Search;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;

public class ImageSearcher {
	private String query;

	public ImageSearcher(String query) {
		this.query = query;
	}
	
	public String findImage(){
		try {
	        Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
	        Customsearch.Cse.List list = customsearch.cse().list(query);
	        list.setKey("key");
	        list.setCx("id");
	        list.setSearchType("image");
	        list.setImgSize("xlarge");
	        list.setImgType("photo");

	        Search results = list.execute();
//	        List<Image> images = new ArrayList<Image>();
//
//	        for (Result result : results.getItems()) {
//	            Image image = new Image();
//	            image.setSource("google");
//	            image.setUrl(result.getLink());
//	            image.setTitle(result.getTitle());
//	            images.add(image);
//	        }
	        return results.getItems().get(0).getLink();

	    } catch (IOException e) {
	        System.out.println("Error");
	        return null;
	    }
	}
}
