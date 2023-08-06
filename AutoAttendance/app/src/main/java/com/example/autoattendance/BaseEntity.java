package com.example.autoattendance;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
    public int id;
    @SerializedName("body")
    public String name;
}
