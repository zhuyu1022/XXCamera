package com.leqi.lwcamera.model

import com.leqi.xxcamera.BuildConfig

enum class CountClick(var key: String?, var value: String?) {

    // 首页
    HomeTakePhoto(BuildConfig.FLAVOR_vest + "_" + "HomeTakePhoto", "首页-选规格拍摄"),
    HomeCrop(BuildConfig.FLAVOR_vest + "_" + "HomeCrop", "首页-裁剪证件照"),
    HomeReplaceBackground(BuildConfig.FLAVOR_vest + "_" + "HomeReplaceBackground", "首页-换底色"),
    HomeCheck(BuildConfig.FLAVOR_vest + "_" + "HomeCheck", "首页-检测证件照"),
    HomeBanner(BuildConfig.FLAVOR_vest + "_" + "HomeBanner", "首页-banner"),
    HomeHotSpec(BuildConfig.FLAVOR_vest + "_" + "HomeHotSpec", "首页热门规格"),
    HomeOrder(BuildConfig.FLAVOR_vest + "_" + "HomeOrder", "订单"),
    HomeOrderEle(BuildConfig.FLAVOR_vest + "_" + "HomeOrderEle", "订单-电子照"),
    HomeOrderPrint(BuildConfig.FLAVOR_vest + "_" + "HomeOrderPrint", "订单-冲印照"),
    HomeCostomer(BuildConfig.FLAVOR_vest + "_" + "HomeCostomer", "客服"),

    //关于
    AboutCopyUseId(BuildConfig.FLAVOR_vest + "_" + "AboutCopyUseId", "关于-复制用户id"),
    AboutShootGuide(BuildConfig.FLAVOR_vest + "_" + "AboutShootGuide", "关于-拍照攻略"),
    AboutScore(BuildConfig.FLAVOR_vest + "_" + "AboutScore", "关于-评分"),

    //拍摄
    ShootBack(BuildConfig.FLAVOR_vest + "_" + "ShootBack", "拍摄-返回"),
    ShootOpenSpecDialog(BuildConfig.FLAVOR_vest + "_" + "ShootOpenSpecDialog", "拍摄-点击规格"),
    ShootFrontCamera(BuildConfig.FLAVOR_vest + "_" + "ShootFrontCamera", "拍摄-前置摄像头"),
    ShootBackCamera(BuildConfig.FLAVOR_vest + "_" + "ShootBackCamera", "拍摄-后置摄像头"),
    ShootAlbum(BuildConfig.FLAVOR_vest + "_" + "ShootAlbum", "拍摄-相册"),
    ShootHotSpec(BuildConfig.FLAVOR_vest + "_" + "ShootHotSpec", "拍摄-规格-热门规格"),
    ShootSearchSpec(BuildConfig.FLAVOR_vest + "_" + "ShootSearchSpec", "拍摄-规格-搜索"),
    //编辑
    EditOpenSpecDialog(BuildConfig.FLAVOR_vest + "_" + "EditOpenSpecDialog", "编辑-规格"),
    EditBack(BuildConfig.FLAVOR_vest + "_" + "EditBack", "编辑-返回"),
    EditSearchSpec(BuildConfig.FLAVOR_vest + "_" + "EditSearchSpec", "编辑-规格-搜索"),
    EditHotSpec(BuildConfig.FLAVOR_vest + "_" + "EditHotSpec", "编辑-规格-热门"),
    EditChangeBg(BuildConfig.FLAVOR_vest + "_" + "EditChangeBg", "编辑-换底色"),
    EditChangeBgConfirm(BuildConfig.FLAVOR_vest + "_" + "EditChangeBgConfirm", "编辑-换底色-确认"),
    EditChangeBgCancle(BuildConfig.FLAVOR_vest + "_" + "EditChangeBgCancle", "编辑-换底色-取消"),
    EditBeauty(BuildConfig.FLAVOR_vest + "_" + "EditBeauty", "编辑-美颜"),
    EditBeautyCompare(BuildConfig.FLAVOR_vest + "_" + "EditBeautyCompare", "编辑-美颜-对比"),
    EditBeautyClose(BuildConfig.FLAVOR_vest + "_" + "EditBeautyClose", "编辑-美颜-关闭美颜"),
    EditBeautyBasic(BuildConfig.FLAVOR_vest + "_" + "EditBeautyBasic", "编辑-美颜-基础美颜"),
    EditBeautyConfirm(BuildConfig.FLAVOR_vest + "_" + "EditBeautyConfirm", "编辑-美颜-确认"),
    EditBeautyCancle(BuildConfig.FLAVOR_vest + "_" + "EditBeautyCancle", "编辑-美颜-取消"),
    EditChangeCloth(BuildConfig.FLAVOR_vest + "_" + "EditChangeCloth", "编辑-换装"),
    EditChangeClothConfirm(BuildConfig.FLAVOR_vest + "_" + "EditChangeClothConfirm", "编辑-换装-确认"),
    EditSave(BuildConfig.FLAVOR_vest + "_" + "EditSave", "编辑-保存"),

