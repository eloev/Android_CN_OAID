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

/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package repeackage.com.android.creator;

/**
 * 本文件代码根据以下AIDL生成，只改包名以便解决和移动安全联盟的SDK冲突问题：
 * <pre>
 *     // IdsSupplier.aidl
 *     package com.android.creator;
 *
 *     interface IdsSupplier {
 *
 *        boolean isSupported();
 *
 *        String getUDID(String str);
 *
 *        String getOAID();
 *
 *        String getVAID();
 *
 *         String getAAID(String str);
 *
 *     }
 * </pre>
 * removed:
 *       String getUDID(String str);
 *       String getVAID();
 *       String getAAID(String str);
 */
@SuppressWarnings("All")
public interface IdsSupplier extends android.os.IInterface {
    /**
     * Default implementation for IdsSupplier.
     */
    public static class Default implements IdsSupplier {
        @Override
        public boolean isSupported() throws android.os.RemoteException {
            return false;
        }

        @Override
        public java.lang.String getOAID() throws android.os.RemoteException {
            return null;
        }

        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IdsSupplier {
        private static final java.lang.String DESCRIPTOR = "com.android.creator.IdsSupplier";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an repeackage.com.android.creator.IdsSupplier interface,
         * generating a proxy if needed.
         */
        public static IdsSupplier asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IdsSupplier))) {
                return ((IdsSupplier) iin);
            }
            return new IdsSupplier.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_isSupported: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isSupported();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                case TRANSACTION_getOAID: {
                    data.enforceInterface(descriptor);
                    java.lang.String _result = this.getOAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IdsSupplier {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public boolean isSupported() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_isSupported, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().isSupported();
                    }
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public java.lang.String getOAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getOAID, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getOAID();
                    }
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static IdsSupplier sDefaultImpl;
        }

        static final int TRANSACTION_isSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getOAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);

        public static boolean setDefaultImpl(IdsSupplier impl) {
            // Only one user of this interface can use this function
            // at a time. This is a heuristic to detect if two different
            // users in the same process use this function.
            if (Stub.Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IdsSupplier getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public boolean isSupported() throws android.os.RemoteException;

    public java.lang.String getOAID() throws android.os.RemoteException;
}
