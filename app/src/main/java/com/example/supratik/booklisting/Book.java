package com.example.supratik.booklisting;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private String mTitle;
    private JSONArray mAuthors; // list of authors(string).
    private String mThumbnilLinkString, mInfoLink,mCountry,mSale;

    private double mPrice;

//    constructors
//    public Book(String title, JSONArray authors, String infoLink,String thumbnilLInkString,double price,String country){
//        mTitle = title;
//        mAuthors= authors;
//        mImageResourceId = imageResourceId;
//        mThumbnilLinkString = thumbnilLInkString;
//        mPrice = price;
//        mCountry = country;
//    }
    public Book(String title, JSONArray authors, String infoLink, String thumbnilLInkString,double price,String country,String sale) {
        mTitle = title;
        mAuthors = authors;
        mInfoLink = infoLink;
        mThumbnilLinkString = thumbnilLInkString;
        mPrice = price;
        mCountry = country;
        mSale = sale;
    }

        //Methods


    public String getCountry() {
        return mCountry;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getSale() {
        return mSale;
    }

    public String getTitle () {
            return mTitle;
        }

        public JSONArray getAuthors(){
            return mAuthors;
        }


        public String getInfoLink () {
            return mInfoLink;
        }

        public String getThumbnailLinkString () {
            return mThumbnilLinkString;
        }

//        public String getPrice () {
//            return String.valueOf(mPrice);
//        }

//    public String getCountry(){
//        return mCountry;
//    }

    }
