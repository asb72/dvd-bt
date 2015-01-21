package android.tw.john;

import android.os.Handler;
import java.util.ArrayList;
import java.util.Iterator;

public class TWUtil {
    private int mNativeContext;
    private ArrayList<THandler> mTHandler;

    public class THandler {
        Handler mHandler;
        String mTag;

        public THandler(String tag, Handler handler) {
            this.mTag = tag;
            this.mHandler = handler;
        }
    }

    public class TWObject {
        public Object obj3;
        public Object obj4;

        public TWObject(Object _obj3, Object _obj4) {
            this.obj3 = _obj3;
            this.obj4 = _obj4;
        }
    }

    private native void native_close();

    private final native void native_finalize();

    private final native void native_init(int i);

    private native int native_open(short[] sArr, int i);

    private native void native_start();

    private native void native_stop();

    private native int native_write(int i, int i2, int i3, Object obj, Object obj2);

    public TWUtil() {
        this.mNativeContext = 0;
        this.mTHandler = new ArrayList();
        native_init(0);
        if (this.mNativeContext != 0) {
        }
    }

    public TWUtil(int clazz) {
        this.mNativeContext = 0;
        this.mTHandler = new ArrayList();
        native_init(clazz);
        if (this.mNativeContext != 0) {
        }
    }

    protected void finalize() {
        native_finalize();
    }

    public int open(short[] id, int flag) {
        return native_open(id, flag);
    }

    public int open(short[] id) {
        return open(id, 0);
    }

    public void close() {
        native_close();
    }

    public int write(int what, int arg1, int arg2, Object obj3, Object obj4) {
        return native_write(what, arg1, arg2, obj3, obj4);
    }

    public int write(int what) {
        return write(what, 0, 0, null, null);
    }

    public int write(int what, int arg1) {
        return write(what, arg1, 0, null, null);
    }

    public int write(int what, int arg1, int arg2) {
        return write(what, arg1, arg2, null, null);
    }

    public int write(int what, int arg1, int arg2, Object obj) {
        return write(what, arg1, arg2, obj, null);
    }

    public void start() {
        native_start();
    }

    public void stop() {
        native_stop();
    }

    public void addHandler(String tag, Handler handler) {
        synchronized (this.mTHandler) {
            this.mTHandler.add(new THandler(tag, handler));
        }
    }

    public void removeHandler(String tag) {
        synchronized (this.mTHandler) {
            Iterator i$ = this.mTHandler.iterator();
            while (i$.hasNext()) {
                THandler thandler = (THandler) i$.next();
                if (thandler.mTag != null && thandler.mTag.equals(tag)) {
                    this.mTHandler.remove(thandler);
                    break;
                }
            }
        }
    }

    public Handler getHandler(String tag) {
        Iterator i$ = this.mTHandler.iterator();
        while (i$.hasNext()) {
            THandler thandler = (THandler) i$.next();
            if (thandler.mTag != null && thandler.mTag.equals(tag)) {
                return thandler.mHandler;
            }
        }
        return null;
    }

    public void sendHandler(String tag, int what, int arg1, int arg2, Object obj) {
        synchronized (this.mTHandler) {
            try {
                Handler handler = getHandler(tag);
                if (handler != null) {
                    handler.obtainMessage(what, arg1, arg2, obj).sendToTarget();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendHandler(String tag, int what) {
        sendHandler(tag, what, 0, 0, null);
    }

    public void sendHandler(String tag, int what, int arg1) {
        sendHandler(tag, what, arg1, 0, null);
    }

    public void sendHandler(String tag, int what, int arg1, int arg2) {
        sendHandler(tag, what, arg1, arg2, null);
    }
}