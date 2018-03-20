package com.example.android.booklisting;

import static android.R.attr.description;

/**
 * Created by dell on 24-06-2017.
 */

public class Book {
    private String mBookName;
    private String mAuthor;

    public Book(String bookName, String authorName) {
        mBookName = bookName;
        mAuthor = authorName;
       }

    public String getBookName() {
        return mBookName;
    }

    public String getAuthorName() {
        return mAuthor;
    }
}
