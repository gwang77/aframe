package sz.internal.common.util;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

public class ZipUtil {

    /**
     * zip folder
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
        if (file == null) {
            return;
        }

        //if file
        if (file.isFile()) {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1) {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        } else {
            File[] childFileList = file.listFiles();
            for (File aChildFileList : childFileList) {
                zip(srcRootDir, aChildFileList, zos);
            }
        }
    }

    /**
     * zip file or folder
     */
    public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName)) {
            throw new Exception("Param is null");
        }
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);

            if (srcFile.isDirectory() && zipPath.contains(srcPath)) {
                throw new Exception("zipPath must not be the child directory of srcPath.");
            }

            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }

            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(zipFilePath);
                zipFile.delete();
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);

            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * unzip file
     */
    @SuppressWarnings("unchecked")
    public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception {
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath)) {
            throw new Exception("Param is null");
        }
        
        //below to clear last separator if exists
        File unzipFilePathFile = new File(unzipFilePath);
        unzipFilePath = unzipFilePathFile.getAbsolutePath();
        
        File zipFile = new File(zipFilePath);
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (StringUtils.isNotEmpty(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }

        //start unzip
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            String entryName = entry.getName();
            if (entryName.endsWith("/") || entryName.endsWith("\\")) {
                continue;
            }
            entryName = replaceFilePath(entryName);
            entryFilePath = unzipFilePath + File.separator + entryName;
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1) {
                entryDirPath = entryFilePath.substring(0, index);
            } else {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            if (!entryDir.exists() || !entryDir.isDirectory()) {
                entryDir.mkdirs();
            }

            entryFile = new File(entryFilePath);
            if (entryFile.exists()) {
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(entryFilePath);
                entryFile.delete();
            }

            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        }
        zip.close();
    }

    private static String replaceFilePath(String entryName) {
        String fileSeparatorReplace = File.separator;
        if (fileSeparatorReplace.equals("\\")) {
            fileSeparatorReplace = "\\\\";
        }
        entryName = entryName.replaceAll("/", fileSeparatorReplace);
        entryName = entryName.replaceAll("\\\\", fileSeparatorReplace);
        return entryName;
    }

    public static void main(String[] args) {
        String zipPath = "D:\\ftp_client_folder";
        String dir = "D:\\ftp_client_folder\\20160809";
        String zipFileName = "20160809.zip";
        try {
            zip(dir, zipPath, zipFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String zipFilePath = "D:\\ftp_client_folder\\20160809.zip";
        String zipFilePath2 = "D:\\ftp_client_folder\\abc.zip";
        String unzipFilePath = "D:\\ftp_client_folder\\test";
        try {
            unzip(zipFilePath, unzipFilePath, true);
            unzip(zipFilePath2, unzipFilePath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}