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
        webView.loadData("<html><h1>Vizimba (Cages)</h1>" +
                "<h2>Utangulizi</h2>" +
                "<p>Vizimba ni mfumo wa ufugaji kuku ambapo kuku wanatunzwa kwenye vizimba muda wote. " +
                "Vizimba hivi vimeshonwa pamoja vikiwa na ukubwa sawa. Hujulikana pia kama " +
                "  <u>battery cages</u>.</p>. Vizimba ni maarufu kwa kufugia kuku wa mayai."+

                "<p>Aina za vizimba:</p>" +
                "<p> Vizimba vimeaininshwa kutokana na vigezo kadha wa kadha kama ifuatavyo</p>"+
                "<ul>"+
                "<li>Kulingana na idadi ya kuku katika kizimba</li>"+
                "<li>Kulingana na idadi ya safu za vizimba</li>"+
                " <li>Kutokana na mpangilio wa vizimba katika safu</li>"+
                "<li>Kutokana na aina ya kuku wanaofugwa katika vizimba</li>"+
                 "</ul>"+
                "<p>Aina za vizimba kutokana uwezo wake (idadi ya kuku)</p>" +


                "<li>Vizimba vyenye sehemu nyingi: "+
                "Hivi vyaweza kubeba kuku wawili hadi 10. Mara nyingi hubeba kati ya kuku " +
                "3 hadi 4 kwa kila kizimba</li>"+


                "<li>Vizimba vya halaiki:"+

                "Kwenye aina hii, kuku wanakaa wengi zaidi kuliko aina ya kwanza " +
                "zaidi ya kuku 11 kwa kila kizimba</li>"+


                "<p>Aina za vizimba kutokana na idadi ya safu </p>"+

                "<ul>"+
                "<li> Vizimba vilivyoko kwenye safu moja </li>"+

                "<li>Vizimba vyenye safu mbili (tazama picha)</li>"+

                "<li>Vizimba vyenye safu tatu (Tazama picha) </li>"+

                "<li>Vizimba vyenye safu nne (Tazama picha)</li>"+
                "</ul>"+

                "<p>Aina za vizimba kutokana na Aina ya kuku wafugwao </p>"+

                "<ul>"+
                "<li> Vizimba vya kuatamia vifaranga. Hivi ni muhimu kwa " +
                "ajili ya kutunzia vifaranga wa kuanzia siku moja hadi wiki 8 </li>"+

                "<li>Vizimba kwa ajili ya kuku wanaokua (growers). Hii ni kwa ajili ya " +
                "kulelea kuku kuanzia wiki 9 -18 wanapoanza kutaga</li>"+


                "<li>Vizimba kwa ajili ya kuku wanaotaga. Vizimba aina hii sakafu " +
                "yake ina mteremko kiasi kuelekea mbele kwa ajili ya kurahisisha " +
                "ukusanyaji wa mayai </li>"+
                "</ul>"+


                "<p>Faida za mfumo wa vizimba</p>" +
                "<ul>"+
                "<li>Huboresha afya ya kuku. Mfumo wa vizimba umetengenezwa " +
                "kiasi kwamba kinyesi cha kuku hudondoka chini moja kwa moja," +
                " kwa hiyo ni rahisi kusafisha na kupunguza uwezekanao wa kusababisha magonjwa" +
                "</li>"+
                " <li>Huongeza utagaji kwa kuku wa mayai, kutokana na kuwa kuku wako na afya njema " +
                "wakati wote</li>"+
                "<li>Hupunguza gharama za nguvu kazi. Hakuna haja ya kusafisha vifaa vya kulishia " +
                "au kunyweshea kila siku kama ilivyo mfumo wa kawaida. Na pia kuku wanakuwa mbali " +
                "na yalipo maji au chakula, hivyo siyo rahisi kufikia na kuchafua</li>"+
                 "<li>Hubeba kuku wengi zaidi katika sehemu ndogo. Hupunguza gharama za ujenzi wa banda</li>"+
                 "<li>Hurahisisha uwekaji wa kumbu kumbu sahihi. Ni rahisi kuwapanga kuku kulingana na umri au uwezo wake" +
                 "Ni rahisi pia kuwaona kuku yeyote mwenye hitilafu kama ugonjwa au utagaji hafifu na kuwaondoa</li>"+
                "<li>Husaidia kuku kutaga mayai masafi. Mayai yenye kinyesi hayapendezi machoni pa wateja. " +
                "Pia kinyesi cha kuku kwenye mayai chaweza kuwa na bakteria au virusi ambao ni hatari kwa watumiaji</li>"+
                "<li>Huzuia upotevu wa vyakula na maji. Hii hutokana na kuwa mfumo wa vizimba una vifaa " +
                        "mahususi kwa ajili ya chakula na maji. Ni rahisi pia kufahamu mahitaji ya kuku kila siku </li>" +
                "</ul>"+

                "<p>Hasara za mfumo wa vizimba </p>" +
                "<ul>"+
                "<li>Kuku hukosa mazoezi ya viungo kutokana na kukosa nafasi </li>"+
                " <li>Kupata mfadhaiko kutokana na kukosa nafasi ya kuatamia yai linapotagwa</li>"+
                "<li>Kukosa fursa ya kuoga vumbi</li>"+
                "<li>Uwezekano mkubwa kupata vidonda miguuni</li>"+
                "</ul>"+

                "<p>Dondoo wakati wa kuchagua mfumo wa vizimba</p>" +
                "<ul>"+
                "<li>Zingatia ukubwa wa vizimba ili kuwapatia kuku nafasi ya kutosha wawapo nadni ya kizimba </li>"+
                " <li>Chagua mfumo wa vizimba wenye vifaa imara vya kulishia </li>"+
                "<li>Hakikisha mfumo wa vizimba una mfumo wa kunyweshea imara, usiovuja</li>"+
                "<li>Hakikisha vizimba vimetengenezwa kwa wavu imara, usiopata kutu</li>"+
                "</ul>"+



                "</html>","text/html", "UTF-8");


        //webView.loadUrl("file://android_assets/"+ fileName);

        return rootView;
    }


}