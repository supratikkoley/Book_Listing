package com.example.supratik.booklisting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Book> booklist;
    private BookAdapter mAdapter;
    private static final String BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=20";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent bookListIntent = new Intent(MainActivity.this,BookActivity.class);
//                startActivity(bookListIntent);
                MainAsyncTask task = new MainAsyncTask();
                task.execute();
            }
        });
    }

    public Void updateUI(ArrayList<Book> booklist) {

        // booklist = BookUtils.extractBookList(BOOKS_REQUEST_URL);

        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        startActivity(intent);

        ListView listView = findViewById(R.id.list);

        mAdapter = new BookAdapter(this, booklist);

        listView.setAdapter(mAdapter);


        return null;
    }

    public class MainAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            EditText editText = findViewById(R.id.searchBar);
            String query = editText.getText().toString();

            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
            return null;
        }
    }
}