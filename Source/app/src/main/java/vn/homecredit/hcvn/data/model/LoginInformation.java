/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/19/18 3:17 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model;

public class LoginInformation
{
    public String phoneNumber;
    public String password;

    public LoginInformation() { }
    public LoginInformation(String phoneNumber, String password)
    {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
