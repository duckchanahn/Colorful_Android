package com.example.colorful_android.DTO;

import static android.util.Base64.NO_WRAP;


import com.google.gson.annotations.SerializedName;

import android.util.Base64;
import android.util.Log;

public class PersonalColorTestDTO {
    @SerializedName("customerId")
    private int customerId;
    @SerializedName("binary")
    private String binary;

    public PersonalColorTestDTO(int customerId, byte[] binary) {
        this.customerId = customerId;
        this.binary = Base64.encodeToString(binary, NO_WRAP);
        Log.e("TAG", "binary encode : " + binary);
        Log.e("TAG", "binary length : " + binary.length);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
}
