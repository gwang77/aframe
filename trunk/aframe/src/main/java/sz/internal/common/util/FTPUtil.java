package sz.internal.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.*;

public class FTPUtil {
    private static String SLASH = "/";
    private static String LOCAL_FILE_SEPARATOR = File.separator;

    private static Logger logger = Logger.getLogger(FTPUtil.class);
    private FTPClient ftp;

    public FTPClient getFtp() {
        return ftp;
    }

    public void setFtp(FTPClient ftp) {
        this.ftp = ftp;
    }

    private static FTPUtil connectFtp(String server_name, String username, String password, String workingFolder) throws Exception {
        return connectFtp(server_name, 0, username, password, workingFolder);
    }

    private static FTPUtil connectFtp(String server_name, int port, String username, String password, String workingFolder) throws Exception {
        FTPClient ftpClient = new FTPClient();
        int reply;
        if (port == 0) {
            ftpClient.connect(server_name, 21);
        } else {
            ftpClient.connect(server_name, port);
        }
        ftpClient.login(username, password);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return null;
        }
        if (StringUtils.isEmpty(workingFolder)) {
            workingFolder = SLASH;
        }
        ftpClient.changeWorkingDirectory(workingFolder);
        //This will cause the file upload/download methods to send a NOOP approximately every 5 minutes.
        ftpClient.setControlKeepAliveTimeout(300);

        FTPUtil ftpUtil = new FTPUtil();
        ftpUtil.setFtp(ftpClient);
        return ftpUtil;
    }

    public void closeFtp() {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private boolean isConnect() {
        return ftp != null && ftp.isConnected();
    }

    /**
     * Upload file
     */
    public void upload(File f, String remoteFolder) throws Exception {
        if (!isConnect()) {
            return;
        }
        logger.info("start upload " + f.getPath() + ", " + remoteFolder);
        if (f.isDirectory()) {
            ftp.changeWorkingDirectory(remoteFolder);
            ftp.makeDirectory(f.getName());
            String workingFolder = remoteFolder + SLASH + f.getName();
            ftp.changeWorkingDirectory(workingFolder);
            File[] files = f.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    upload(file1, workingFolder);
                    ftp.changeToParentDirectory();
                } else {
                    storeFTPFile(file1.getPath());
                }
            }
        } else {
            storeFTPFile(f.getPath());
        }
        logger.info("end upload " + f.getPath());
    }

    private void storeFTPFile(String filePath) {
        File file2 = new File(filePath);
        FileInputStream input = null;
        try {
            input = new FileInputStream(file2);
            ftp.storeFile(file2.getName(), input);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public boolean deleteFTPFile(String filePath) throws Exception {
        if (!isConnect()) {
            return false;
        }
        try {
            return ftp.deleteFile(filePath);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Download file
     */
    public int download(String localDir, String remoteFilePath, String archiveDir) throws Exception {
        if (!isConnect()) {
            return 0;
        }
        int fileCount = 0;
        logger.info("start download " + remoteFilePath);
        int idx = remoteFilePath.lastIndexOf(SLASH);
        String remoteParentPath = remoteFilePath.substring(0, idx + 1);
        String remoteFileFolderName = remoteFilePath.substring(idx + 1);

        boolean changeDir = ftp.changeWorkingDirectory(remoteFilePath);
        if (changeDir) {
            String localFolderStr = localDir + remoteFileFolderName + LOCAL_FILE_SEPARATOR;
            File localFolder = new File(localFolderStr);
            if (!localFolder.exists()) {
                localFolder.mkdirs();
            }

            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                fileCount = fileCount + doDownloadFile(file, localFolderStr, remoteFilePath, archiveDir);
            }
        } else {
            ftp.changeWorkingDirectory(remoteParentPath);
            fileCount = downloadFile(remoteParentPath, remoteFileFolderName, localDir, archiveDir);
        }
        logger.info("end download " + remoteFilePath);

        return fileCount;
    }

    //download files and folder.
    private int doDownloadFile(FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath, String archiveDir) throws Exception {
        if (ftpFile.isFile()) {
            return downloadFile(relativeRemotePath, ftpFile.getName(), relativeLocalPath, archiveDir);
        } else if (".".equals(ftpFile.getName()) || "..".equals(ftpFile.getName())) {
            return 0;
        } else {
            int downloadCount = 0;
            String newLocalRelatePath = relativeLocalPath + ftpFile.getName();
            String newRemote = relativeRemotePath + ftpFile.getName();
            File fl = new File(newLocalRelatePath);
            if (!fl.exists()) {
                fl.mkdirs();
            }

            newLocalRelatePath = newLocalRelatePath + LOCAL_FILE_SEPARATOR;
            newRemote = newRemote + SLASH;
            String currentWorkDir = ftpFile.getName();
            boolean changedir = ftp.changeWorkingDirectory(currentWorkDir);
            if (changedir) {
                FTPFile[] files = null;
                files = ftp.listFiles();
                for (FTPFile file : files) {
                    downloadCount = downloadCount + doDownloadFile(file, newLocalRelatePath, newRemote, archiveDir);
                }
            }
            if (changedir) {
                ftp.changeToParentDirectory();
            }

            return downloadCount;
        }
    }

    //download file, not include folder.
    private int downloadFile(String remoteFolder, String remoteFileName, String relativeLocalPath, String archiveDir) throws Exception {
        if (!remoteFileName.contains("?")) {
            OutputStream outputStream = null;
            try {
                File locaFile = new File(relativeLocalPath + remoteFileName);
                if (locaFile.exists()) {
                    logger.info("File already existed:" + locaFile.getPath());
                    return 0;
                }
                outputStream = new FileOutputStream(relativeLocalPath + remoteFileName);
                ftp.changeWorkingDirectory(remoteFolder);
                ftp.retrieveFile(remoteFileName, outputStream);
                outputStream.flush();
                outputStream.close();

                if (!StringUtils.isEmpty(archiveDir)) {
                    if (!ftp.changeWorkingDirectory(archiveDir)) {
                        ftp.makeDirectory(archiveDir);
                    }
                    ftp.rename(remoteFolder + remoteFileName, archiveDir + remoteFileName);
                }

                return 1;
            } catch (Exception e) {
                logger.error(e);
                throw e;
            } finally {
                IOUtils.closeQuietly(outputStream);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            FTPUtil ftpUtil = FTPUtil.connectFtp("localhost", "", "", "");
            ftpUtil.upload(new File("d:\\ftp_client_folder\\images"), "/");

            ftpUtil.download("d:\\ftp_client_folder\\", "/test.txt", null);
            ftpUtil.download("d:\\ftp_client_folder\\", "/images", null);
            ftpUtil.closeFtp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
