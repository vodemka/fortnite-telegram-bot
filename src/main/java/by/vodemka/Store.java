package by.vodemka;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;

public class Store {
    public static Model getStore(Model model, String type) {
        String result = "";
        try {
            URL url;
            URLConnection uc;
            StringBuilder parsedContentFromUrl = new StringBuilder();
            String urlString = "https://fortnite-api.theapinetwork.com/items/random?authorization=" + Bot.getToken("apiFortniteKey");
            url = new URL(urlString);
            uc = url.openConnection();
            uc.connect();
            uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.getInputStream();
            BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
            int ch;
            while ((ch = in.read()) != -1) {
                parsedContentFromUrl.append((char) ch);
            }
            result = parsedContentFromUrl.toString();

            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            switch (type) {
                case "Outfit":
                default: {
                    JSONObject outfit = data.getJSONObject("outfit");
                    JSONObject images = outfit.getJSONObject("images");
                    model.setName(outfit.getString("name"));
                    model.setBackground(images.getString("background"));
                }
                break;

                case "Pickaxe": {
                    JSONObject pickaxe = data.getJSONObject("pickaxe");
                    JSONObject images = pickaxe.getJSONObject("images");
                    model.setName(pickaxe.getString("name"));
                    model.setBackground(images.getString("background"));
                }
                break;

                case "Emote": {
                    JSONObject emote = data.getJSONObject("emote");
                    JSONObject images = emote.getJSONObject("images");
                    model.setName(emote.getString("name"));
                    model.setBackground(images.getString("background"));
                }
                break;
            }
            model.setDate(LocalTime.now().toString());
            model.setType(type);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
