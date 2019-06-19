package com.hsy.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

/**
 * 
 * @author 张梓枫
 * @Description xml与对象映射
 * @date: 2019年1月2日 上午10:01:00
 */
public final class XmlUtils {

	/**
	 * 将javaBean序列化为xml
	 * 
	 * @param clz
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String toXml(Class<?> clz, Object obj, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(clz, encoding, false).marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author 张梓枫
	 * @param clz
	 * @param obj
	 * @return
	 * @return String
	 * @throws Exception
	 * @desc javaBean序列化为xml,默认编码为utf-8
	 */
	public static String toXml(Class<?> clz, Object obj, boolean bool) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(clz, "UTF-8", bool).marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toXml(Class<?> clz, Object obj) {
		return toXml(clz, obj, false);
	}

	/**
	 * 将Xml字符串序列化为javaBean
	 * 
	 * @param clz
	 * @param xml
	 * @return
	 */
	public static Object fromXml(Class<?> clz, String xml) {
		try {
			StringReader reader = new StringReader(xml);
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clz });
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将xml文件转为对象
	 * 
	 * @param clz
	 * @param xmlPath xml文件路径
	 * @return
	 */
	public static Object xmlToBean(Class<?> clz, String xmlPath) {
		try {
			JAXBContext context = JAXBContext.newInstance(clz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object object = unmarshaller.unmarshal(new File(xmlPath));
			return object;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

	private static Marshaller createMarshaller(Class<?> clz, String encoding, boolean bool) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clz });

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if (bool) {
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			}
			if (ObjectUtils.isNotEmpty(encoding)) {
				marshaller.setProperty("jaxb.encoding", encoding);
			}
			marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
				public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer) throws IOException {
					writer.write(ch, start, length);
				}
			});
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
