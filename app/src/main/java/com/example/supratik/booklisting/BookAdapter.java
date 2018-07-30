package com.example.supratik.booklisting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends ArrayAdapter<Book> {

    //constructors
    public BookAdapter(Context context, ArrayList<Book> resource) {
        super(context, 0,resource);
    }

//    private ImageView thumbnail;

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

             Book currentBook = getItem(position);

             ImageView thumbnail = listItemView.findViewById(R.id.thumbnail);

/**          download the corresponding thumbnail of the book and hook it to the listItem.*/
            if(currentBook != null){
                DownloadImageTask task = new DownloadImageTask(thumbnail);
                task.execute(currentBook.getThumbnailLinkString());
            }

            TextView title = listItemView.findViewById(R.id.title);
            title.setText(currentBook.getTitle());

            if(currentBook.getSale().equals("FOR_SALE")) {
                TextView amount = listItemView.findViewById(R.id.price);
                amount.setText(setCurrency(currentBook.getPrice(), currentBook.getCountry().toLowerCase()));
            }
//            else{
//                TextView amount = listItemView.findViewById(R.id.price);
//                amount.setText("");
//            }
            TextView authors = listItemView.findViewById(R.id.authors);
            try {
                authors.setText(getAuthorName((currentBook.getAuthors())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listItemView;
    }

    public String getAuthorName(JSONArray authors) throws JSONException {

        StringBuilder AuthorName = new StringBuilder();

        int c = 0;

        for(int i=0; i<authors.length(); i++){
            if(i != authors.length()-1) {
                AuthorName.append(authors.getString(i)+" and ");
            }
            else {
                AuthorName.append(authors.getString(i));
            }
        }

        return AuthorName.toString();
    }

    public String setCurrency(double price,String country){
        String Price = NumberFormat.getCurrencyInstance(new Locale("en",country)).format(price);

        return Price;
    }

    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{

        ImageView thumbnail;

        public DownloadImageTask(ImageView bmImage) {
           thumbnail = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            thumbnail.setImageBitmap(result);
        }
    }
}