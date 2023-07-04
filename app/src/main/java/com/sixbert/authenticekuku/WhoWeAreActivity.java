package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WhoWeAreActivity extends AppCompatActivity {


    String twitter_user_name = "https://twitter.com/Dr_Sixbert";
    WebView mission;
    ImageView eKukuImage, youtube, twitter, fbook, instagram;

    @SuppressLint("SetJavaScriptEnabled")




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whoweare);


        mission = findViewById(R.id.mission);
        eKukuImage = findViewById(R.id.ekukuImage);
        youtube = findViewById(R.id.youtube);
        twitter = findViewById(R.id.tweetter);
        fbook = findViewById(R.id.fbook);
        instagram = findViewById(R.id.instagram);

        mission.getSettings().setJavaScriptEnabled(true);
        mission.setWebViewClient(new WebViewClient());
        mission.loadData("<html><h1>Kuhusu eKuku</h1>" +
                "<h2></h2>" +
                "<p>eKuku ni mfumo wa kisasa na wa kidigitali unaowawezesha wazalishaji na " +
                "walaji wa kuku na bidhaa zake kupata taarifa za masoko za bidhaa husika kwa kutumia simu " +
                "janja, muda wowote na mahala popote nchini Tanzania. eKuku imeundwa kutokana" +
                " na utafiti makini kwamba mnyororo wa thamani wa ufugaji wa kuku " +
                "umegubikwa na uhaba wa taarifa za masoko ambapo wafugaji hawafahamu" +
                " kwa uhakika aina au kiasi cha bidhaa kinachohitajika na wanunuzi " +
                "katika muda halisi. Kwa upande mwingine, wanunuzi wanakuwa hawana " +
                "taarifa za kiasi cha kuku au mazao yake kilichopo sokoni, wauzaji " +
                "wako mahala gani na bei ni kiasi gani katika muda halisi. " +
                "Matokeo yake, wafugaji wa kuku hujikuta katika sintofahamu ya kuuza kuku " +
                "na bidhaa zake kwa hasara au faida kidogo, au walaji hujikuta " +
                "wakilipa zaidi  kuliko inavopaswa. eKuku ni jawabu sahihi la kutatua tatizo hili \n</p>+" +

                "<h2>Dira Yetu</h2>"  +
                "<p>Kuunda mifumo rahisi ya kidigitali yenye kurahisisha upatikanaji wa " +
                "taarifa za masoko na elimu kwa wazalishaji na wanunuzi wa kuku na bidhaaa zake nchini " +
                "Tanzania. </p>"+

                "<h2>Dhima Yetu</h2>" +

                "<p> Kukuza fursa kwa wafugaji wa na walaji wa kuku na bidhaa zake kwa " +
                "kuunda mfumo wa kidigitali unaowezesha upatikanaji na uchakataji wa taarifa za masoko ambayo ni ya uhakika, salama, " +
                "yenye uwazi na katika muda halisi ili kuchochea ujasiriamali katika " +
                "tasnia ya ufugaji wa kuku nchini Tanzania. </p>"+
                "</html>","text/html", "UTF-8");


        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCALr7SVQPfcPzT_EvAmDYew")));

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                     "https://twitter.com/Dr_Sixbert")));
            }catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://twitter.com/Dr_Sixbert")));
            }

            }
        });

        fbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}