/*
 * Copyright (c) 2016-present. 贵州纳雍穿青人李裕江 and All Contributors.
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package com.github.gzuliyujiang.oaid;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.github.gzuliyujiang.oaid.impl.OAIDFactory;

import java.security.MessageDigest;

/**
 *
 * @author 大定府羡民（1032694760@qq.com）
 * @since 2020/5/30
 */
@SuppressWarnings("ALL")
public final class DeviceID {
    private String oaid;

    /**
     * 在应用启动时预取客户端标识及OAID，客户端标识按优先级尝试获取IMEI/MEID、OAID/AAID、AndroidID、GUID。
     * !!注意!!：若最终用户未同意隐私政策，或者不需要用到{@link #getClientId()}及{@link #getOAID}，请不要调用这个方法
     *
     * @param application 全局上下文
     * @see Application#onCreate()
     */
    public static void register(Application application) {
        register(application, null);
    }

    /**
     * 在应用启动时预取客户端标识及OAID，客户端标识按优先级尝试获取IMEI/MEID、OAID/AAID、AndroidID、GUID。
     * !!注意!!：若最终用户未同意隐私政策，或者不需要用到{@link #getClientId()}及{@link #getOAID}，请不要调用这个方法
     *
     * @param application 全局上下文
     * @param callback    注册完成回调
     * @see Application#onCreate()
     */
    public static void register(Application application, IRegisterCallback callback) {
        if (application == null) {
            if (callback != null) {
                callback.onComplete("", new RuntimeException("application is nulll"));
            }
            return;
        }
        getOAIDOrOtherId(application, callback);
    }

    private static void getOAIDOrOtherId(Application application, IRegisterCallback callback) {
        getOAID(application, new IGetter() {
            @Override
            public void onOAIDGetComplete(String result) {
                if (TextUtils.isEmpty(result)) {
                    onOAIDGetError(new OAIDException("OAID is empty"));
                    return;
                }
                Holder.INSTANCE.oaid = result;
                OAIDLog.print("Client id is OAID: " + result);
                if (callback != null) {
                    callback.onComplete(result, null);
                }
            }

            @Override
            public void onOAIDGetError(Exception error) {
                if (callback != null) {
                    callback.onComplete("", error);
                }
            }
        });
    }

    /**
     * 使用该方法获取OAID，需要先在{@link Application#onCreate()}里调用{@link #register(Application)}预取
     *
     * @see #register(Application)
     */
    public static String getOAID() {
        String oaid = Holder.INSTANCE.oaid;
        if (oaid == null) {
            oaid = "";
        }
        return oaid;
    }

    /**
     * 异步获取OAID，如果使用该方法获取OAID，请不要调用{@link #register(Application)}进行预取
     *
     * @param context 上下文
     * @param getter  回调
     */
    public static void getOAID(Context context, IGetter getter) {
        IOAID ioaid = OAIDFactory.create(context);
        OAIDLog.print("OAID implements class: " + ioaid.getClass().getName());
        ioaid.doGet(getter);
    }

    /**
     * 判断设备是否支持 OAID 或 AAID 。大多数国产系统需要 Android 10+ 才支持获取 OAID，需要安卓 Google Play Services 才能获取 AAID。
     *
     * @param context 上下文
     * @see #getOAID(Context, IGetter)
     */
    public static boolean supportedOAID(Context context) {
        return OAIDFactory.create(context).supported();
    }

    /**
     * 计算哈希值，算法可以是MD2、MD5、SHA-1、SHA-224、SHA-256、SHA-512等，支持的算法见
     * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest
     */
    public static String calculateHash(String str, String algorithm) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            byte[] data = str.getBytes();
            byte[] bytes = MessageDigest.getInstance(algorithm).digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(String.format("%02x", aByte));
            }
            return sb.toString();
        } catch (Exception e) {
            OAIDLog.print(e);
            return "";
        }
    }

    private static class Holder {
        static final DeviceID INSTANCE = new DeviceID();
    }

    private DeviceID() {
        super();
    }

}