    //拍照保存
    SaveEle(BuildConfig.FLAVOR_vest + "_" + "SaveEle", "拍照-保存电子照"),
    SaveEleMultiBackground(BuildConfig.FLAVOR_vest + "_" + "SaveEleMultiBackground", "拍照-保存-多背景"),

    //订单
    OrderExit(BuildConfig.FLAVOR_vest + "_" + "OrderExit", "订单-退出"),
    OrderCancle(BuildConfig.FLAVOR_vest + "_" + "OrderCancle", "订单-取消订单"),
    OrderSave(BuildConfig.FLAVOR_vest + "_" + "OrderSave", "订单-保存电子照"),
    OrderGoPrint(BuildConfig.FLAVOR_vest + "_" + "OrderGoPrint", "订单-冲印"),
    OrderDelete(BuildConfig.FLAVOR_vest + "_" + "OrderDelete", "订单-删除订单"),
    OrderSaveToPhone(BuildConfig.FLAVOR_vest + "_" + "OrderSaveToPhone", "订单-保存手机"),
    OrderSaveToEmail(BuildConfig.FLAVOR_vest + "_" + "OrderSaveToEmail", "订单-保存邮箱"),
    OrderSaveByCode(BuildConfig.FLAVOR_vest + "_" + "OrderSaveByCode", "订单-提取码"),
    OrderPhoneCustomer(BuildConfig.FLAVOR_vest + "_" + "OrderPhoneCustomer", "订单详情-电话客服"),
    OrderOnlineCustomer(BuildConfig.FLAVOR_vest + "_" + "OrderOnlineCustomer", "订单详情-在线"),
    OrderCopyUserId(BuildConfig.FLAVOR_vest + "_" + "OrderCopyUserId", "订单详情-复制ID"),

    //换底色
    ChangeBgMultiBg(BuildConfig.FLAVOR_vest + "_" + "ChangeBgMultiBg", "换底色-保存多背景"),

    //检测
    CheckSuccessDetail(BuildConfig.FLAVOR_vest + "_" + "CheckSuccessDetail", "检测-合格结果详情"),
    CheckFailDetail(BuildConfig.FLAVOR_vest + "_" + "CheckFailDetail", "检测-未合格详情"),
    CheckShoot(BuildConfig.FLAVOR_vest + "_" + "CheckShoot", "检测-拍摄合格证件照"),

    //客服
    CustomerPhone(BuildConfig.FLAVOR_vest + "_" + "CustomerPhone", "客服-电话"),
    CustomerOnline(BuildConfig.FLAVOR_vest + "_" + "CustomerOnline", "客服-在线"),
    CustomerNormalQuestion(BuildConfig.FLAVOR_vest + "_" + "CustomerNormalQuestion", "客服-常见问题"),


    //分享
    ScreenShootShare(BuildConfig.FLAVOR_vest + "_" + "ShareScreenShoo", "截图分享"),
    ScreenShootFeedback(BuildConfig.FLAVOR_vest + "_" + "ScreenShootFeedback", "截图问题反馈"),


    CardInput(BuildConfig.FLAVOR_vest + "_" + "CardInput", "输入留言卡内容"),
    CardGenerate(BuildConfig.FLAVOR_vest + "_" + "CardGenerate", "生成留言海报"),
    CardSaveToPhone(BuildConfig.FLAVOR_vest + "_" + "CardSaveToPhone", "留言海报保存手机"),
    Login(BuildConfig.FLAVOR_vest + "_" + "Login", "游客状态—登陆"),
    Logout(BuildConfig.FLAVOR_vest + "_" + "Logout", " 登陆后—退出登录"),
    LogoutConfirm(BuildConfig.FLAVOR_vest + "_" + "LogoutConfirm", "退出当前帐号—确定"),
    LogoutCancel(BuildConfig.FLAVOR_vest + "_" + "LogoutCancel", "退出当前帐号——取消"),
    AboutNewAccount(BuildConfig.FLAVOR_vest + "_" + "AboutNewAccount", "关联新账号"),
    AboutOldAccount(BuildConfig.FLAVOR_vest + "_" + "AboutOldAccount", "关联老账号");


    override fun toString(): String {
        return "key: $key value: $value"
    }
}
