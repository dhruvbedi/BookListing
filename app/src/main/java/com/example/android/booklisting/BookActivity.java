package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private String BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter bookAdapter;
    private TextView emptyTextView;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        String text = getIntent().getStringExtra("extra");
        BOOK_URL = BOOK_URL + text;

        ListView listView = (ListView) findViewById(R.id.listView);
        bookAdapter = new BookAdapter(this,new ArrayList<Book>());

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(bookAdapter);

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkinfo = CM.getActiveNetworkInfo();
        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        if (networkinfo != null && networkinfo.isConnected()) {
            BookAsyncTask task = new BookAsyncTask();
            task.execute(BOOK_URL);
        } else {
            progressbar.setVisibility(View.GONE);
            emptyTextView.setText("Internet Connection not found");
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    private class BookAsyncTask extends AsyncTask<String,Void,List<Book>> {
        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null)
                return null;
            List<Book> books = QueryUtils.fetchBookData(urls[0]);
            if(books == null)
                return null;
            else
                return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            bookAdapter.clear();
            progressbar = (ProgressBar) findViewById(R.id.progressBar);
            if (books != null && !books.isEmpty()) {
                progressbar.setVisibility(View.INVISIBLE);
                bookAdapter.addAll(books);
            }
            else{
                progressbar.setVisibility(View.INVISIBLE);
                emptyTextView.setText("NO RESULT FOUND");
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
