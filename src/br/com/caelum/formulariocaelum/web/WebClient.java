package br.com.caelum.formulariocaelum.web;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.inject.Inject;

import br.com.caelum.formulariocaelum.enums.Extras;


public class WebClient {
	
	@Inject
	private DefaultHttpClient httpClient;
	
	public WebClient() {
		//Guice constructor
	}
	
	public String post(String json) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(Extras.mobileURL.getId());
		post.setEntity(new StringEntity(json));
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");
		
		HttpResponse response = this.httpClient.execute(post);
		String jsonResposta = EntityUtils.toString(response.getEntity());
		
		return jsonResposta;
	}
	
}
