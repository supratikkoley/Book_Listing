package com.example.supratik.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class BookUtils {

    public static ArrayList<Book> extractBookList(String Url) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonResponse = "";
        URL url = createUrl(Url);

        ArrayList<Book> bookLists = new ArrayList<Book>();

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //create the root object of the whole json response
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray items = root.getJSONArray("items");

            JSONObject volumeInfo, saleInfo, retailPrice;
            String title, thumbnail, infoLink, country, sale;
//            int imageResourceId = 0;
            JSONArray authors;
            double amount;
            Log.i("length", String.valueOf(items.length()));
            for (int i = 0; i < items.length(); i++) {
                volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");

                saleInfo = items.getJSONObject(i).getJSONObject("saleInfo");

                title = volumeInfo.getString("title");

                authors = volumeInfo.getJSONArray("authors");

                thumbnail = volumeInfo.getJSONObject("imageLinks")
                        .getString("smallThumbnail");
                infoLink = volumeInfo.getString("infoLink");

                sale = saleInfo.getString("saleability");
//                retailPrice = saleInfo.getJSONObject("retailPrice");
//                amount = retailPrice.getDouble("amount");
                Log.i("sale", String.valueOf(sale));
                if (sale.equals("FOR_SALE")) {

//                    retailPrice = saleInfo.getJSONObject("retailPrice");
                    Log.i("string","hello");

                    amount = saleInfo.getJSONObject("retailPrice").getDouble("amount");

                    country = saleInfo.getString("country");

                    bookLists.add(new Book(title, authors, infoLink, thumbnail,amount,country,sale));

                Log.i("amount", String.valueOf(amount));
                }
                else{
                    amount=0.0;
                    country = saleInfo.getString("country");
                    bookLists.add(new Book(title, authors, infoLink, thumbnail,amount,country,sale));
                    Log.i("amount", String.valueOf(amount));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookLists;
    }


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            //Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null)
            return null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

//    public int getImageResourceId(String infoLink) {
//
//    }
}