/**
 * File Name:GitUtil.java
 * Package Name:com.ht.maven.plugin.git
 * Date:2017-4-16上午12:02:25
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
 * 1.1下载主仓  cloneRepository("https://github.com/NaNyoyo/yoyo.git","d:\\tmp\\tit3");
 *
 * 1.2下载子仓 cloneSubRepository("d:\\tmp\\tit3", "zicang/新建文件夹");
 * 
 * 2、pull（主仓+子仓（部分））
 * pullRepository("d:\\tmp\\tit3");
 * pullSubRepository("d:\\tmp\\tit3","\\zicang/新建文件夹");
 *
 * 3.代码提交（主仓+子仓（部分））
 * commitRepository("d:\\tmp\\tit3", "345.txt","测试体骄傲");
 * commitSubRepository("d:\\tmp\\tit3", "\\zicang/新建文件夹", "123/123.txt","你大爷");
 *
 * 4.push（主仓+子仓（部分））
 * pushRepository("d:\\tmp\\tit3");
 * pushSubRepository("d:\\tmp\\tit3","\\zicang/新建文件夹");
 *
 * 5.切换分支（子仓切）
 * switchBranch("d:\\tmp\\tit3","remotes/origin/master");
 * switchSubhBranch("d:\\tmp\\tit3", "\\zicang/新建文件夹", "remotes/origin/newbranch2");
 * fetchBranch("d:\\tmp\\tit3\\.git\\modules"+"\\zicang/新建文件夹");
 * fetchBranch("d:\\tmp\\tit3");
 * switchLocalBranch("d:\\tmp\\tit3", "");
 * switchBranch("d:\\tmp\\tit2", "branch1");
 * getLocalBranchNames("d:\\tmp\\tit3");
 * getRemoteBranchNames("d:\\tmp\\tit3", "origin");
 * getChangedFiles("d:\\tmp\\tit3");
 *
 */

package com.ht.maven.plugin.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.SubmoduleInitCommand;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
//import org.eclipse.jgit.transport.CredentialsProvider;

