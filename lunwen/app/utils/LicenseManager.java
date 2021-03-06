package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;

public class LicenseManager {

    private static final String password = "http://www.hatech.com.cn";
    private static String salt;
    private static int pswdIterations = 65536;
    private static int keySize = 128;
    private byte[] ivBytes;

    public static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChDzcjw/rWgFwnxunbKp7/4e8w" + "\r"
            + "/UmXx2jk6qEEn69t6N2R1i/LmcyDT1xr/T2AHGOiXNQ5V8W4iCaaeNawi7aJaRht" + "\r"
            + "Vx1uOH/2U378fscEESEG8XDqll0GCfB1/TjKI2aitVSzXOtRs8kYgGU78f7VmDNg" + "\r" + "XIlk3gdhnzh+uoEQywIDAQAB" + "\r";

    public static final String DEFAULT_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKEPNyPD+taAXCfG" + "\r"
            + "6dsqnv/h7zD9SZfHaOTqoQSfr23o3ZHWL8uZzINPXGv9PYAcY6Jc1DlXxbiIJpp4" + "\r"
            + "1rCLtolpGG1XHW44f/ZTfvx+xwQRIQbxcOqWXQYJ8HX9OMojZqK1VLNc61GzyRiA" + "\r"
            + "ZTvx/tWYM2BciWTeB2GfOH66gRDLAgMBAAECgYBp4qTvoJKynuT3SbDJY/XwaEtm" + "\r"
            + "u768SF9P0GlXrtwYuDWjAVue0VhBI9WxMWZTaVafkcP8hxX4QZqPh84td0zjcq3j" + "\r"
            + "DLOegAFJkIorGzq5FyK7ydBoU1TLjFV459c8dTZMTu+LgsOTD11/V/Jr4NJxIudo" + "\r"
            + "MBQ3c4cHmOoYv4uzkQJBANR+7Fc3e6oZgqTOesqPSPqljbsdF9E4x4eDFuOecCkJ" + "\r"
            + "DvVLOOoAzvtHfAiUp+H3fk4hXRpALiNBEHiIdhIuX2UCQQDCCHiPHFd4gC58yyCM" + "\r"
            + "6Leqkmoa+6YpfRb3oxykLBXcWx7DtbX+ayKy5OQmnkEG+MW8XB8wAdiUl0/tb6cQ" + "\r"
            + "FaRvAkBhvP94Hk0DMDinFVHlWYJ3xy4pongSA8vCyMj+aSGtvjzjFnZXK4gIjBjA" + "\r"
            + "2Z9ekDfIOBBawqp2DLdGuX2VXz8BAkByMuIh+KBSv76cnEDwLhfLQJlKgEnvqTvX" + "\r"
            + "TB0TUw8avlaBAXW34/5sI+NUB1hmbgyTK/T/IFcEPXpBWLGO+e3pAkAGWLpnH0Zh" + "\r"
            + "Fae7oAqkMAd3xCNY6ec180tAe57hZ6kS+SYLKwb4gGzYaCxc22vMtYksXHtUeamo" + "\r" + "1NMLzI2ZfUoX" + "\r";

    /**
     * ??????
     */
    private RSAPrivateKey privateKey;

    /**
     * ??????
     */
    private RSAPublicKey publicKey;

