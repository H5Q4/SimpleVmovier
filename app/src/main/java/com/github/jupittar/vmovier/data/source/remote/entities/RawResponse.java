package com.github.jupittar.vmovier.data.source.remote.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawResponse<T> {

    private String status;
    private String msg;
    private T data;

}