/**
 * ClassName:GitUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-4-16 上午12:02:25 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class GitUtil {
	 public static void log(String localPath){
	        try {
	            Git git = Git.open(new File(localPath));
	            LogCommand logCommand=git.log();
	            Iterable<RevCommit> list=logCommand.call();
	            for (RevCommit revCommit : list) {
	                System.out.println(revCommit.getFullMessage());
	                System.out.println(revCommit.getName());
	            }
	        }catch(Exception e){
	             
	        }
	    }
	     
	    public static void getChangedFiles(String localPath){
	        try {
	            Git git = Git.open(new File(localPath));
	            StatusCommand statusCommand=git.status();
	            org.eclipse.jgit.api.Status status= statusCommand.call();
	            Map<String, Set<String>> result=new HashMap<String, Set<String>>();
	            Set<String> addedSet=status.getAdded();
	            result.put("added", addedSet);
	            Set<String> changedSet=status.getChanged();
	            result.put("changed", changedSet);
	            Set<String> missSet=status.getMissing();
	            result.put("missed", missSet);
	            System.out.println(result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	     
	    /**
	     * fetch
	     * @param localPath
	     * @param branch
	     * @return
	     */
	    public static String fetchBranch(String localPath){
	        try {
	            Git git = Git.open(new File(localPath));
	            git.fetch().call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 切换分支<br>
	     * 首先判断本地是否已有此分支
	     * @param localPath 主仓
	     * @return
	     */
	    public static String switchBranch(String localPath, String branch) {
	        try {
	            Git git = Git.open(new File(localPath));
	            String newBranch = branch.substring(branch.lastIndexOf("/")+1,branch.length());
	            CheckoutCommand checkoutCommand = git.checkout();
	            List<String> list=getLocalBranchNames(localPath);
	            if(!list.contains(newBranch)){//如果本地分支
	                checkoutCommand.setStartPoint(branch);
	                checkoutCommand.setCreateBranch(true);
	            }
	            checkoutCommand.setName(newBranch);
	            checkoutCommand.call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	     
	     
	    /**
	     * 切换子仓的分支
	     * @param localPath 主仓
	     * @param sub 子仓
	     * @param branch 分支名
	     * @return
	     */
	    public static String switchSubhBranch(String localPath, String sub,String branch) {
	        try {
	            Git git = Git.open(new File(localPath+"\\.git\\modules"+sub));
	            String newBranch = branch.substring(branch.lastIndexOf("/")+1,branch.length());
	            CheckoutCommand checkoutCommand = git.checkout();
	            List<String> list=getLocalBranchNames(localPath+"\\.git\\modules"+sub);
	            if(!list.contains(newBranch)){//如果本地分支
	                checkoutCommand.setStartPoint(branch);
	                checkoutCommand.setCreateBranch(true);
	                checkoutCommand.setForce(true);
	            }
	            checkoutCommand.setName(newBranch);
	            checkoutCommand.call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	     
	     
	    /**
	     * 拿到当前本地分支名
	     * @param localPath 主仓
	     * @return
	     * @throws IOException
	     */
	    public static String getCurrentBranch(String localPath) throws IOException{
	            Git git = Git.open(new File(localPath));
	            return git.getRepository().getBranch();
	    }
	     
	    /**
	     * 拿到当前远程分支名
	     * @param localPath 主仓
	     * @return
	     * @throws IOException
	     */
	    public static String getCurrentRemoteBranch(String localPath) throws IOException{
	        Git git = Git.open(new File(localPath));
	        StoredConfig storedConfig = git.getRepository().getConfig();
	        String currentRemote=storedConfig.getString("branch",getCurrentBranch(localPath), "remote");
	        return currentRemote;
	    }
	     
	     
	    /**
	     * 获取所有远程
	     * @param localPath 主仓
	     * @return
	     * @throws IOException
	     * @throws GitAPIException
	     */
	    public static List<String> getRemotes(String localPath) throws IOException, GitAPIException{
	        Git git = Git.open(new File(localPath));
	        RemoteListCommand remoteListCommand=git.remoteList();
	        List<RemoteConfig> list=remoteListCommand.call();
	        List<String> result=new LinkedList<String>();
	        for (RemoteConfig remoteConfig : list) {
	            result.add(remoteConfig.getName());
	        }
	        return result;
	    }
	     
	     
	    /**
	     * 获取本地所有分支名
	     * @param localPath
	     * @return
	     * @throws IOException
	     */
	    public static List<String> getLocalBranchNames(String localPath) throws IOException{
	        List<String> result=new LinkedList<String>();
	        Git git = Git.open(new File(localPath));
	        Map<String, Ref> map=git.getRepository().getAllRefs();
	        Set<String> keys=map.keySet();
	        for (String str : keys) {
	            if(str.indexOf("refs/heads")>-1){
	                String el=str.substring(str.lastIndexOf("/")+1, str.length());
	                result.add(el);
	            }
	        }
	        return result;
	    }
	     
	    /**
	     * 根据名称获取所有远程分支
	     * @param localPath 主仓
	     * @param name 远程名字
	     * @return
	     * @throws IOException
	     */
	    public static List<String> getRemoteBranchNames(String localPath,String remote) throws IOException{
	        List<String> result=new LinkedList<String>();
	        Git git = Git.open(new File(localPath));
	        Map<String, Ref> map=git.getRepository().getAllRefs();
	        Set<String> keys=map.keySet();
	        String index="refs/remotes/"+remote;
	        for (String str : keys) {
	            if(str.indexOf(index)>-1){
	                //String el=str.substring(str.lastIndexOf("/")+1, str.length());
	                result.add(str);
	                System.out.println(str);
	            }
	        }
	        return result;
	    }
	     
	     
	    /**
	     * 切换本地分支
	     * @param localPath
	     * @param branch
	     * @return
	     */
	    public static String switchLocalBranch(String localPath, String branch){
	        try {
	            Git git = Git.open(new File(localPath));
	            Repository repository=git.getRepository();
	            ListBranchCommand listBranchCommand=git.branchList();
	            List<Ref> ll=listBranchCommand.setListMode(ListMode.ALL).call();
	            for (Ref ref : ll) {
	                System.out.println(ref.getObjectId().toString());
	            }
	             
	            System.out.println(">>>>>>>>>>>>>>>>>>>>");
	            RemoteListCommand remoteListCommand=git.remoteList();
	            List<RemoteConfig> list=remoteListCommand.call();
	            for (RemoteConfig remoteConfig : list) {
	                System.out.println(remoteConfig.getName());
	            }
	             
	            System.out.println(">>>>>>>>>>>>>>>>>>>");
	            //得到仓库本地分支
	            String currentbranch=repository.getBranch();
	             
	            LsRemoteCommand lsRemoteCommand=git.lsRemoteRepository();
	            Collection<Ref> collection=lsRemoteCommand.call();
	            for (Ref ref : collection) {
	                System.out.println(ref.getName());
	            }
	             
	            System.out.println(">>>>>>>>>>>>>>>>>>>>");
	            Map<String, Ref> map=repository.getAllRefs();
	            Set<String> keys=map.keySet();
	            for (String string : keys) {
	                System.out.println(string);
	            }
	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	            Collection<Ref> values=map.values();
	            for (Ref ref : values) {
	                Storage storage=ref.getStorage();
	                System.out.println(ref.getName());
	            }
	            System.out.println(">>>>>>>>>>>>>>>>>>>");
	             
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	    /**
	     * push到远程仓库<br>
	     * @param string
	     * @param string2
	     * @param string3
	     * @return
	     */
	    public static String pushRepository(String localPath,String userName,String password) {
	        try {
	            Git git = Git.open(new File(localPath));
	            PushCommand pushCommand = git.push();
	            //CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("15862663403@163.com", "yoyosd619");
	            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(userName, password);
	            pushCommand.setCredentialsProvider(credentialsProvider);
	            // pushCommand.add(fileNames);
	            pushCommand.setForce(true).setPushAll();
	            Iterable<PushResult> iterable = pushCommand.call();
	            for (PushResult pushResult : iterable) {
	                System.out.println(pushResult.toString());
	            }
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 远程提交子仓
	     * @param localPath 主仓
	     * @param sub 子仓
	     * @return
	     */
	    public static String  pushSubRepository(String localPath, String sub) {
	        try {
	            String newPath = localPath + "\\.git\\modules";
	            Git git = Git.open(new File(newPath + sub));
	            PushCommand pushCommand = git.push();
	            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider( "15862663403@163.com", "yoyosd619");
	            pushCommand.setCredentialsProvider(credentialsProvider);
	            pushCommand.setForce(true).setPushAll();
	            Iterable<PushResult> iterable = pushCommand.call();
	            for (PushResult pushResult : iterable) {
	                System.out.println(pushResult.toString());
	            }
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	     
	    /**
	     * 提交本地代码
	     * @param localPath 主仓
	     * @param fileNames 文件名集合
	     * @param message 备注
	     * @return
	     */
	    public static String commitRepository(String localPath, String fileNames,
	            String message) {
	        try {
	            Git git = Git.open(new File(localPath));
	            AddCommand addCommand = git.add();
	            String[] fileArr = fileNames.split(",");
	            for (String file : fileArr) {
	                addCommand.addFilepattern(file);
	            }
	            addCommand.call();
	            CommitCommand commitCommand = git.commit();
	            commitCommand.setMessage(message);
	            commitCommand.setAllowEmpty(true);
	            commitCommand.call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * @param localPath 主仓
	     * @param sub 子仓 
	     * @param fileNames 文件名
	     * @param message 消息
	     * @return
	     */
	    public static String commitSubRepository(String localPath, String sub,
	            String fileNames, String message) {
	        try {
	            String newPath = localPath + "\\.git\\modules";
	            Git git = Git.open(new File(newPath + sub));
	 
	            AddCommand addCommand = git.add();
	            String[] fileArr = fileNames.split(",");
	            for (String file : fileArr) {
	                addCommand.addFilepattern(file);
	            }
	            addCommand.call();
	            CommitCommand commitCommand = git.commit();
	            commitCommand.setMessage(message);
	            commitCommand.setAllowEmpty(true);
	            commitCommand.call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 更新主仓
	     * @param localPath 主仓
	     * @return
	     */
	    public static String pullRepository(String localPath) {
	        try {
	            Git git = Git.open(new File(localPath));
	            PullCommand pullCommand = git.pull();
	            pullCommand.call();
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 更新子仓
	     * @param sub  子仓名，多个时用，隔开
	     * @param localPath 主仓
	     * @return
	     */
	    public static String pullSubRepository(String localPath, String sub) {
	        try {
	            String newPath = localPath + "\\.git\\modules";
	            String[] subArr = sub.split(",");
	            for (String path : subArr) {
	                Git git = Git.open(new File(newPath + path));
	                PullCommand pullCommand = git.pull();
	                pullCommand.call();
	            }
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 下载仓库
	     * @param url 远程url
	     * @param localPath 本地主仓路径
	     */
	    public static String cloneRepository(String url, String localPath) {
	    	try {
	            System.out.println("开始下载主仓。。。");
	            CloneCommand cloneCommand = Git.cloneRepository().setURI(url);
	            cloneCommand.setDirectory(new File(localPath)).call();
	            System.out.println("主仓下载完成。。。");
	            return "success";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	 
	    /**
	     * 下载子仓
	     * @param localPath 主仓
	     * @param sub 子仓
	     * @return
	     */
	    public static String cloneSubRepository(String localPath, String sub) {
	        try {
	            File zicang=new File(localPath+"\\.git\\modules\\"+sub);
	            if(zicang.exists()){
	                deleteDir(zicang);
	            }
	            Git git = Git.open(new File(localPath));
	            System.out.println("开始下载子仓。。。");
	            SubmoduleInitCommand submoduleInit = git.submoduleInit();
	            SubmoduleUpdateCommand submoduleUpdate = git.submoduleUpdate();
	            String[] submoduleArr = sub.split(",");
	            for (String s : submoduleArr) {
	                System.out.println("准备下载子仓：" + s);
	                submoduleInit.addPath(s);
	                submoduleUpdate.addPath(s);
	            }
	            submoduleInit.call();
	            submoduleUpdate.call();
	            System.out.println("子仓下载完成。。。");
	            return "";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	 
	    }
	     
	    public static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
	    
	    public static void copyFolder(File src, File dest) {  
		    if (src.isDirectory()) {  
		        if (!dest.exists()) {  
		            dest.mkdir();  
		        }  
		        String files[] = src.list();  
		        for (String file : files) {  
		            File srcFile = new File(src, file);  
		            File destFile = new File(dest, file);  
		            // 递归复制  
		            copyFolder(srcFile, destFile);  
		        }  
		    } else {  
		    	InputStream in=null;
		    	OutputStream out=null;
		        try {
					 in = new FileInputStream(src);  
					 out = new FileOutputStream(dest);  
	  
					byte[] buffer = new byte[1024];  
	  
					int length;  
					  
					while ((length = in.read(buffer)) > 0) {  
					    out.write(buffer, 0, length);  
					}  
					
				} catch (Exception e) {
					e.printStackTrace();
					
				} finally{
					if(in!=null){
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
							
						}  
					}
					if(out!=null){
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
							
						}
					}
				}
		    }  
		}  
}

