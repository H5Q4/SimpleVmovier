package com.github.jupittar.vmovier.data.source.remote.interceptors;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public  class HttpLoggingInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Logger.t(0).i(String.format("%s: %s%n%s%nBODY: %s",
            request.method(),
            request.url(), request.headers(),
            stringifyRequestBody(request)
        ));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Logger.t(0).i(String.format(Locale.CHINA, "Received response for %s in %.1fms%n%s",
            response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        ResponseBody responseBody = response.body();
        String responseBodyString = response.body().string();
        Logger.t(0).json(responseBodyString);

        return response.newBuilder()
            .body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes()))
            .build();
    }

    private static String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
