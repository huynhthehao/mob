/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 4:06 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data;

import java.io.Serializable;
import java.util.Observable;

public class ObservableString extends Observable implements Serializable {
    private String value;

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
        this.setChanged();
        this.notifyObservers(value);
    }
}
