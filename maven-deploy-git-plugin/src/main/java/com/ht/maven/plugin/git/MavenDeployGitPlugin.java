/**
 * File Name:HelloWorldMojo.java
 * Package Name:com.ht.maven.plugin.git
 * Date:2017-4-15下午9:20:34
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.ht.maven.plugin.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal deploy2git
 * @phase deploy
 * @requiresProject false
 */
public class MavenDeployGitPlugin extends AbstractMojo {
	/**
	 * @parameter expression="${project.build.directory}"
	 */
	String target;//构建目录 缺省为target
	/*
	 * @parameter expression="${project.build.finalName}"
	 */
	String file;//产出物名称，缺省为${project.artifactId}-${project.version}
	
	/**
	 * @parameter expression="${project.version}"
	 */
	String version;
	/**
	 * @parameter expression="${project.groupId}"
	 */
	String groupId;
	/**
	 * @parameter expression="${project.artifactId}"
	 */
	String artifactId;
	/**
	 * @parameter expression="${project.packaging}"
	 */
	String packaging;
	
	/**
	 * @parameter expression="${git.url}"
	 * @required
	 */
	String url;
	/**
	 * @parameter expression="${git.user}"
	 */
	String user;
	/**
	 * @parameter expression="${git.password}"
	 */
	String password;
	/**
	 * @parameter expression="${git.child}"
	 */
	String child;
	
	static MessageDigest md = null;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ne) {
		}
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		if( file == null ) {
		file=artifactId+"-"+version;
		}

		getLog().info("[-发布git仓库插件执行器开始]"+file);
		String dir="/"+getMd5("mavenDeployGitPlugin")+"/"+getMd5(url);
		if(!new File(dir).exists()){
			//getLog().info("1111111111111");
			GitUtil.cloneRepository(url, dir);
		}else {
		}
		//getLog().info("22222222222222222");
		if(null!=GitUtil.pullRepository(dir)){
			getLog().info("本地git仓库错误["+dir+"]");
		}
		
		String fromFile=null;
		if(packaging.equals("jar")){
			
			fromFile=new File(target+"/"+file).getAbsolutePath()+".jar";
		
		}else if(packaging.equals("maven-plugin")){
			
			fromFile=new File(target+"/"+file).getAbsolutePath()+".jar";
			
		}else if(packaging.equals("pom")){
			
			fromFile=new File(target+"/"+file).getAbsolutePath()+".pom";
		
		}else if(packaging.equals("war")){
			
			fromFile=new File(target+"/"+file).getAbsolutePath()+".war";
		}
		String toUrl= child ==null ? new File(dir).toURI().toString():new File(dir+"/"+child).toURI().toString();
		StringBuffer buffer=new StringBuffer("cmd /c");
		buffer.append(" mvn deploy:deploy-file");
		buffer.append(" -DgroupId="+groupId);
		buffer.append(" -Dversion="+version);
		buffer.append(" -DartifactId="+artifactId);
		buffer.append(" -Dpackaging="+packaging);
		buffer.append(" -Dfile="+fromFile);
		buffer.append(" -Durl="+toUrl);
		getLog().info(buffer);
		  BufferedReader br=null;
		  StringBuffer log=new StringBuffer();
		try {
			Process proc = Runtime.getRuntime().exec(buffer.toString());
			 InputStream stderr =  proc.getInputStream(); // 获取输入流  
	             br = new BufferedReader(new InputStreamReader(stderr));  
	            String line = null;  
	            while ((line = br.readLine()) != null) { // 打印出命令执行的结果  
	                log.append(line);
	            } 
	         getLog().info(log);  
	         //getLog().info("33333333333333");
			GitUtil.commitRepository(dir, ".", "[groupId="+groupId+"][artifactId="+artifactId+"][version="+version+"]");
			GitUtil.pushRepository(dir, user, password);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
			getLog().info("发布git仓库插件执行结束");
		}
	
		
	}
	/**
	 * 用于获取一个String的md5值
	 * 
	 * @param string
	 * @return
	 */
	public static String getMd5(String str) {
		byte[] bs = md.digest(str.getBytes());
		StringBuilder sb = new StringBuilder(40);
		for (byte x : bs) {
			if ((x & 0xff) >> 4 == 0) {
				sb.append("0").append(Integer.toHexString(x & 0xff));
			} else {
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}
	
	public MavenDeployGitPlugin(){
		
	}
	public MavenDeployGitPlugin(String target, String file, String version, String groupId, String artifactId,
			String packaging, String url, String user, String password, String child) {
		super();
		this.target = target;
		this.file = file;
		this.version = version;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.packaging = packaging;
		this.url = url;
		this.user = user;
		this.password = password;
		this.child = child;
	}
	public static void main(String[] args) throws Exception {
		
		File[] fs = new File("E:/temp").listFiles();
		for (File f : fs) {
			String file=f.getName().substring(0,f.getName().lastIndexOf(".")); 
			String version="4.1.0"; 
			String groupId="com.ht.rocketmq"; 
			String artifactId=f.getName().substring(0,f.getName().indexOf("-4.1.0-")); 
			MavenDeployGitPlugin deployGitPlugin=new MavenDeployGitPlugin("E:/temp",
					file, version, groupId, artifactId, "jar", "https://github.com/soulCalibur/maven/", 
					"soulCalibur", "-----------", "repo");
			deployGitPlugin.execute();
		}
	}
}
//[INFO] cmd /c mvn deploy:deploy-file -DgroupId=com.ht.maven.plugin -Dversion=1.0.0 -DartifactId=git-deploy -Dpackaging=maven-plugin -Dfile=E:\work\ht_common\maven-plugin\git-deploy\target\null.jar -Durl=file:/E:/927e833d02ad18be7818d8ae8836d187/449b9e68c0a982650b97a3767c8b778d/repo/