    /**
     * ????????????????????????????????????
     */
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * ????????????
     * 
     * @return ?????????????????????
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * ????????????
     * 
     * @return ?????????????????????
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * ?????????????????????
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * ????????????????????????????????????
     * 
     * @param in
     *            ???????????????
     * @throws Exception
     *             ??????????????????????????????
     */
    public void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("???????????????????????????");
        } catch (NullPointerException e) {
            throw new Exception("?????????????????????");
        }
    }

    /**
     * ???????????????????????????
     * 
     * @param publicKeyStr
     *            ?????????????????????
     * @throws Exception
     *             ??????????????????????????????
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("????????????");
        } catch (InvalidKeySpecException e) {
            throw new Exception("????????????");
        } catch (IOException e) {
            throw new Exception("??????????????????????????????");
        } catch (NullPointerException e) {
            throw new Exception("??????????????????");
        }
    }

    /**
     * ????????????????????????
     * 
     * @param keyFileName
     *            ???????????????
     * @return ????????????
     * @throws Exception
     */
    public void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("????????????????????????");
        } catch (NullPointerException e) {
            throw new Exception("?????????????????????");
        }
    }

    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("????????????");
        } catch (InvalidKeySpecException e) {
            throw new Exception("????????????");
        } catch (IOException e) {
            throw new Exception("??????????????????????????????");
        } catch (NullPointerException e) {
            throw new Exception("??????????????????");
        }
    }

    /**
     * ????????????
     * 
     * @param publicKey
     *            ??????
     * @param plainTextData
     *            ????????????
     * @return
     * @throws Exception
     *             ??????????????????????????????
     */

    public String encrypt(RSAPublicKey publickey, String plainText) throws Exception {

        // get salt
        salt = generateSalt();
        byte[] saltBytes = salt.getBytes("UTF-8");

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, pswdIterations, keySize);

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // encrypt the message
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publickey);
        int key_len = publicKey.getModulus().bitLength() / 8;
        String[] datas = splitString(plainText, key_len - 11);
        String mi = "";
        // ??????????????????????????????-11??????????????????
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        // byte[] encryptedTextBytes =
        // cipher.doFinal(plainText.getBytes("UTF-8"));
        return mi;
    }

    /**
     * BCD????????????
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * ???????????????
     */
    public static String[] splitString(String string, int len) {
        int x = lengthIncludeChinese(string) / len;
        int y = lengthIncludeChinese(string) % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = subStringByByte(string, i * len, i * len + y);
            } else {
                str = subStringByByte(string, i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    @SuppressWarnings("static-access")
    public String decrypt(RSAPrivateKey privateKey, String encryptedText) throws Exception {

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = encryptedText.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);

        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr), "UTF-8");
        }
        return ming;
    }

    /**
     * ASCII??????BCD???
     * 
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * ????????????
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String s = new String(password);
        return s;
    }

    /**
     * ????????????????????????????????????
     * 
     * @param data
     *            ????????????
     * @return ??????????????????
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // ???????????????????????? ???????????????????????????????????????????????? ?????????????????????
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // ???????????????????????? ????????????????????????????????????????????????
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
        }
        return stringBuilder.toString();
    }

    public static byte[] stringToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static int lengthIncludeChinese(String value) {
        int valueLength = 0;
        String chinese = "[??-???]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength++;
            }
        }
        return valueLength;
    }

    public static String subStringByByte(String s, int n) {
        try {
            byte[] c = s.getBytes("GBK");
            int len = c.length > n ? n : c.length;
            if (len % 2 == 1) {
                len = len - 1;
            }
            byte[] b = new byte[len];

            for (int i = 0; i < b.length; i++) {
                b[i] = c[i];
            }

            return new String(b, "GBK");
        } catch (Exception e) {
        }
        return "";
    }

    public static String subStringByByte(String s, int begin, int end) {
        try {
            byte[] c = s.getBytes("GBK");
            byte[] b = new byte[end - begin];

            for (int i = begin; i < end; i++) {
                b[i - begin] = c[i];
            }

            return new String(b, "GBK");
        } catch (Exception e) {
        }
        return "";
    }

    public static List<String> getMacs() {
        List<String> result = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    if (sb.toString() != null && !"".equalsIgnoreCase(sb.toString().trim())) {
                        result.add(sb.toString());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        LicenseManager rsaEncrypt = new LicenseManager();
        try {
            rsaEncrypt.loadPublicKey(LicenseManager.DEFAULT_PUBLIC_KEY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // ????????????
        try {
            rsaEncrypt.loadPrivateKey(LicenseManager.DEFAULT_PRIVATE_KEY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // ???????????????
        String encryptStr = "{\"custom_name\":\"poc1\",\"project_style\":\"??????\",\"mac_address\":\"64-27-37-9B-0D-A7\",\"expire_date\":\"2019-11-28\",\"array_number\":999,\"switch_number\":999,\"host_number\":999,\"tape_number\":999,\"db_number\":9999}";
     //   String encryptStr = "{\"custom_name\":\"????????????\",\"project_style\":\"????????????\",\"array_number\":10,\"switch_number\":999,\"host_number\":0,\"tape_number\":0,\"db_number\":0}";
        System.out.println(encryptStr);

        try {
            String _license_str = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr);
            System.out.println(_license_str);

            System.out.println(rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), _license_str));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

}
