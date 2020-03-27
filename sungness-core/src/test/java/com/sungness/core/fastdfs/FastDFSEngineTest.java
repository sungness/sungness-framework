package com.sungness.core.fastdfs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * fastDFS 上传、更新、删除、下载 测试
 * Created by Chwing on 2019/1/14.
 */
public class FastDFSEngineTest {

    private static final Logger log = LoggerFactory.getLogger(FastDFSEngineTest.class);

    private FastDFSEngine engine;

    @Before
    public void before () throws Exception {
        engine = new FastDFSEngine("fdfs_client.conf");
    }

    /**
     * 上传、更新、删除、下载
     * @throws Exception e
     */
    @Test
    public void testUUDD() throws Exception {
        // String fileName = "D:\\Chwing\\Pictures\\temp\\jx_512.png";

        // String fileId = engine.upload(FileUtils.readFileToByteArray(new File(fileName)), fileName);
        // log.debug("upload fileId: {}", fileId);

        // byte[] byteDown = engine.download(fileId);
        // if (byteDown != null) {
        //     log.debug("download bytes length: {}", byteDown.length);
        // } else {
        //     log.error("download fail");
        // }

        // String fileIdUpdate = engine.update(fileId, byteDown, "jx_512.png");
        // log.debug("update fileId: {}", fileIdUpdate);

        // boolean delete = engine.delete(fileIdUpdate);
        // log.debug("delete {}", delete ? "success" : "fail");

    }


    /**
     * 上传
     * @throws Exception
     */
    @Test
    public void testUpload() throws Exception {
        // String fileName = "D:\\Chwing\\Pictures\\temp\\jx_512.png";

        // String fileId = engine.upload(FileUtils.readFileToByteArray(new File(fileName)), fileName);

        // log.debug(fileId);
        // log.debug(FilenameUtils.getBaseName(fileId));
        // log.debug(FilenameUtils.getExtension(fileId));
        // log.debug(FilenameUtils.getFullPath(fileId));
    }

    /**
     * 删除
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        // String fileId = "group1/M00/00/01/wKgKmlw751eAZvfFAACGTjG7j2U658.png";
        // byte[] bytesB = engine.download(fileId);
        // log.debug("delete before length: " + bytesB.length);
        // boolean delete = engine.delete(fileId);
        // log.debug(delete ? "delete success" : "delete fail");
        // byte[] bytesA = engine.download(fileId);
        // log.debug(bytesA == null ? "delete success" : "delete after length: " + bytesA.length);
    }

    /**
     * 更新文件
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        // String fileId = "group1/M00/00/01/wKgKmlw76G6AA91RAACGTjG7j2U752.png";
        // byte[] bytesB = engine.download(fileId);
        // log.debug("delete before length: " + bytesB.length);
        // String fileId1 = engine.update(fileId, bytesB, "jx_512.png");
        // log.debug(fileId1);
        // byte[] bytesA = engine.download(fileId);
        // log.debug(bytesA == null ? "update success" : "update after length: " + bytesA.length);
    }
}
