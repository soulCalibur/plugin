import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/*
 * @Project Name: lib
 * @File Name: PluginFileCreate.java
 * @Package Name: 
 * @Date: 2017-4-11上午10:21:45
 * @Creator: bb.h
 * @line------------------------------
 * @修改人: 
 * @修改时间: 
 * @修改内容: 
 */
/**
 * @description TODO
 * @author bb.h
 * @date 2017-4-11上午10:21:45
 * @see
 */
public class PluginFileCreate {
	
	public static void main(String[] args) {
		//path=../eclipse_plugin/git
		String path="拷贝此目录内容到eclipse插件dropins目录下";
		String outStart=null;
		String selfDir=null;
		File directory = new File("");
		File target = new File(path);
		if(target.exists()){
			deleteDirectory(target.getAbsolutePath());
		}
		selfDir=directory.getAbsolutePath().replaceAll("\\\\", "/");
		if(args!=null&&args.length>0){
			outStart=args[0].replaceAll("\\\\", "/");
		 }
		File[] data = new File(directory.getAbsolutePath()).listFiles();
		target.mkdir();
		/////////////////////////
		
		for (File file : data) {
			if(file.isDirectory()&&!file.getName().startsWith("remove-")){
				FileOutputStream fileOutputStream=null;
				try {
					 fileOutputStream=new FileOutputStream(path+"/"+file.getName()+".link");
					 String content="path="+selfDir+"/"+file.getName();
					 if(null!=outStart){
						 content="path="+outStart+"/"+file.getName();
					}
					fileOutputStream.write(content.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					if(null!=fileOutputStream){
						try {
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		}
	}
	 public static boolean deleteFile(String fileName){
	        File file = new File(fileName);
	        if(file.isFile() && file.exists()){
	            Boolean succeedDelete = file.delete();
	            if(succeedDelete){
	                System.out.println("删除单个文件"+fileName+"成功！");
	                return true;
	            }
	            else{
	                System.out.println("删除单个文件"+fileName+"失败！");
	                return true;
	            }
	        }else{
	            System.out.println("删除单个文件"+fileName+"失败！");
	            return false;
	        }
	    }


	    public static boolean deleteDirectory(String dir){
	        //如果dir不以文件分隔符结尾，自动添加文件分隔符
	        if(!dir.endsWith(File.separator)){
	            dir = dir+File.separator;
	        }
	        File dirFile = new File(dir);
	        //如果dir对应的文件不存在，或者不是一个目录，则退出
	        if(!dirFile.exists() || !dirFile.isDirectory()){
	            System.out.println("删除目录失败"+dir+"目录不存在！");
	            return false;
	        }
	        boolean flag = true;
	        //删除文件夹下的所有文件(包括子目录)
	        File[] files = dirFile.listFiles();
	        for(int i=0;i<files.length;i++){
	            //删除子文件
	            if(files[i].isFile()){
	                flag = deleteFile(files[i].getAbsolutePath());
	                if(!flag){
	                    break;
	                }
	            }
	            //删除子目录
	            else{
	                flag = deleteDirectory(files[i].getAbsolutePath());
	                if(!flag){
	                    break;
	                }
	            }
	        }

	        if(!flag){
	            System.out.println("删除目录失败");
	            return false;
	        }

	        //删除当前目录
	        if(dirFile.delete()){
	            System.out.println("删除目录"+dir+"成功！");
	            return true;
	        }else{
	            System.out.println("删除目录"+dir+"失败！");
	            return false;
	        }
	    }
}
