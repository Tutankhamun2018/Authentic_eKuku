package com.sixbert.authenticekuku;

import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Security {
    public static final String TAG = "IABUtil";
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static boolean verifyPurchase(String base64PublicKey, String signedData,
                                         String signature) throws IOException {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) ||
                TextUtils.isEmpty(signature)) {
            return false;
        }
        PublicKey key = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    public static PublicKey generatePublicKey(String encodedPublicKey) throws IOException {
    try {
        byte[] decodeKey = android.util.Base64.decode(encodedPublicKey, android.util.Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    } catch (NoSuchAlgorithmException e){
        throw  new RuntimeException(e);
            } catch (InvalidKeySpecException e){
        String message = "Invalid key specification " + e;
        throw new IOException(message);
    }
    }

    public  static  boolean verify(PublicKey publicKey, String signedData, String signature){
        byte[] signatureBytes;
        try{
            signatureBytes = Base64.decode(signature, android.util.Base64.DEFAULT);
            } catch (IllegalArgumentException e){
            return false;
        }
        try {
            Signature signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM);
            signatureAlgorithm.initVerify(publicKey);
            signatureAlgorithm.update(signedData.getBytes());
            return signatureAlgorithm.verify(signatureBytes);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);

            } catch (SignatureException | InvalidKeyException e){
            e.printStackTrace();

        }
        return false;


    }
}
