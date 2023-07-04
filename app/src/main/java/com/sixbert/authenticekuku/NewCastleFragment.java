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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadData("<html><h1>Ugonjwa wa New Castle (Mdondo)</h1>" +
                "<h2>utangulizi</h2>" +
"<p>Ugonjwa Mdondo au New Castle ni ugonjwa unaowapata jamii ya ndege. Husababishwa na virusi aina ya  <u>paramyxovirus</u>.</p>"+

"<p>Huenea kwa njia zifuatazo:" +
"<ul>"+
    "<li>Mayai ya kuku mgonjwa</li>"+
    "<li>Kugusana baina ya kuku wazima na wagonjwa</li>"+
   " <li>Kutumia maji yenye virusi</li>"+
                "<li>Wakati wa kutotolesha vifaranga</li>"+
                "<li>Kwa njia ya hewa</li>"+
                "</ul>"+
"<p>Dalili za New Castle (Mdondo):"+
"<ol>"+
                "<li>Vifo vya ghafla</li>"+
                "<li>Kutoa udenda mdomoni</li>"+
                "<li>Kukosa hamu ya kula</li>"+
                "<li>Kuharisha kinyesi cheupe na kijani</li>"+
                "<li>Kupumua kwa shida</li>"+
                "<li>Kupunguza utagaji</li>"+
                        "</ol>"+
                "</html>","text/html", "UTF-8");


        //webView.loadUrl("file://android_assets/"+ fileName);

        return fragView;
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/

}