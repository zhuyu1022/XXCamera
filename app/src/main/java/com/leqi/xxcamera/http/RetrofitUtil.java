package com.leqi.xxcamera.http;

import com.leqi.lwcamera.http.HttpService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static RetrofitUtil mRetrofitUtil;
    private Retrofit mRetrofit;

    public static RetrofitUtil getInstanse() {
        if (mRetrofitUtil == null) {
            mRetrofitUtil = new RetrofitUtil();
        }
        return mRetrofitUtil;
    }


    private RetrofitUtil() {
        initRetrofit();
    }

    private void initRetrofit() {

        /*********************************OkHttp配置**********************************/
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //自动追加  url参数
       // okHttpBuilder.addInterceptor(new AppendUrlParamInterceptor());
        //自动追加header参数
       // okHttpBuilder.addInterceptor(new AppendHeaderParamInterceptor());

        //okhttp信息拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(httpLoggingInterceptor);


        /******************************************************************************/

        //step1：首先获取retrofit实例
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                //gson解析库，可直接以实体形式拿到返回值
                .addConverterFactory(GsonConverterFactory.create())
                //支持rxjava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //传入自定义的okhttpclient实例
                .client(okHttpBuilder.build())
                .build();

    }

    public HttpService createApi() {
        return mRetrofit.create(HttpService.class);
    }
}
