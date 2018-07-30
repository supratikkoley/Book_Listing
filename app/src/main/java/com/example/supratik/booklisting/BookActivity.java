package com.example.supratik.booklisting;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class BookActivity extends AppCompatActivity {

    private BookAdapter mAdapter;
    private ArrayList<Book> booklist;


    private String BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    //"https://www.googleapis.com/books/v1/volumes?q="+"&maxResults=20";

    private TextView mEmptyView;
//    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("query");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("query");
        }

        ProgressBar progressBar = findViewById(R.id.progressbar);

        mEmptyView = findViewById(R.id.emptyView);

        BOOKS_REQUEST_URL = BOOKS_REQUEST_URL + newString + "&maxResults=20";

//        booklist = BookUtils.extractBookList(BOOKS_REQUEST_URL);
//
//        ListView listView = findViewById(R.id.list);
//
//        mAdapter = new BookAdapter(this, new ArrayList<Book>());
//
//        listView.setAdapter(mAdapter);
//        BookAsyncTask task = new BookAsyncTask();
//        task.execute(BOOKS_REQUEST_URL);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (isConnected) {

            BookAsyncTask task = new BookAsyncTask();
            task.execute(BOOKS_REQUEST_URL);
        } else {
            progressBar.setVisibility(View.GONE);
            mEmptyView.setText(R.string.no_internet);
        }
    }

    public Void updateUI(ArrayList<Book> booklist) {

        // booklist = BookUtils.extractBookList(BOOKS_REQUEST_URL);


        ListView listView = findViewById(R.id.list);

        mAdapter = new BookAdapter(this, booklist);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mAdapter.getItem(position);
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(book.getInfoLink()));
                startActivity(i);
            }
        });


        return null;
    }

    public class BookAsyncTask extends AsyncTask<String, Void, ArrayList<Book>> {


        @Override
        protected ArrayList<Book> doInBackground(String... urls) {
            booklist = BookUtils.extractBookList(urls[0]);
            return booklist;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {

            ProgressBar progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);

            if (books != null && !books.isEmpty()) {

                updateUI(books);
            } else {
                mEmptyView.setText(R.string.no_book);
            }

        }

    }

}
