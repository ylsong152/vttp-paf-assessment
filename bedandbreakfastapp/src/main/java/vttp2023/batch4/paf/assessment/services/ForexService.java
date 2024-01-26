package vttp2023.batch4.paf.assessment.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ForexService {

	// TODO: Task 5 
	public float convert(String from, String to, float amount) {

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.frankfurter.app/latest?from=AUD";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		try (InputStream is = new ByteArrayInputStream(response.getBody().getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject data = reader.readObject();
			

		} catch (IOException e) {
			e.printStackTrace();
		}


		return -1000f;
	}
}
