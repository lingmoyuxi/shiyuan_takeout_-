package com.shiyuan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtil {

	/**
	 * ��ȡ��Ŀ·�� ��Ŀ���ص�ַ/WebContent/WEB-INF/ <br> ����<br>Ĭ��·��
	 * //D:\software\eclipse\jee-2019-062\data\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\��Ŀ����\<br>
	 * ��·�������޸� ��ʹ�価���ܴ洢����Ŀ�ڣ�������
	 * .metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps ��ʱ�ļ�����
	 * @param request
	 * @return
	 */
	public static String getPath(HttpServletRequest request) {
		String p = request.getServletContext().getRealPath("WebContent/WEB-INF/");
		int startIndex = p.indexOf(".metadata");
		int endIndex = p.lastIndexOf("\\wtpwebapps\\") + "\\wtpwebapps\\".length();
		String path = p.replace(p.substring(startIndex, endIndex), "");
		return path;
	}

	/**
	 * ��ȡ�ļ�����Ŀ¼
	 * 
	 * @param file CommonsMultipartFile �ļ���
	 * @return �ļ�����Ŀ¼
	 */
	public static String getFileTypePath(CommonsMultipartFile file) {
		// �ļ�������ͷ���
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("resource/");
		if (file.getContentType().startsWith("image")) { // �����ͼƬ�ļ��������image�ļ�����
			stringBuffer.append("image/");
		} else if (file.getContentType().startsWith("audio")) { // �������Ƶ�ļ��������audio�ļ�����
			stringBuffer.append("audio/");
		} else { // �������������ָ�������ͣ������others�ļ�����
			stringBuffer.append("others/");
		}
		return stringBuffer.toString();
	}

	/**
	 * �����ļ� 
	 * @param source Դ�ļ�
	 * @param dest  Ŀ���ļ�
	 */
	public static void copyFile(final File source,final File dest) {
		new Runnable() {
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				copyFileUsingFileChannels(source, dest);
				System.out.println("�¼����ļ�-->" + dest.getPath());
			}
		}.run();
	}
	/**
	 * �����ļ�
	 * @param source Դ�ļ�
	 * @param filePath Ŀ���ļ�
	 */
	public static void copyFile(final File source,final String filePath) {
		new Runnable() {
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				File copyFile= copyFileUsingFileChannels(source, filePath);
				System.out.println("�¼����ļ�-->" + copyFile.getPath());
			}
		}.run();
	}

	/**
	 * �����ļ��������������Ŀ¼
	 * @param fileName �ļ���
	 * @return File
	 */
	public static File creatFileAndDir(String fileName) {
		File file = new File(fileName);
		// ����ļ��в������򴴽�
		if (!file.exists() && !file.isDirectory()) {
//						dest.mkdirs();
			file.getParentFile().mkdirs();
		}
		return file;

	}
	
	/**
	 * ɾ���ļ�
	 * @param file
	 */
	public static void deleteFile(final File file) {
		new Runnable() {
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				if(file.exists() && !file.isDirectory()) {
					file.delete();
				}
			}
		}.run();
	}
	
	/**
	 * ɾ���ļ�
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		deleteFile(new File(filePath));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void copyFileUsingFileChannels(File source, File dest) {
		try {
			FileChannel inputChannel = null;
			FileChannel outputChannel = null;
			// ����ļ��в������򴴽�
			if (!dest.exists() && !dest.isDirectory()) {
//				dest.mkdirs();
				dest.getParentFile().mkdirs();
			}
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(dest);
			try {
				inputChannel = fis.getChannel();
				outputChannel = fos.getChannel();
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			} finally {
				fis.close();
				fos.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static File copyFileUsingFileChannels(final File source, final String filePath) {
		File creatFileAndDir = creatFileAndDir(filePath);
		copyFileUsingFileChannels(source, creatFileAndDir);
		return creatFileAndDir;
	}
}
