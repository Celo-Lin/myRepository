package com.juan.adx.task.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * @Description 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }

    /**
     * @Description 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * @Description 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * @Description 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBean(clazz);
    }
    
    /**
     * @Description 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        Assert.notNull(applicationContext, "applicationContext未注入,请在xml中定义BeanUtil");
    }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
