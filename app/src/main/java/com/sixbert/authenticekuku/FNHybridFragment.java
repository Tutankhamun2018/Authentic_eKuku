package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class FNHybridFragment extends Fragment {


    public FNHybridFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fnView = inflater.inflate(R.layout.fragment_f_n_hybrid, container, false);
        WebView webView = fnView.findViewById(R.id.poultry_shed);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadData("<html><h1>Chakula na Lishe</h1>"+
                "</html>","text/html", "UTF-8");


        //webView.loadUrl("file://android_assets/"+ fileName);

        return fnView;
    }


}