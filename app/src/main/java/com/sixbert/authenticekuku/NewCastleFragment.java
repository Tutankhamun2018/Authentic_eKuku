package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class NewCastleFragment extends Fragment {
    public String fileName = "newCastle.html";
    private WebView webView = null;

    public NewCastleFragment(){

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //final String source = "http://google.com";
        View fragView = inflater.inflate(R.layout.fragment_new_castle, container, false);

        WebView webView = fragView.findViewById(R.id.newcastle);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/raw/newcastle.html");

        return fragView;
    }



}