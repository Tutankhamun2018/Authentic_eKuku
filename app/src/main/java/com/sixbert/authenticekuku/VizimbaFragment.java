package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


public class VizimbaFragment extends Fragment {
ImageView imageView;

    public VizimbaFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        //final String source = "http://google.com";
        View rootView = inflater.inflate(R.layout.fragment_vizimba, container, false);
        imageView = rootView.findViewById(R.id.cages_image);

        WebView webView = rootView.findViewById(R.id.batteryCages);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/raw/vizimba.html");


        return rootView;
    }


}