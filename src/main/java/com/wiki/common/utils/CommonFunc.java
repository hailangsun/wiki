package com.wiki.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public final class CommonFunc {

    /**
     * 产生ID
     *
     * @return ID
     */
    public static final String generateID(int i) {
        String id = System.currentTimeMillis() + "" + mathRandom(i);
        return id;
    }

    /**
     * 拿到指定位数的随机数（String）
     *
     * @param len
     * @return
     */
    public static String getRandom(int len) {
        if (len < 1) {
            return "0";
        }
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int nextInt = r.nextInt(10);
            rs.append(nextInt == 0 ? 1 : nextInt);
        }
        return rs.toString();
    }


    public static int getCurrentYear() // 当前年
    {
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        return calendar.get(Calendar.YEAR);

    }

    /**
     * 产生指定长度的数学随机数
     *
     * @param length 随机数长度
     * @return 随机数
     */
    public static final String mathRandom(final long length) {
        double len = Math.pow(10D, length);
        long result = (long) (len * Math.random());
        // 补齐随机数长度
        if (result == 0) {
            result = (long) (len * Math.random());
        }
        while (len / result > 10) {
            result *= 10;
        }
        return Long.toString(result);
    }


    public static Map<String, Object> javaBeanMap(Object javaBean) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = javaBean.getClass().getMethods(); // 获取所有方法
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                String field = method.getName(); // 拼接属性名
                field = field.substring(field.indexOf("get") + 3);
                field = field.toLowerCase().charAt(0) + field.substring(1);
                Object value = null; // 执行方法
                try {
                    value = method.invoke(javaBean, (Object[]) null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                map.put(field, value);
            }
        }
        return map;
    }

}