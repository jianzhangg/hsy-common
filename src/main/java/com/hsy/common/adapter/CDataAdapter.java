/**
 * 
 */
package com.hsy.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author 张梓枫
 * @date  2019年3月29日下午2:16:15
 * @Description
 */
public class CDataAdapter extends XmlAdapter<String, String>{

    /* 
     * Description: 从xml到javabean的适配方法
     * @param v
     * @return
     * @throws Exception  
     */
    @Override
    public String unmarshal(String v) throws Exception {
        if (v.contains("<![CDATA[")){
            String tmp = v.substring(v.indexOf("<![CDATA["), v.indexOf("]]"));
            return tmp.substring(tmp.lastIndexOf("[")+1);
        }
        return v;
    }

    /* 
     * Description: javabean到xml的适配方法
     * @param v
     * @return
     * @throws Exception  
     */
    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

}
