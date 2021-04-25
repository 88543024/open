package controllers;
//
//import java.io.IOException;
//import java.io.InputStream;
//
public class Test {
//	
//	private static FTPClient ftpClient = new FTPClient();
//	private static String encoding = System.getProperty("file.encoding");
//	
//	  /**
//	   * Description: 向FTP服务器上传文件
//	   * @Version1.0
//	   * @param url
//	   * FTP服务器hostname
//	   * @param port
//	   * FTP服务器端口
//	   * @param username
//	   * FTP登录账号
//	   * @param password
//	   * FTP登录密码
//	   * @param path
//	   * FTP服务器保存目录,如果是根目录则为“/”
//	   * @param filename
//	   * 上传到FTP服务器上的文件名
//	   * @param input
//	   * 本地文件输入流
//	   * @return 成功返回true，否则返回false
//	   */
//	
//	public static boolean uploadFile(String url, int port, String username, String password,
//			String path, String filename, InputStream input) {
//		boolean result = false;
//		try {
//			int reply;
//			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
//			ftpClient.connect(url);
//			// ftp.connect(url, port);// 连接FTP服务器
//			// 登录
//			ftpClient.login(username, password);
//			ftpClient.setControlEncoding(encoding);
//			// 检验是否连接成功
//			reply = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				System.out.println("连接失败");
//				ftpClient.disconnect();
//				return result;
//				}
//			
//			// 转移工作目录至指定目录下
//			boolean change = ftpClient.changeWorkingDirectory(path);
//			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//			if(change) {
//				result = ftpClient.storeFile(new String(filename.getBytes(encoding),"iso-8859-1"), input);
//				if(result) {
//					System.out.println("上传成功!");
//				}
//			}
//			input.close();
//			ftpClient.logout();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			if(ftpClient.isConnected()){
//				try {
//					ftpClient.disconnect();
//				} catch (IOException ioe) {
//					ioe.printStackTrace();
//				}
//			}
//		}
//		 return result;
//		
//	}
//	
/*	public static void main(String[] args) {
		System.out.println("1");
		System.out.println("2");
		System.out.println("3");
		System.out.println("4");
		System.out.println("5");

	}*/

}
