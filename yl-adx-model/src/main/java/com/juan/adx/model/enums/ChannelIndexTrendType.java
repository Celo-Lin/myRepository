package com.juan.adx.model.enums;

/**
 * 趋势图类型：1-请求数、2-填充数、3-展示数、4-点击数
 */
public enum ChannelIndexTrendType {

    FILL(2, "填充数"),
    
    DISPLAY(3, "展示数"),
	
	;
	
	private int type;
	
	private String desc;
	
	private ChannelIndexTrendType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static ChannelIndexTrendType get(Integer type){
		if(type == null){
			return null;
		}
		for (ChannelIndexTrendType advertType : values()) {
			if(advertType.getType() == type.intValue()){
				return advertType;
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
