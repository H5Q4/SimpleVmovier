package com.github.jupittar.commlib.utilities;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static String assetFile2Str(Context c, String urlStr) {
    InputStream in = null;
    try {
      in = c.getAssets().open(urlStr);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
      String line = null;
      StringBuilder sb = new StringBuilder();
      do {
        line = bufferedReader.readLine();
        if (line != null && !line.matches("^\\s*\\/\\/.*")) {
          sb.append(line);
        }
      } while (line != null);

      bufferedReader.close();
      in.close();

      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
    }
    return null;
  }

  /**
   * 使用MD5算法对传入的key进行加密并返回。
   */
  public static String toMD5(String str) {
    String result = null;
    try {
      final MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(str.getBytes());
      result = bytesToHexString(digest.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String bytesToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      String hex = Integer.toHexString(0xff & aByte);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * 验证字符串是否是email
   */
  public static boolean isEmail(String str) {
    Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    Matcher matcher = pattern.matcher(str);
    return matcher.matches();
  }

  /**
   * 验证字符串是否是电话号码
   */
  public static boolean isPhone(String phoneStr) {
    Pattern pattern = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
    Matcher matcher = pattern.matcher(phoneStr);
    return matcher.matches();
  }

  /**
   * 将字符串的首字母大写
   * <p>
   * <pre>
   * capitalizeFirstLetter(null)     =   null;
   * capitalizeFirstLetter("")       =   "";
   * capitalizeFirstLetter("2ab")    =   "2ab"
   * capitalizeFirstLetter("a")      =   "A"
   * capitalizeFirstLetter("ab")     =   "Ab"
   * capitalizeFirstLetter("Abc")    =   "Abc"
   * </pre>
   */
  public static String capitalizeFirstLetter(String str) {
    if (TextUtils.isEmpty(str)) {
      return str;
    }

    char c = str.charAt(0);
    return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : String.valueOf(Character.toUpperCase(c)) + str.substring(1);
  }

}