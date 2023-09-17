package com.example.autoattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import com.example.autoattendance.databinding.ActivityMainAppBinding;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainAppActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    static String KEY_NAME = "FingerprintKey";
    private ActivityMainAppBinding binding;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 2;
    KeyGenerator keyGenerator;
    KeyStore keyStore;
    SecretKey key;
    Cipher cipher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

    }




    public void StoreData(String key, boolean value) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public void StoreData(String key, String value) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public void StoreData(String key, int value) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public boolean createkeyGenerator(){
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            return true;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }
    }

    public boolean generateSecretKey() {
        try {
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setUserAuthenticationRequired(true)
                    .setInvalidatedByBiometricEnrollment(true)
                    .build();

            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(keyGenParameterSpec);
            keyGenerator.generateKey();
            return true;
        } catch (NoSuchAlgorithmException | NoSuchProviderException
                 | InvalidAlgorithmParameterException e) {
//            throw new RuntimeException(e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public SecretKey getSecretKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            return ((KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_NAME, null)).getSecretKey();
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException
                e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException("Failed to get secret key from KeyStore", e);
        } catch (UnrecoverableEntryException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean checkKeyIntegrity() {

        try {
            Cipher cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            System.out.println("The key has been invalidated.");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        }
        return false;
    }

    public void handleInvalidatedKey(SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (KeyPermanentlyInvalidatedException e) {
            System.out.println("The key has been invalidated. Generating a new one.");
            generateSecretKey();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}