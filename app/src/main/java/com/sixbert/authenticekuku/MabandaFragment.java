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


public class MabandaFragment extends Fragment {
ImageView imageView;

    public MabandaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View rootView = inflater.inflate(R.layout.fragment_mabanda, container, false);
      imageView = rootView.findViewById(R.id.sheds);

        WebView webView = rootView.findViewById(R.id.poultry_shed);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/raw/mabanda.html");

        return rootView;
    }



}