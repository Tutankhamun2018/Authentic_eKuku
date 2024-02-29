package com.sixbert.authenticekuku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

public class SubscriptionsActivity extends AppCompatActivity {
    private BillingClient billingClient;
    RecyclerView recyclerView;
    ArrayList<MultiSubs> subsList;

    Context context;
    String dur;
    ImageView quit;
    String productId;

    int planIndex;
    TextView statusTv;// tandC, privPolicy;
    boolean isSuccess = false;
    //ActivitySubscriptionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_modern);
        quit = findViewById(R.id.quitImageView);
        statusTv = findViewById(R.id.status_Tv);
        productId = "ekuku_monthly_subs";
        context = SubscriptionsActivity.this;

        billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        showSubsList();

        subsList = new ArrayList<>();

        recyclerView = findViewById(R.id.subsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*if (ConnectionClass.premium) {
            statusTv.setText(getResources().getText(R.string.alreadySubscribed));
            //subscribe.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));//replace MainActivity with Subscriptions.Activity
            overridePendingTransition(0,0);
            super.finish();
            //I don't want the user to get back, but finish
        } else {
            statusTv.setText(getResources().getText(R.string.notyetsubscribed));
            startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
            //super.finish();
        }*/
        

        quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                        //finish();

                    }
                });
            }



    private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> listPurchases) {

            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && listPurchases!=null) {
                for (Purchase purchase : listPurchases) {
                    handlePurchase(purchase);

                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(SubscriptionsActivity.this, "Umeshaunganishwa ",Toast.LENGTH_SHORT).show();
                isSuccess = true;
                ConnectionClass.premium = true;
                ConnectionClass.locked = false;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));//replace MainActivity with Subscriptions.Activity
                finish();
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                Toast.makeText(SubscriptionsActivity.this, "Haijawezekana ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                //finish();
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                Toast.makeText(SubscriptionsActivity.this, "Imesitisha. Jaribu tena ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                //finish();
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.NETWORK_ERROR) {
                Toast.makeText(SubscriptionsActivity.this, "Tatizo la Mtandao. Jaribu tena ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                //finish();
            }else {
                Toast.makeText(SubscriptionsActivity.this, "Wasiliana na watoa Huduma wetu ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                //finish();
        }
        }
    };

    private  void showSubsList(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
               // retryBillingServiceConnection();
                showSubsList();

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                                    .setProductList(
                                            ImmutableList.of(
                                                    QueryProductDetailsParams.Product.newBuilder()
                                                            .setProductId(productId)
                                                            .setProductType(BillingClient.ProductType.SUBS)
                                                            .build()))
                                    .build();
                            billingClient.queryProductDetailsAsync(
                                    queryProductDetailsParams,
                                    new ProductDetailsResponseListener() {
                                        @Override
                                        public void onProductDetailsResponse(@NonNull BillingResult billingResult,
                                                                             @NonNull List<ProductDetails> list) {
                                            for (ProductDetails productDetails : list) {
                                                for (int i = 0; i <= (productDetails.getSubscriptionOfferDetails().size()); i++) {

                                                    String subsName = null;
                                                    String status = null;

                                                    if (i == 0) {
                                                        subsName = productDetails.getName();
                                                    }
                                                    int index = i;
                                                    String phases;

                                                    String formattedPrice = productDetails.getSubscriptionOfferDetails()
                                                            .get(i)
                                                            .getPricingPhases()
                                                            .getPricingPhaseList()
                                                            .get(0)
                                                            .getFormattedPrice();
                                                    String billingPeriod = productDetails.getSubscriptionOfferDetails()
                                                            .get(i)
                                                            .getPricingPhases()
                                                            .getPricingPhaseList()
                                                            .get(0).getBillingPeriod();
                                                    int recurrenceMode = productDetails.getSubscriptionOfferDetails().get(i)
                                                            .getPricingPhases().getPricingPhaseList()
                                                            .get(0).getRecurrenceMode();

                                                    String number, duration, bp;

                                                    number = billingPeriod.substring(1, 2);
                                                    duration = billingPeriod.substring(2, 3);
                                                    bp = billingPeriod;

                                                    int nPhases = productDetails.getSubscriptionOfferDetails()
                                                            .get(i)
                                                            .getPricingPhases()
                                                            .getPricingPhaseList().size();

                                                    if (recurrenceMode == 2) {
                                                        if (duration.equals("M")) {
                                                            dur = " Mwezi " + "mzima " + number;
                                                        } else if (duration.equals("Y")) {
                                                            dur = "Mwaka" + "mzima " + number;
                                                        } else if (duration.equals("W")) {
                                                            dur = " Kwa " + "Wiki " + number;

                                                        } else if (duration.equals("D")) {
                                                            dur = " Kwa " + "Siku " + number;
                                                        }
                                                    } else {
                                                        if (bp.equals("P1M")) {
                                                            dur = "/Kila Mwezi";
                                                        } else if (bp.equals("P6M")) {
                                                            dur = "/Kila Miezi 6 ";

                                                        } else if (bp.equals("P1Y")) {
                                                            dur = "/Kila Mwaka";

                                                        }

                                                    }
                                                    phases = formattedPrice + " " + dur;
                                                    for (int j = 0; j <= nPhases - 1; j++) {
                                                        if (j > 0) {
                                                            String period = productDetails.getSubscriptionOfferDetails().get(i)
                                                                    .getPricingPhases().getPricingPhaseList().get(j).getBillingPeriod();
                                                            String price = productDetails.getSubscriptionOfferDetails().get(i)
                                                                    .getPricingPhases().getPricingPhaseList().get(j).getFormattedPrice();

                                                            if (period.equals("P1M")) {
                                                                dur = "/Kila Mwezi";

                                                            } else if (period.equals("P6M")) {
                                                                dur = "/Kila Miezi 6";

                                                            } else if (period.equals("P1Y")) {
                                                                dur = "/Kila Mwaka";

                                                            } else if (period.equals("P1W")) {
                                                                dur = "/Kila Wiki";

                                                            } else if (period.equals("P3W")) {
                                                                dur = "Kila / Miezi 3";
                                                            } else {
                                                                dur = "";
                                                            }

                                                            phases += "\n" + price + dur;

                                                            subsName = productDetails.getSubscriptionOfferDetails().get(i).getOfferId();
                                                        }
                                                    }

                                                    subsList.add(new MultiSubs(subsName, phases, subsName, index));

                                                }

                                            }
                                        }
                                    }
                            );


                        }
                    });



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            SubsAdapter subscriptionAdapter = new SubsAdapter(context, subsList);
                            recyclerView.setAdapter(subscriptionAdapter);
                            subscriptionAdapter.setOnItemClickListener(position -> {
                                String p =String.valueOf(position);
                                Toast.makeText(SubscriptionsActivity.this, p, Toast.LENGTH_SHORT).show();
                                MultiSubs multiSubs = subsList.get(position);
                                planIndex = multiSubs.planIndex;
                                subscribeProduct();
                            });
                        }


                    });

                }


            }
        });
    }

   /* private void retryBillingServiceConnection() {
        new Handler().postDelayed(()->{
            billingClient.startConnection(this@BillingClient.BillingResponseCode)
            reconnectMilliseconds);
        }
    }*/

    private  void subscribeProduct(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                        .setProductList(
                                ImmutableList.of(
                                        QueryProductDetailsParams.Product.newBuilder()
                                                .setProductId(productId)
                                                .setProductType(BillingClient.ProductType.SUBS)
                                                .build()))
                        .build();

                billingClient.queryProductDetailsAsync(
                        queryProductDetailsParams, new ProductDetailsResponseListener() {
                            @Override
                            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> productDetailsList) {
                                for(ProductDetails productDetails:productDetailsList){
                                    assert productDetails.getSubscriptionOfferDetails() != null;
                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(planIndex)
                                            .getOfferToken();
                                    ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = ImmutableList.of(
                                            BillingFlowParams.ProductDetailsParams
                                                    .newBuilder()
                                                    .setProductDetails(productDetails)
                                                    .setOfferToken(offerToken)
                                                    .build()
                                    );

                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParamsList)
                                            .build();
                                    billingClient.launchBillingFlow(SubscriptionsActivity.this, billingFlowParams);
                                }

                            }
                        }
                );


            }
        });
    }

    private void handlePurchase(final Purchase purchase) {
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        ConsumeResponseListener consumeResponseListener = ((billingResult, s) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

            }
        });
        billingClient.consumeAsync(consumeParams, consumeResponseListener);

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                Toast.makeText(getApplicationContext(), "Manunuzi siyo sahihi", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                //statusTv.setText(getResources().getText(R.string.subscribed));
                isSuccess = true;
            } else {
                Toast.makeText(context, "Umeshaunganishwa na kifurushi", Toast.LENGTH_SHORT).show();//statusTv.setText(getResources().getText(R.string.alreadySubscribed));
            }
            ConnectionClass.premium = true;
            ConnectionClass.locked = false;
            //subscribe.setVisibility(View.GONE);
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            Toast.makeText(context, "Imesubirishwa", Toast.LENGTH_SHORT).show();
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            Toast.makeText(context, "Hakuna Taarifa", Toast.LENGTH_SHORT).show();
        }

        //Log.d("Purchase Token", "Purchase " + purchase.getPurchaseToken());
        //Log.d("Purchase Time", "Purchasetime " + purchase.getPurchaseTime());
        //Log.d("Purchase Token", "PurchaseID " + purchase.getOrderId());

    }



          AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                @Override
                public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        //statusTv.setText(getResources().getText(R.string.alreadySubscribed));
                        isSuccess = true;
                        ConnectionClass.premium = true;
                        ConnectionClass.locked = false;
                    }

                }
            };

            private boolean verifyValidSignature(String signDate, String signature) {
                try {
                    String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fJCNjwesxF6m3eX5ED9SZJiUqxExyTuyat7ooknNcz2fs6HQAk3YMN+S/vOO2eBqBXTjRG/dgUY9v8wPro56M2L7ZA1bp7ECx820idzuJe9s29z2kUMaUxoVhZGvmiT1FOmQY3HDWhHqlYg10GaSzvxQitG6f31Gd7Qtel2QovzPMnUMf1DttpNnFXGVO4jhfR7zSIXhkdfr1egfsn+pVzxVmz2MT3YOwWqXtWNs64p7U5oydw18iu5hk/5Dr0/aKoZdm8HZ5idbzcA0peejLUEfrYg3fvQhpHuzC5CObvh6o/zRdspcpITAgyQf83MB98930ZbitWD8U5dFmV0GwIDAQAB";
                    return Security.verifyPurchase(base64Key, signDate, signature);

                } catch (IOException e) {
                    return false;
                }
            }



   /* protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();

            //finish();
        }
    }*/

    protected void onResume(){
                super.onResume();
                billingClient.queryPurchasesAsync(
                        QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                        (billingResult, list) -> {
                            if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                                for (Purchase purchase:list){
                                    if (purchase.getPurchaseState()==Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()){
                                        verifySubsPurchase(purchase);
                                    }
                                }
                            }
                        }
                );
    }

    private void verifySubsPurchase(Purchase purchase) {
                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                        .newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                    if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                        isSuccess = true;
                        ConnectionClass.premium = true;
                        ConnectionClass.locked = false;
                    }
                });
    }

}

