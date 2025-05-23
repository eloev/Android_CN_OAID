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

package com.github.gzuliyujiang.oaid.impl;

import android.app.Application;
import android.content.Context;

import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDLog;
import com.github.gzuliyujiang.oaid.OAIDRom;

/**
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/4/28 20:32
 */
public final class OAIDFactory {
    private static IOAID ioaid;

    private OAIDFactory() {
        super();
    }

    public static IOAID create(Context context) {
        if (context != null && !(context instanceof Application)) {
            // See https://github.com/gzu-liyujiang/Android_CN_OAID/pull/23
            context = context.getApplicationContext();
        }
        if (ioaid != null) {
            return ioaid;
        }
        // 优先尝试各厂商自家提供的接口
        ioaid = createManufacturerImpl(context);
        if (ioaid != null && ioaid.supported()) {
            OAIDLog.print("Manufacturer interface has been found: " + ioaid.getClass().getName());
            return ioaid;
        }
        // 再尝试移动安全联盟及谷歌服务框架提供的接口
        ioaid = createUniversalImpl(context);
        return ioaid;
    }

    private static IOAID createManufacturerImpl(Context context) {
        if (OAIDRom.isLenovo() || OAIDRom.isMotolora()) {
            return new LenovoImpl(context);
        }
        if (OAIDRom.isMeizu()) {
            return new MeizuImpl(context);
        }
        if (OAIDRom.isNubia()) {
            return new NubiaImpl(context);
        }
        if (OAIDRom.isXiaomi() || OAIDRom.isMiui() || OAIDRom.isBlackShark()) {
            return new XiaomiImpl(context);
        }
        if (OAIDRom.isSamsung()) {
            return new SamsungImpl(context);
        }
        if (OAIDRom.isVivo()) {
            return new VivoImpl(context);
        }
        if (OAIDRom.isASUS()) {
            return new AsusImpl(context);
        }
        if (OAIDRom.isHonor()) {
            HonorImpl honor = new HonorImpl(context);
            if (honor.supported()) {
                // 支持的话（Magic UI 4.0,5.0,6.0及MagicOS 7.0或以上）直接使用荣耀的实现，否则尝试华为的实现
                return honor;
            }
        }
        if (OAIDRom.isHuawei() || OAIDRom.isEmui()) {
            return new HuaweiImpl(context);
        }
        if (OAIDRom.isOppo() || OAIDRom.isOnePlus()) {
            OppoImpl oppo = new OppoImpl(context);
            if (oppo.supported()) {
                return oppo;
            }
            return new OppoExtImpl(context);
        }
        if (OAIDRom.isCoolpad(context)) {
            return new CoolpadImpl(context);
        }
        if (OAIDRom.isCoosea()) {
            return new CooseaImpl(context);
        }
        if (OAIDRom.isFreeme()) {
            return new FreemeImpl(context);
        }
        if (OAIDRom.is360OS()) {
            return new QikuImpl(context);
        }
        return null;
    }

    private static IOAID createUniversalImpl(Context context) {
        // 若各厂商自家没有提供接口，则优先尝试移动安全联盟的接口
        // If the vendors do not provide their own interfaces, the Mobile Security Alliance interface will be used first.
        IOAID ioaid = new MsaImpl(context);
        if (ioaid.supported()) {
            OAIDLog.print("Mobile Security Alliance has been found: " + ioaid.getClass().getName());
            return ioaid;
        }
        // 默认不支持
        // Not supported by default
        ioaid = new DefaultImpl();
        OAIDLog.print("OAID/AAID was not supported: " + ioaid.getClass().getName());
        return ioaid;
    }

}
