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
	 * 获取项目路径 项目本地地址/WebContent/WEB-INF/ <br> 例如<br>默认路径
	 * //D:\software\eclipse\jee-2019-062\data\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\项目名称\<br>
	 * 对路径进行修改 ，使其尽可能存储在项目内，而非在
	 * .metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps 临时文件夹中
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
	 * 获取文件分类目录
	 * 
	 * @param file CommonsMultipartFile 文件流
	 * @return 文件分类目录
	 */
	public static String getFileTypePath(CommonsMultipartFile file) {
		// 文件存放类型分类
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("resource/");
		if (file.getContentType().startsWith("image")) { // 如果是图片文件，则放入image文件夹内
			stringBuffer.append("image/");
		} else if (file.getContentType().startsWith("audio")) { // 如果是音频文件，则放入audio文件夹内
			stringBuffer.append("audio/");
		} else { // 如果都不是以上指定的类型，则放入others文件夹内
			stringBuffer.append("others/");
		}
		return stringBuffer.toString();
	}

	/**
	 * 拷贝文件 
	 * @param source 源文件
	 * @param dest  目标文件
	 */
	public static void copyFile(final File source,final File dest) {
		new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				copyFileUsingFileChannels(source, dest);
				System.out.println("新加入文件-->" + dest.getPath());
			}
		}.run();
	}
	/**
	 * 拷贝文件
	 * @param source 源文件
	 * @param filePath 目标文件
	 */
	public static void copyFile(final File source,final String filePath) {
		new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				File copyFile= copyFileUsingFileChannels(source, filePath);
				System.out.println("新加入文件-->" + copyFile.getPath());
			}
		}.run();
	}

	/**
	 * 创建文件并根据情况创建目录
	 * @param fileName 文件名
	 * @return File
	 */
	public static File creatFileAndDir(String fileName) {
		File file = new File(fileName);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
//						dest.mkdirs();
			file.getParentFile().mkdirs();
		}
		return file;

	}
	
	/**
	 * 删除文件
	 * @param file
	 */
	public static void deleteFile(final File file) {
		new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				if(file.exists() && !file.isDirectory()) {
					file.delete();
				}
			}
		}.run();
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		deleteFile(new File(filePath));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void copyFileUsingFileChannels(File source, File dest) {
		try {
			FileChannel inputChannel = null;
			FileChannel outputChannel = null;
			// 如果文件夹不存在则创建
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
