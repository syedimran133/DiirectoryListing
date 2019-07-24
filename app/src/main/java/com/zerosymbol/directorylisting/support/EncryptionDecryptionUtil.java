package com.zerosymbol.directorylisting.support;


import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.encoders.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * root
 */
public class EncryptionDecryptionUtil {

    final static String PASSKEY = "test1234test1234";
    private static final String USER_AGENT = "Mozilla/5.0";

    private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }

    private static byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    private static byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
    }


   /* public static void main(String [] args) throws Exception {

//        byte[] enc= encrypt("Wallet deposit was successful".getBytes(),PASSKEY.getBytes(), PASSKEY.getBytes());
//        String encBase =  Base64.encode(enc);
//        String encrypted =new String(encBase.getBytes(), "UTF-8");
//        System.out.println("Encrypted String is:"+encrypted);
//        System.out.println("Encrypted length is:"+encrypted.length());
//
//        byte[] decBase = Base64.decode(encrypted.getBytes());
//        byte[] dec= decrypt(decBase,PASSKEY.getBytes() , PASSKEY.getBytes());
//        System.out.println("Decrypted file is:"+new String(dec, "UTF-8"));
    	
         String ekey="p2p_cashin 8285608596 Sanjay 8447955231 Abhay 110046 3000 1234";
    	 String dkey="QMBqZ4HTGi5LbGbGj/vHygfeQcH7y20aKl44mS6cLpx4GwHGlMqPA5OzO5pDy2Yiw35yke+feNE47RZWQPOTosjyQ46C++vT4BveMwwcem9ZlXFqEQe5wPD2QqB4ubo0";
        String response = doDecrypt(dkey);//sendEncrypted(encrypted,"9956135357");
        System.out.println(response+"\n Length==>"+response.length());
        //sendEncrypted("LijXUpeXiKELMDGx9owCIH3Q1vwLN3ZwBCwGkJRhNzZQ6prP6B6GdDI7HxdoJRjcBwBz53W5FdfLsnPdsBAKKw==","9956135357");
    }*/

    public static String doDecrypt(String msg) throws Exception {
        byte[] decBase = Base64.decode(msg.getBytes());
        byte[] dec = decrypt(decBase, PASSKEY.getBytes(), PASSKEY.getBytes());
        return new String(dec, "UTF-8");
    }

    public static String EncryptAndSend(String msg, String msisdn) throws Exception {
        byte[] enc = encrypt(msg.getBytes(), PASSKEY.getBytes(), PASSKEY.getBytes());
        String encBase = new String(Base64.encode(enc), "UTF-8");//Base64.encode(enc);
        String encrypted = new String(encBase.getBytes(), "UTF-8");
        String response = sendEncrypted(encrypted, msisdn);
        return response;
    }

    public static String doEncrypt(String msg) {
        try {
            if (msg.endsWith(".00")) {
                msg = msg.substring(0, msg.length() - 3);
            } else if (msg.endsWith(".0")) {
                msg = msg.substring(0, msg.length() - 2);
            } else if (msg.endsWith(".")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            byte[] enc = encrypt(msg.getBytes(), PASSKEY.getBytes(), PASSKEY.getBytes());
            String encBase = new String(Base64.encode(enc), "UTF-8");//Base64.encode(enc);
            String encrypted = new String(encBase.getBytes(), "UTF-8");
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    private static String sendEncrypted(String msg, String msisdn) throws Exception {

        String url = "http://122.160.164.233:8086/msgtest/sendSMS.php?msisdn=" + msisdn + "&port=184E&msg=" + URLEncoder.encode("DPL|" + msg + "^X", "UTF-8");
        ;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header1
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }

}