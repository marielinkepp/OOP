package gh;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


public class GhAPIUserDataGetter {

    public static void main(String[] args) throws IOException, InterruptedException {
        /*Using Github API fetch the following information about your profile:

    Your Github login name
    URL to your Github profile
    Number of public repositories
    Date when your Github profile was created
    For this exercise your could either use either Gson or Jackson, whichever you prefer. However, for practice purposes, it might be useful to try Gson, since we have already used Jackson in an example.*/

        // Create the mapper that will transform XML into our Java object
        //ObjectMapper mapper = new XmlMapper();
        // Disable feature that fails on unknown properties
        // This is needed because we dont need all of the properties
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Create HTTP client
        var client = HttpClient.newHttpClient();

        // Create a request that we will send over the network
        var request = HttpRequest.newBuilder()
                // URL that we will fetch the data from
                .uri(URI.create("https://api.github.com/users/marielinkepp"))
                // We have to set this header for this call, otherwise
                // Estonian Weather Service will not send us the response
                .header("User-Agent", "Mozilla/5.0")
                // HTTP method that specifies that we want to get the information
                .GET()
                .build();

        // Send the request over the network
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        //System.out.println(response.body());

        Gson gson = new Gson();
        // Use JsonReader to read the JSON
        try (JsonReader jsonReader = new JsonReader( new InputStreamReader(response.body()))) {
            // Replace YourClass with the class that represents the structure of your JSON
            Kasutaja kasutaja = gson.fromJson(jsonReader, Kasutaja.class);
            System.out.println(kasutaja.toString());

        }
    }
}
