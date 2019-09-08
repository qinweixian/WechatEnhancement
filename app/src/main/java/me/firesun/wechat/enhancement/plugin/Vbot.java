package me.firesun.wechat.enhancement.plugin;


import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.firesun.wechat.enhancement.Main;
import me.firesun.wechat.enhancement.PreferencesUtils;
import me.firesun.wechat.enhancement.util.HookParams;


public class Vbot implements IPlugin {

    static Object chattingObj;

    @Override
    public void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        
        XposedBridge.hookAllConstructors(HookParams.getInstance().ChattingClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    //保存构造函数生成的对象
                    Vbot.chattingObj = param.thisObject;
                    XposedBridge.log("聊天构造方法hook成功");
                } catch (Error | Exception e) {
                }
            }
        });
    }

    /**
     * 发送消息
     *
     * @param msg
     * @return
     */
    public static boolean sendTxt(String msg) {
        try {
            XposedBridge.log("发送消息:" + msg);
            Method sendMethod = HookParams.getInstance().ChattingClass.getMethod(HookParams.getInstance().ChattingTxtMethod, new Class[]{String.class});
            sendMethod.invoke(Vbot.chattingObj, new Object[]{msg});
            return true;
        } catch (Error | Exception e) {
            return false;
        }
    }
}
