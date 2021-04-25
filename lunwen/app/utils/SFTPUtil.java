package utils;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class SFTPUtil {
	
	public static final String host = "10.246.147.5";
	public static final  int port = 22;
	public static final  String username = "root";
	public static final  String password = "root";
	public static final  String directory = "/export/mksysb";//路径：/data/backup/168
	public static String uploadFile = "D:/tomc/apache-tomcat-7.0.55.tar.gz";//路径：d:/cc.jpg
	private ChannelSftp sftp = null;
	
	public ChannelSftp connect(String host, int port, String username,String password) {  
//        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftp;
    }
	
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
     * Disconnect with server
     */
    public void disconnect() {
        if(this.sftp != null){
            if(this.sftp.isConnected()){
                this.sftp.disconnect();
                System.out.println("sftp is closed now!");
            }else if(this.sftp.isClosed()){
                System.out.println("sftp is closed already");
            }
        }

    }
    
	public static void main(String[] args) throws Exception {
		SFTPUtil sf = new SFTPUtil();
		ChannelSftp  sftp = sf.connect(host, port, username, password);//获取连接
		sf.upload(directory, uploadFile, sftp);//上传文件
		sf.disconnect();
    }
	
	

}
