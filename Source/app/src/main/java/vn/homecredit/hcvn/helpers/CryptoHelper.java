/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/20/18 4:57 PM  by hien.nguyenm
 */

package vn.homecredit.hcvn.helpers;


public interface CryptoHelper {
    String encrypt(String value);
    String decrypt(String encrypted);

    String encryptObject(Object obj);
    <T> T decryptObject(String enscriptedObject, Class<T> type);

    String signWithRSAPem(String privateKeyString, String message);
    String signWithRSAPem(String message);
    boolean verifyRSASignedString(String publicKeyString, String message, String signature);
}
