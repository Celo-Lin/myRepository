package com.juan.adx.model.dsp.youdao.enums;

import com.juan.adx.model.dsp.yueke.enums.YueKeMacro;
import com.juan.adx.model.entity.api.MacroParamterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/15 16:51
 */
public enum YouDaoMacro {

    DP_WIDTH("__MF_DP_WIDTH__", "__width_logic__", "实际广告位的宽度，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
    WIDTH("__MF_WIDTH__", "__width__", "实际广告位的宽度，物理像素，单位：像素"),

    POS_UP_Y("__MF_POS_UP_Y__", "__up_y__", "用户手指抬起时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
    POS_UP_X("__MF_POS_UP_X__", "__up_x__", "用户手指抬起时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
    POS_DP_UP_Y("__MF_POS_DP_UP_Y__", "__up_logic_y__", "用户手指抬起时的纵坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
    POS_DP_UP_X("__MF_POS_DP_UP_X__", "__up_logic_x__", "用户手指抬起时的纵坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),

    AIT("__MF_AIT__", "__sld__", "广告交互方式(Ad Interaction Type)：0-正常触屏点击、1-引导滑动点击、2-摇动点击(摇一摇)、3-自定义手势、4-正常触屏点击 或 滑动点击、5-扭动点击(扭一扭)、6-擦除"),

    DP_HEIGHT("__MF_DP_HEIGHT__", "__height_logic__", "实际广告位的高度，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
    HEIGHT("__MF_HEIGHT__", "__height__", "实际广告位的高度，物理像素，单位：像素"),

    ABS_DOWN_Y("__MF_ABS_DOWN_Y__", "__down_y__", "用户手指按下时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
    ABS_DOWN_X("__MF_ABS_DOWN_X__", "__down_x__", "用户手指按下时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
    POS_DP_DOWN_Y("__MF_POS_DP_DOWN_Y__", "__down_logic_y__", "用户手指按下时的纵坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
    POS_DP_DOWN_X("__MF_POS_DP_DOWN_X__", "__down_logic_x__", "用户手指按下时的横坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),

    X_MAX_ACC("__MF_X_MAX_ACC__", "__x_max_acc__", "SLD=2 时必填，用户摇动点击时 x 轴加速度峰值。Android端乘以 100 取整， iOS 端乘以 980 取整"),
    Y_MAX_ACC("__MF_Y_MAX_ACC__", "__y_max_acc__", "SLD=2 时必填，用户摇动点击时 y 轴加速度峰值。Android端乘以 100 取整，iOS 端乘以 980 取整"),
    Z_MAX_ACC("__MF_Z_MAX_ACC__", "__z_max_acc__", "SLD=2 时必填，用户摇动点击时 z 轴加速度峰值。Android端乘以 100 取整，iOS 端乘以 980 取整"),
    TURN_X("__MF_TURN_X__", "__turn_x__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 x轴扭动角度，为-180 到 180 的整数"),
    TURN_Y("__MF_TURN_Y__", "__turn_y__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 y轴扭动角度，为-180 到 180 的整数"),
    TURN_Z("__MF_TURN_Z__", "__turn_z__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 z轴扭动角度，为-180 到 180 的整数"),
    TURN_TIME("__MF_TURN_TIME__", "__turn_time__", "SLD=5 时必填，扭动触发点击时扭动的总时间（单位毫秒，保留整数）。即最后一次监听到 3 个方向扭动角度均小于±5 度，到点击触发的时间");

    @Getter
    @Setter
    private String macro;

    @Getter
    @Setter
    private String dspMacro;

    @Getter
    @Setter
    private String desc;


    private static List<MacroParamterMapping> macroParamterMappings;

    static {
        macroParamterMappings = new ArrayList<MacroParamterMapping>();
        for (YouDaoMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

    YouDaoMacro(String macro, String dspMacro, String desc) {
        this.macro = macro;
        this.dspMacro = dspMacro;
        this.desc = desc;
    }

    /**
     * 返回一个不可被修改的只读列表
     */
    public static List<MacroParamterMapping> getMacroParamterMappings() {
        return Collections.unmodifiableList(macroParamterMappings);
    }


}
