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


public class GumboroFragment extends Fragment {
    public String fileName = "gumboro.html";
    private WebView webView;

    public GumboroFragment(){

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_gumboro, container, false);

        webView = fragView.findViewById(R.id.gumboro);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/raw/gumboro.html");

        return fragView;
    }



}