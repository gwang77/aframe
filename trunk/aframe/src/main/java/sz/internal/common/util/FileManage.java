package sz.internal.common.util;

import java.io.*;


public final class FileManage
{
	public static final int PARAMETER_ERROR=-1;
	public static final int FILE_NOT_EXIST=-2;
	public static final int READ_ONLY=1;
	public static final int READ_WRITE=2;
	
	public static int getReadWrite(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return PARAMETER_ERROR;
			
		File fp=new File(filepath);
		if(!fp.exists()) return FILE_NOT_EXIST;
		
		if(fp.canWrite()) return READ_WRITE;
		else return READ_ONLY;
	}

	public static boolean checkFileExist(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
			
		File fp=new File(filepath);
		if(!fp.exists()) return false;
		if(!fp.isFile()) return false;
		
		return true;
	}
		
	public static boolean checkFolderExist(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
			
		File fp=new File(filepath);
		if(!fp.exists()) return false;
		if(!fp.isDirectory()) return false;
		
		return true;
	}	
	
	public static boolean createFolder(String folderpath)
	{
		if(folderpath==null || folderpath.trim().length()==0) return false;
			
		File fp=new File(folderpath);
		if(fp.exists()) return false;
		
		return fp.mkdirs();
	}	
	
	public static boolean delete(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
			
		File fp=new File(filepath);
		if(!fp.exists()) return false;
		
		return fp.delete();
	}	

	public static boolean deleteFolder(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
			
		File fp=new File(filepath);

		return deleteFolder(fp);	
	}	

	public static boolean deleteFolder(File file)
	{
		if(file==null) return false;
		
		File[] subfile=file.listFiles();
		for(int i=0;i<subfile.length;i++)
		{
			if(subfile[i].isDirectory())
			{
				if(!deleteFolder(subfile[i])) return false;
			}
			else if(subfile[i].isFile())
			{
				if(!subfile[i].delete()) return false;
			}
		}
		
		return file.delete();	
	}

	public static String readTextFile(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return null;
		try
		{	
			String contents=null;
			RandomAccessFile fp=new RandomAccessFile(filepath,"r");

			String strtmp=fp.readLine();
			while(strtmp!=null)
			{
				if(contents==null)
					contents=strtmp;
				else
					contents=contents + "\n" + strtmp;
				strtmp=fp.readLine();	
			}
			fp.close();
			return contents;
		}	
		catch(Exception e)
		{
			System.out.println("Read File error:"+e.toString());
			return null;
		}
	}

	public static byte[] readFile(String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return null;
		try
		{	
			RandomAccessFile fp=new RandomAccessFile(filepath,"r");
			byte[] contents=new byte[(int)fp.length()];
			
			fp.read(contents);
			fp.close();
			return contents;
		}	
		catch(Exception e)
		{
			System.out.println("Read File error:"+e.toString());
			return null;
		}
	}
	public static byte[] readFile(File filepath)
	{
		if(filepath==null) return null;
		try
		{	
			RandomAccessFile fp=new RandomAccessFile(filepath,"r");
			byte[] contents=new byte[(int)fp.length()];
			
			fp.read(contents);
			fp.close();
			return contents;
		}	
		catch(Exception e)
		{
			System.out.println("Read File error:"+e.toString());
			return null;
		}
	}

	public static boolean saveFile(String str,String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
		String strTmp=str;
		if(strTmp==null) strTmp="";
		try
		{
			File file=new File(filepath);
			RandomAccessFile rdfile=new RandomAccessFile(file, "rw"); 
	
			rdfile.write(strTmp.getBytes());
			rdfile.close();
		}
		catch(Exception e)
		{
			System.out.println("save File error:"+e.toString());
			return false;
		}
		return true;
	}
	
	public static boolean saveFile(byte[] str,String filepath)
	{
		if(filepath==null || filepath.trim().length()==0) return false;
		byte[] strTmp=str;
		if(strTmp==null) strTmp=new byte[0];
		try
		{
			File file=new File(filepath);
			RandomAccessFile rdfile=new RandomAccessFile(file, "rw"); 
	
			rdfile.write(strTmp);
			rdfile.close();
		}
		catch(Exception e)
		{
			System.out.println("save File error:"+e.toString());
			return false;
		}
		return true;
	}

	public static boolean copyFile(String spath,String tpath)
	{
		if(spath==null || spath.trim().length()==0 ||tpath==null || tpath.trim().length()==0)
			return false;
		try
		{	
			File sfile=new File(spath);
			String filename=sfile.getName();
			
			File tfile=new File(tpath,filename);
			
			RandomAccessFile rdtfile=new RandomAccessFile(tfile, "rw"); 
	
			RandomAccessFile rdsfile=new RandomAccessFile(sfile,"r");
			byte[] contents=new byte[(int)rdsfile.length()];
			rdsfile.read(contents);
			rdsfile.close();
			
			rdtfile.write(contents);
			rdtfile.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Copy file error:" + e.toString());
			return false;
		}
	}

	public static boolean moveFile(String spath,String tpath)
	{
		if(spath==null || spath.trim().length()==0 ||tpath==null || tpath.trim().length()==0)
			return false;
		if(copyFile(spath,tpath))
			return delete(spath);
		else
			return false;
	}

} 

