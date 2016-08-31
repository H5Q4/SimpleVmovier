package com.github.jupittar.vmovier.network.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawResponse<T> {

    private String status;
    private String msg;
    private T data;

}
