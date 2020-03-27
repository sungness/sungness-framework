package com.sungness.core.fastdfs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * FastDFS文件服务器引擎
 * 读取 fdfs_client.conf 配置文件，连接文件服务器集群，
 * 提供文件上传与获取功能。
 * Created by wanghongwei on 07/02/2017.
 */
public class FastDFSEngine {
    private static final Logger log = LoggerFactory.getLogger(FastDFSEngine.class);
    /** 客户端文件名，配置时需带classpath前缀 */
    private String clientConfigFileName;

    /** 文件服务器追踪器 */
    private TrackerClient tracker;

    public FastDFSEngine(String clientConfigFileName)
            throws Exception {
        this.clientConfigFileName = clientConfigFileName;
        ClientGlobal.init(getConfigFile());
        tracker = new TrackerClient();
    }

    private String getConfigFile() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String fileName = resourceLoader.getResource(
                "classpath:" + this.clientConfigFileName).getFile().getAbsolutePath();
        log.info(fileName);
        return fileName;
    }

    /**
     * 上传文件
     * @param fileBuff byte[] 文件字节数组
     * @param fileName String 文件名
     * @return String 文件id（文件路径，group+file）
     */
    public String upload(byte[] fileBuff, String fileName)
            throws IOException, MyException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", fileName);
            return client.upload_file1(fileBuff,  FilenameUtils.getExtension(fileName), metaList);
        } finally {
            if (trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    /**
     * 下载文件
     * @param fileId String 文件id
     * @return byte[] 文件二进制数组
     */
    public byte[] download(String fileId) throws IOException, MyException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);
            return client.download_file1(fileId);
        } finally {
            if (trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    /**
     * 更新文件
     * @param fileId String 文件id
     * @param fileBuff byte[] 文件二进制数组
     * @param fileName String 新文件名
     * @return 更新成功返回新文件id
     */
    public String update(String fileId, byte[] fileBuff, String fileName)
            throws IOException, MyException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);
            int res = client.delete_file1(fileId);
            if (res != 0) {
                throw new MyException("Delete old file failed, error code:" + res);
            }
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", fileName);
            return client.upload_file1(fileBuff, FilenameUtils.getExtension(fileName), metaList);
        } finally {
            if (trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    /**
     * 删除文件
     * @param fileId String 文件id
     * @return 删除成功返回true
     */
    public boolean delete(String fileId) throws IOException, MyException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);
            int res = client.delete_file1(fileId);
            return res == 0;
        } finally {
            if (trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String fileName = "/path/to/IMG_2760.PNG";
            FastDFSEngine engine = new FastDFSEngine("fdfs_client.conf");
//            String fileId = engine.upload(FileUtils.readFileToByteArray(new File(fileName)), fileName);
            String fileId = "group1/M00/00/00/wKgBI1iaDGKAeFHIAAEvRwCpM6o4635375.PNG";
            log.warn("fileId=" + fileId);
            fileId = engine.update(fileId, FileUtils.readFileToByteArray(new File(fileName)), fileName);
            log.debug("update res new fileId=" + fileId);
            byte[] fileBytes = engine.download(fileId);
            FileUtils.writeByteArrayToFile(new File("test4.JPG"), fileBytes);

            log.debug(FilenameUtils.getBaseName(fileId));
            log.debug(FilenameUtils.getExtension(fileId));
            log.debug(FilenameUtils.getFullPath(fileId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
