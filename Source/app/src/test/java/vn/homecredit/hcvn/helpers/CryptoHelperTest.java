/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/22/18 6:14 PM  by hien.nguyenm
 */

package vn.homecredit.hcvn.helpers;

import org.junit.Test;
import vn.homecredit.hcvn.helpers.entities.TestObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CryptoHelperTest extends BaseTests {
    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fLlU37GwfBp8MnjDYYHwD1LMsJ" +
            "hqbMouzOyJzbgqRFE5VvIRsIkLw6Eroehmd6+3h767lg5vF9KaNSpDTzz8zIr5n9R/gTdID" +
            "E8oGZaIYIX+CcFQY3e0wH7XIp3TXN3Vu/OwgXi42UVra7Ezkcs62DfoFCC2L31a9MrOUcAc" +
            "iFAX9KnvVHYzhoGpLLi3YzcUyX95sMNtBZCNR9etiNipfOpbRnVyzSQ3FGel0GSa8veinLU" +
            "sa1Y61y9Jwrm24Zln9mSPFJ2bJi9JdbWx0th/nBUvcHZ4NBjOuWWKVJX/kGEvl69GmsHUcx" +
            "vzxkjuNRgilnqEGds1NwTYCDkpljhwwIDAQAB" +
            "-----END PUBLIC KEY-----";

    private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----" +
            "MIIEowIBAAKCAQEA2fLlU37GwfBp8MnjDYYHwD1LMsJhqbMouzOyJzbgqRFE5VvIRsIkLw6E" +
            "roehmd6+3h767lg5vF9KaNSpDTzz8zIr5n9R/gTdIDE8oGZaIYIX+CcFQY3e0wH7XIp3TXN3" +
            "Vu/OwgXi42UVra7Ezkcs62DfoFCC2L31a9MrOUcAciFAX9KnvVHYzhoGpLLi3YzcUyX95sMN" +
            "tBZCNR9etiNipfOpbRnVyzSQ3FGel0GSa8veinLUsa1Y61y9Jwrm24Zln9mSPFJ2bJi9JdbW" +
            "x0th/nBUvcHZ4NBjOuWWKVJX/kGEvl69GmsHUcxvzxkjuNRgilnqEGds1NwTYCDkpljhwwID" +
            "AQABAoIBAEkJVsRWNbEEKdxhK0963NoVMEAAV84QldO2DTuK7lp5yQa7bTAZGxsR2HDMTuFL" +
            "fdbdtuVsd1yi6kKbEVO4W1/iLkf93rIrWxbQqSpvYkDcYb/tA5vmOQepIFzwoe44hYOCtVzb" +
            "sB7X3x3m9VjTDUCLpCULFe4gKkBJ8bAuFP4pfCHrJ+jwAwLnS9HjpmaCB83rIE5N6rfD9KWU" +
            "y2M+WVhU6/E0m9kEkWijwsmNsfhIRIvkm4YF8j2X8a+Bz6OOJT/T+I4FlIkgZ7S5ExRjjVS+" +
            "k+u8O3JReVdqBiEfCl/AWfKhgKRqKTuEfMPnCHEe043eH29DH9khAijwcXeZ6nECgYEA7btn" +
            "CggYf9YYwpgH9xMzfhwkwHIONtjFiXqXl18WNItct7+EF0XVoLcOndUdSABecEehImYsCcFp" +
            "uDMzkJo6d/oxvcSqEzjSonszJGab8CI2Hz+INrkYDhmZGOGljv1ibh83prKDt/FugVqqVqM4" +
            "i25WBRbR8CvAAvXWh2n0YEcCgYEA6rJSvjp7ZNMM3gSbh/Zb588KlgFZ4iTWLwYwBGtuG9GM" +
            "Ac8mM9wRKTOh2M7RgwYbPiQgMrgIhYMGrjwMIKR+83nELJE7BQ5ea+wpxantafodnpWbNjUF" +
            "Ys3wXJrpeYsh4jPfaJXMJ4/D+BoS8aMse1KwHZv/wgl/tk+i/tdQjKUCgYAkEzESo6HcHEgL" +
            "l3h19NL2ixzEm0ou5nwrXZANZ19+P7ulIonP4kgFqGGub72OxIeQMD5ycuCx5ki9q/9iLRU8" +
            "SZosuuA4JIJKJ7neBzgYm4hs+34XF8VftmC2Ft/BsA+0uEYApMw6KIflVzXMCd61wduJ0Cmx" +
            "cWIk0NtqFxj/VwKBgQCi2hfmHahvnq6oA+R0JHNDV3vN+KQI8myYdp+tAWqG7RFMVBP7GjHi" +
            "OAyO53ujggI3TVP2PZLUKcXh8pKrt9jLU2vXTPL6IC1Oj7GwkafGyKHy33ay13mkRKgLagal" +
            "qvBZCIt2A7OEpBJkPpt0hjggXpEMCqoeVv8CXyIH2RnwbQKBgAfF4HqK9cMKUTDGqRecdP6X" +
            "e33UH50DGrLhCW6k+eJSqHo9cnNPAaSKMas2RHMyLuC7JZevHHYjgg6Xz8iM4QMK4ByzgG9w" +
            "GPKJ2K+/LCPO3NSuXSdn3x2rs3ZKDTIItK1Zy+qoy+pgY4TQ8PZcz0T/wIn/oXeCIKWSi92O" +
            "hMtC" +
            "-----END RSA PRIVATE KEY-----";



    @Test
    public void encrypt_isCorrect() {
        // Arrange
        String originalString = "HomeCredit_MobileApp";

        // Action
        String encryptString = CryptoHelper.encrypt(originalString);
        String decryptString = CryptoHelper.decrypt(encryptString);

        // Assert
        assertEquals(originalString, decryptString);
    }


    @Test
    public void encryptObject_isCorrect() {
        // Arrange
        TestObject originalObject = new TestObject("Test ID", "Test Name");

        // Action
        String encryptString = CryptoHelper.encryptObject(originalObject);
        TestObject decryptObject = CryptoHelper.decryptObject(encryptString, TestObject.class);

        // Assert
        assertEquals(originalObject, decryptObject);
    }

    @Test
    public void signWithRSAPem_isCorrect() {
        // Arrange
        String originalString = "HomeCredit_MobileApp";

        // Action
        String signedString = CryptoHelper.signWithRSAPem(PRIVATE_KEY, originalString);
        boolean verifyResult = CryptoHelper.verifyRSASignedString(PUBLIC_KEY, originalString, signedString);

        // Assert
        assertTrue(verifyResult);
    }
}

