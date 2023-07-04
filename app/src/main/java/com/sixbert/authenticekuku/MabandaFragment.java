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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadData("<html><h1>Mabanda ya Kuku</h1>" +
                "<h2>Utangulizi</h2>" +
                "<p>Ujenzi wa mabanda ya kufugia kuku ni shughuli muhimu ambayo inaweza kuwa ghali. " +
                "Kwa hiyo ni vema kuzingatia masuala kadha wa kadha kwani mabanda ndio masingi" +
                " wa ufugaji wa kuku. Mahala sahihi pa ujenzi w abanda/mabanda ni pale ambapo " +
                "hapatuamishi maji kwa urahisi , palipo tambarare n ani karibu na chanzo cha" +
                " maji ya kunywa. Pia ni sharti pawe na mzunguko mzuri wa hewa ila pasiwe na " +
                "upepo mkali. Vinginevyo, vizuia upepo mfano miti mirefu vitahitajika. " +
                "Kutokana na harufu kali zitokanazo na kinyesi cha kuku kwenye mabanda," +
                " yafaa mabanda yawe uendako mkondo wa upepo, mbali na makazi au nyumba ya kuishi." +
                "Iwapo kuna Zaidi y abanda moja, basi mabanda yawe umbali wa mita 10-15," +
                " kupunguza uwezekano wa kusambaza magonjwa. Mabanda ya kukuzia vifaranga " +
                "yapaswa kuwa umbali wa mita 30 kutoka au Zaidi, kutoka yalipo mabanda mengine, " +
                "wakati huo huo yakiwa na vyanzo vya chakula na maji vinavyojitegemea. </p>"+

                "<h2>Mahitaji ya kimazingira</h2>" +
                "<p>Katika nyanda za joto, changamoto kubwa ni kuhakikisha kuwa mabanda ya kuku " +
                "yana joto na unyevuwa hewa wa wastani. Katika maeneo ya nyanda za juu, aghalab ni " +
                "vema kudhibiti hali ya baridi na upepo. Unyevunyevu ukiwa wa kiwango cha chini " +
                "(mfano katika maeneo kame) husababisha vumbi wakati kiasi kikubwa cha unyevunyevu," +
                " vikiambatana na joto la juu husababisha msongo na huweza kusababisha vifo. \n" +
                "Mabanda ya kuku yafaa pia kudhibiti wezi pamoja na wanyama waharibifu wanaoweza " +
                "kuwashambulia kuku mfano vicheche, vipanga, mapaka, mapanya na ndege wengine " +
                "wanaoweza kusambaza magonjwa\n </p>"+

                "<h2>Mwanga</h2>" +
                "<p>Mabanda ni sharti yawe na mwanga wa kutosha na uwezekano wa kuwa na mwanga " +
                "stahiki pale unapohitajika. Kwa kuku wa nyama, mwanga unahitajika kwa masaa 24, " +
                "wakati kwa kuku wanaotaga, masaa 14 ya mwanga yatatosha kabisa.  </p>"+

                "<h2>Mambo Muhimu katika Ujenzi wa mabanda</h2>" +
                "<p>1.\tKatika nyanda za joto ambako ubaridi utahitajika muda mwingi, ukuta wa " +
                "chini ni muhimu kwa ajili ya kuzuia jua, ambako juu yake patakuwa wazi" +
                " (kwa kuta zote nne) ili kuruhusu mzunguko wa hewa. Pazia la jafafa " +
                "lahitajika kuwepo ili litumike kuwakinga kuku dhidi ya upepo mkali au jua. " +
                "Upana w abanda usizidi mita 9 ili kuruhusu mzunguko sahihi wa hewa.</p>"+

                "<p>Vipengele Muhimu:" +
                "<ul>"+
                "<li>\tSakafu – sakafu ya saruji na mchanga inapendekezwa itumike kwa sababu " +
                "ni rahisi kusafisha, inadumu muda mrefu na pia inadhibiti mapanya " +
                "(inazuia mapanya kutengeneza mashimo yao). Hata hivyo, changarawe au " +
                "udongo ulishindiliwa vema vyaweza kutumika katika kutengeza sakafu</li>"+
                "<li>\tPaa – Paa laweza kuezekwa kwa makuti au mabati. Paa iliyoezekwa kwa mabati" +
                " ni nzuri Zaidi kwani ni rahisi kutunza na kusafisha, kuliko paa la makuti. " +
                "Pia paa la mabati linadumu muda mrefu. Hata hivyo, paa la makuti linafaa " +
                "katika uwanda wa joto kwani hupunguza joto kwa kiwango kikubwa. </li>"+
                "</ul>"+


                "</html>","text/html", "UTF-8");


        //webView.loadUrl("file://android_assets/"+ fileName);

        return rootView;
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