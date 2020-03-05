package com.leqi.lwcamera.http


import com.leqi.baselib.net.HttpProvider
import com.leqi.lwcamera.model.bean.apiV2.*
import com.leqi.xxcamera.model.bean.apiV2.*
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 *  接口文档地址: https://gitea.leqi.us/id-photo-verify/IDPhotoVerifyPython/src/branch/master/README.md
 *  base url : https://api.id-photo-verify.com/api/V2/
 */

interface HttpService {

    @PUT
    suspend fun upOSS(@Url url: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @GET("user_key")
    suspend fun userID(): UserIDBean

    @GET("adver")
    suspend fun adver(@Query("type") type: String): AdverBean

    @GET("app_switch")
    suspend  fun appSwitch(): AppSwitchBean

    @GET("version_check")
    suspend fun checkVer():VersionBean

    @GET("phonenumber")
    suspend fun customerServiceNumber(): PhoneNumberBean

    @GET("home_specs")
    suspend fun homeHotSpecs(): Observable<SpecsResponse>

    @GET("original_oss_url")
    suspend fun upOriginOSS(): UpOriginalBean

    @POST("check_specification")
    suspend fun checkPhoto(@Body requestBody: RequestBody): Observable<CheckBean>

    @GET("specs/{keyword}")
    suspend fun searchSpecsByKey(@Path("keyword") keyword: String): Observable<SpecsResponse>

    //13 规格详情
    @GET("specs_by_id/{spec_id}")
    suspend fun searchSpecById(@Path("spec_id") spec_id: Int): Observable<SearchSpecIdBean>

    @GET("hot_specs")
    suspend fun hotSpecs(): Observable<HotSpecsBean>

    @POST("change_background")
    suspend fun replace(@Body requestBody: RequestBody): Observable<ReplaceBean>

    @POST("crop_creation_order")
    suspend fun crop(@Body requestBody: RequestBody): Observable<CropBean>

    @POST("ele_order")
    suspend fun confirmEleOrder(@Body requestBody: RequestBody): Observable<ConfirmElectronicOrderBean>

    @GET("wechat_pay/{order_id}")
    suspend fun wechatPayEle(@Path("order_id") order_id: String): Observable<WechatPayBean>

    @GET("alipay/{order_id}")
    suspend fun aliPayEle(@Path("order_id") order_id: String): Observable<AliPayBean>

    @GET("order_state/{order_id}")
    suspend  fun orderStateEle(@Path("order_id") order_id: String): Observable<OrderStateEleBean>

    //47 查看电子照订单列表
    @GET("multi_back_ele_order")
    suspend fun orderInfoEleList(@Query("order_state") order_state: String): Observable<OrderListInfoEleBean>

    //48 根据订单号查看电子订单详情(返回的图片url为列表)
    @GET("multi_back_ele_order/{order_id}")
    suspend fun orderInfoEle(@Path("order_id") order_id: String): Observable<OrderInfoEleBean>

    @GET("delete_ele_order/{order_id}")
    suspend fun deleteOrderEle(@Path("order_id") order_id: String): Observable<BaseCode>

    @POST("email/{order_id}")
    suspend fun email(@Path("order_id") order_id: String, @Body requestBody: RequestBody): Observable<BaseCode>

    @POST("print_platform_picture_key")
    suspend fun printPlatform(@Body requestBody: RequestBody): Observable<PlatformBean>

    // 获取规格及分组信息
    @GET("specs_group")
    suspend  fun specsGroup(): Observable<SpecsGroupResponse>

    @POST("feedback")
    suspend fun feedback(@Body requestBody: RequestBody): BaseCode

    @GET("problem/{problem_type}")
    suspend  fun problem(@Path("problem_type") problem_type: String): ProblemBean

    @GET("cut_spec")
    suspend fun cutSpecList(): Observable<CropSpecBean>

    @GET("clothes")
    suspend  fun clothesList(): Observable<ClothesBean>

    @POST("cutout_beauty_cut")
    suspend  fun manufacture(@Body requestBody: RequestBody): Observable<ManufactureBean>

    @POST("cutout_beauty_cut/serial_number")
    suspend fun manufactureDone(@Body requestBody: RequestBody): Observable<ManufactureDoneBean>

    @GET("link_resources/{src_type}")
    suspend  fun link(@Path("src_type") src_type: String): LinkBean

    //微信授权登录
    @POST("OAuth2/wechat")
    suspend  fun wechatLoginCheck(@Body requestBody: RequestBody): Observable<LoginCheckBean>

    //QQ授权登录
    @POST("OAuth2/qq")
    suspend  fun qqLoginCheck(@Body requestBody: RequestBody): Observable<LoginCheckBean>

    //解绑原账号用户并绑定到当前账号下
    @POST("OAuth2/bind")
    suspend  fun loginBind(@Body requestBody: RequestBody): Observable<BaseCode>

    // 查看当前账号信息
    @GET("OAuth2/info")
    suspend  fun loginInfo(): Observable<LoginInfoBean>


    // 创建留言卡
    @POST("card/leave_message")
    suspend fun leave_message(@Body requestBody: RequestBody): Observable<MessageCardBean>

    //获取该用户下所有留言卡
    @GET("card")
    suspend fun getCardList(): Observable<MessageCardListBean>

    //查看当前账号下所有的优惠卷
    @GET("coupons")
    suspend  fun coupons(): Observable<CouponListResponBean>

    //查看当前是否存在优惠活动
    @GET("coupons/activity/{coupon_type}/inquiry")
    suspend  fun isHasCoupon(@Path("coupon_type") couponType: String): Observable<CouponsResponBean>

    //领取优惠活动的优惠卷
    @POST("coupons/activity/{coupon_type}/grant")
    suspend fun receiveCoupon(@Path("coupon_type") couponType: String): Observable<BaseCode>



    /*************************************职业形象照专属接口***********************************************/

    //形象照获取模特图
    @GET("body/models")
    suspend  fun getModels(): ModelsResponse


    //形象照获取背景色
    @GET("body/background")
    suspend  fun getBodyBackground(): BodyBackgroundResponse

    //形象照获取规格
    @GET("body/specs")
    suspend  fun getBodySpecs(): BodySpecsResponse


    //形象照返回无背景图片
    @POST("body/pic")
    suspend  fun getBodyPicture(@Body requestBody: RequestBody): BodyPictureResponse

    //形象照获取流水号
    @POST("body/serial_number")
    suspend fun getBodySerialNumber(@Body requestBody: RequestBody): BodySerialResponse



}
