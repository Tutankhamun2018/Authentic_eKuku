package com.sixbert.authenticekuku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubscriptionsActivity extends AppCompatActivity {
    private BillingClient billingClient;
    String subscriptionName, phases, description, dur;
    ImageView quit;
    SwitchMaterial freeTrial;
    Button getPriceButton, subscribe;
    TextView statusTv, subsName, priceList, tandC, privPolicy, benefits;
    boolean isSuccess = false;
    //ActivitySubscriptionBinding binding;

    //productIdNew:ekuku_monthly_subs
    //BasePlanId: 2023-base-plan-ekuku-offer-11

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_modern);
        subscribe = findViewById(R.id.continueBtn);
        quit = findViewById(R.id.quitImageView);
        benefits = findViewById(R.id.benefit1Tv);
        getPriceButton =findViewById(R.id.getPriceButton);
        priceList = findViewById(R.id.pricesTv);
        subsName = findViewById(R.id.subsName);
        tandC = findViewById(R.id.tandC);
        privPolicy = findViewById(R.id.priPolicy);
        statusTv = findViewById(R.id.status_Tv);

        billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        getPrices();

        if (ConnectionClass.premium) {
            statusTv.setText(getResources().getText(R.string.alreadySubscribed));
            subscribe.setVisibility(View.GONE);
        } else {
            statusTv.setText(getResources().getText(R.string.notyetsubscribed));
        }




        //freeTrial = findViewById(R.id.switch1);


        privPolicy.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PrivacyActivity.class)));

        tandC.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TCActivity.class)));

      /* freeTrial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                } else {

                }


            }
        });*/

      getPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrices();

            }
        });




        subscribe.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             billingClient.startConnection(new BillingClientStateListener() {
                                                 @Override
                                                 public void onBillingServiceDisconnected() {

                                                 }

                                                 @Override
                                                 public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                                                     QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                                                             .setProductList(
                                                                     ImmutableList.of(QueryProductDetailsParams.Product.newBuilder()
                                                                             .setProductId("ekuku_monthly_subs")
                                                                             .setProductType(BillingClient.ProductType.SUBS)
                                                                             .build()))
                                                             .build();
                                                     billingClient.queryProductDetailsAsync(
                                                             queryProductDetailsParams, new ProductDetailsResponseListener() {
                                                                 public void onProductDetailsResponse(@NonNull BillingResult billingResult1, @NonNull List<ProductDetails> productDetailsList) {
                                                                     for (ProductDetails productDetails : productDetailsList) {
                                                                         String offerToken = productDetails.getSubscriptionOfferDetails()
                                                                                 .get(0)
                                                                                 .getOfferToken();
                                                                         ImmutableList productDetailsParamsList = ImmutableList.of(BillingFlowParams.ProductDetailsParams.newBuilder()
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
                                                             });


                                                 }
                                             });
                                         }
                                     });




                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(getApplicationContext(), UnsubscribedMainActivity.class));
                        finish();

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
                statusTv.setText(getResources().getText(R.string.alreadySubscribed));
                isSuccess = true;
                ConnectionClass.premium = true;
                ConnectionClass.locked = false;
                subscribe.setVisibility(View.GONE);
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                statusTv.setText(getResources().getText(R.string.featurenotsupported));
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                statusTv.setText(getResources().getText(R.string.feature_cancelled));
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.NETWORK_ERROR) {
                statusTv.setText(getResources().getText(R.string.network_error));
            }else {
                Toast.makeText(getApplicationContext(), "Kuna tatizo "+ billingResult.getDebugMessage(),Toast.LENGTH_SHORT).show();
        }
        }
    };

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
                statusTv.setText(getResources().getText(R.string.subscribed));
                isSuccess = true;
            } else {
                statusTv.setText(getResources().getText(R.string.alreadySubscribed));
            }
            ConnectionClass.premium = true;
            ConnectionClass.locked = false;
            subscribe.setVisibility(View.GONE);
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            statusTv.setText(getResources().getText(R.string.subscriptionpending));
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            statusTv.setText(getResources().getText(R.string.unspecifiedstate));
        }

    }



          AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                @Override
                public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        statusTv.setText(getResources().getText(R.string.alreadySubscribed));
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


            private void getPrices(){
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

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
                                                                    .setProductId("ekuku_monthly_subs")
                                                                    .setProductType(BillingClient.ProductType.SUBS)
                                                                    .build()))
                                            .build();
                                    billingClient.queryProductDetailsAsync(
                                            queryProductDetailsParams,
                                            new ProductDetailsResponseListener() {
                                                @Override
                                                public void onProductDetailsResponse(@NonNull BillingResult billingResult,
                                                                                     @NonNull List<ProductDetails> list) {
                                                    for (ProductDetails productDetails:list){
                                                        String offerToken = productDetails.getSubscriptionOfferDetails().
                                                                get(0).getOfferToken();
                                                        ImmutableList productDetailsParamasList = ImmutableList.of(
                                                                BillingFlowParams.ProductDetailsParams.newBuilder()
                                                                        .setProductDetails(productDetails)
                                                                        .setOfferToken(offerToken)
                                                                        .build()
                                                        );
                                                        subscriptionName = productDetails.getName();
                                                        description = productDetails.getDescription();
                                                        String formattedPrice = productDetails.getSubscriptionOfferDetails()
                                                                .get(0)
                                                                .getPricingPhases()
                                                                .getPricingPhaseList()
                                                                .get(0)
                                                                .getFormattedPrice();
                                                        String billingPeriod = productDetails.getSubscriptionOfferDetails()
                                                                .get(0)
                                                                .getPricingPhases()
                                                                .getPricingPhaseList()
                                                                .get(0).getBillingPeriod();
                                                        int recurrenceMode = productDetails.getSubscriptionOfferDetails().get(0)
                                                                .getPricingPhases().getPricingPhaseList()
                                                                .get(0).getRecurrenceMode();

                                                        String number, duration, bp;

                                                        number = billingPeriod;
                                                        duration = billingPeriod.substring(2,3);
                                                        bp = billingPeriod.substring(1,2);

                                                        if (recurrenceMode == 2){
                                                            if(duration.equals("M")){
                                                                dur = " Kwa " + "Mwezi " + number;
                                                            } else if (duration.equals("Y")){
                                                                dur = "Kwa "  + "Mwaka " + number;
                                                            } else if (duration.equals("W")){
                                                                dur = " Kwa "  + "Wiki " + number;

                                                            } else  if (duration.equals("D")){
                                                                dur = " Kwa " + "Siku " + number ;
                                                            }
                                                        } else {
                                                            if (bp.equals("P1M")){
                                                                dur = "/Kila Mwezi";
                                                            }else if (bp.equals("P6M")){
                                                                dur = "/Kila Miezi 6 ";

                                                            }else  if (bp.equals("P1Y")){
                                                                dur = "/Kila Mwaka";

                                                            }

                                                        }
                                                        phases = formattedPrice+" "+ dur;
                                                        for (int i = 0; i<= (productDetails.getSubscriptionOfferDetails().get(0)
                                                                .getPricingPhases().getPricingPhaseList().size()); i++){
                                                            if (i>0){
                                                                String period = productDetails.getSubscriptionOfferDetails().get(0)
                                                                        .getPricingPhases().getPricingPhaseList().get(i).getBillingPeriod();
                                                                String price = productDetails.getSubscriptionOfferDetails().get(0)
                                                                        .getPricingPhases().getPricingPhaseList().get(i).getFormattedPrice();

                                                                if(period.equals("P1M")){
                                                                    dur = "/Kila Mwezi";

                                                                } else if (period.equals("P6M")){
                                                                    dur = "/Kila Miezi 6";

                                                                } else if (period.equals("P1Y")){
                                                                    dur = "/Kila Mwaka";

                                                                } else  if (period.equals("P1W")){
                                                                    dur = "/Kila Wiki";

                                                                }  else  if (period.equals("P3W")){
                                                                    dur = "Kila / Miezi 3";
                                                                }

                                                                phases +="\n"+price+dur;
                                                            }
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

                                //binding.

                                subsName.setText(subscriptionName);
                                    priceList.setText(phases);
                                    benefits.setText(description);
                            }


                            });

                        }

                    }
                });
            }

    protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }

}

