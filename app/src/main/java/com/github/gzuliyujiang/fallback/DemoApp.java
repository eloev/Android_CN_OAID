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

package com.github.gzuliyujiang.fallback;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IRegisterCallback;
import com.github.gzuliyujiang.oaid.OAIDLog;

/**
 * @author 大定府羡民（1032694760@qq.com）
 * @since 2020/5/20
 */
public class DemoApp extends Application {
    private boolean privacyPolicyAgreed = false;

    static {
        // 开启日志打印，默认是关闭的，启动本应用会打印如下类似的日志：
        // IMEI/MEID not allowed on Android 10+
        // android.content.pm.PackageManager$NameNotFoundException: com.mdid.msa
        // Google Play Service has been found: com.github.gzuliyujiang.oaid.impl.GmsImpl
        // Service has been bound: Intent { act=com.google.android.gms.ads.identifier.service.START pkg=com.google.android.gms }
        // Service has been connected: com.google.android.gms.ads.identifier.service.AdvertisingIdService
        // OAID/AAID acquire success: 3f398576-c70a-455c-95ab-1fe35a9ae175
        // Client id is OAID/AAID: 3f398576-c70a-455c-95ab-1fe35a9ae175
        // Service has been unbound: com.google.android.gms.ads.identifier.service.AdvertisingIdService
        OAIDLog.enable();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        privacyPolicyAgreed = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注意APP合规性，若最终用户未同意隐私政策则不要调用
        if (privacyPolicyAgreed) {
            //DeviceIdentifier.register(this);
            DeviceID.register(this, new IRegisterCallback() {
                @Override
                public void onComplete(String clientId, Exception error) {
                    // do something
                }
            });
        }
    }

}
