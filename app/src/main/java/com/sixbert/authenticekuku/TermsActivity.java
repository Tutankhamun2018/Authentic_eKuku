package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TermsActivity extends AppCompatActivity {

    WebView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_terms);

        terms = findViewById(R.id.tcWeb);

        terms.getSettings().setJavaScriptEnabled(true);
        terms.setWebViewClient(new WebViewClient());
        terms.loadData("<html><h1>Vigezo na Masharti</h1>" +
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

    }
}