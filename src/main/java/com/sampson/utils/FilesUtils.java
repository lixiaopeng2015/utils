package com.sampson.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/17 11:43
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class FilesUtils {
    private static Logger logger = LoggerFactory.getLogger(FilesUtils.class);

    /**
     * 检查文件或文件夹是否存在
     * @param folderPath  文件或文件夹路径
     * @return true为存在，false不存在
     */
    public static boolean checkFolder(String folderPath) {
        boolean result = false;
        if (StringUtils.isEmpty(folderPath)) {
            File newFilePath = new File(folderPath);
            result = newFilePath.exists();
        }
        return result;
    }

    /**
     * 新建目录操作
     *
     * @param folderPath 文件夹路径
     */
    public static boolean mkdirs(String folderPath) {
        try {
            if (StringUtils.isNotEmpty(folderPath)) {
                File newFilePath = new File(folderPath);
                if (!newFilePath.exists()) {
                    return newFilePath.mkdirs();
                }
            }
        } catch (Exception e) {
            logger.error("create dictionary exception ERROR KEY:" + e.getMessage());

        }
        return false;
    }

    /**
     * 创建新的文件
     *
     * @param pathAndName 文件名称
     * @param fileContent 文件内容
     */
    public static boolean createFileByPath(String pathAndName, String fileContent) {
        boolean result = false;
        try {
            if (StringUtils.isEmpty(pathAndName)) {
                File newFilePath = new File(pathAndName);
                if (!newFilePath.exists()) {
                    newFilePath.createNewFile();
                }
                FileUtils.writeStringToFile(newFilePath, fileContent);
                result = true;
            }
        } catch (Exception e) {
            logger.error("新建文件操作出错ERROR KEY:" + e.getMessage());
        }
        return result;
    }

    /**
     * 创建新的文件
     *
     * @param filePathAndName 文件名称
     * @param fileContent     文件内容
     */
    public static boolean createFile(String filePathAndName, String fileContent, String encoding) {
        try {
            if (StringUtils.isNotEmpty(filePathAndName)) {
                File path = new File(filePathAndName);
                if (!path.exists()) {
                    path.createNewFile();
                }
                FileUtils.writeStringToFile(path, fileContent, encoding);
                return true;
            }
        } catch (Exception e) {
            logger.error("create file exception ERROR KEY:" + e.getMessage());
        }
        return false;
    }

    /**
     * 创建新的文件
     *
     * @param inputStream 文件内容流
     * @param filePath    文件路径
     * @return  返回true为创建成功，false为创建失败
     */
    public static boolean createFileByInputStream(InputStream inputStream, String filePath) {
        boolean result = false;
        if (inputStream == null)
            return false;
        FileOutputStream output = null;
        try {
            if (checkFolder(filePath))
                deleteFile(filePath);
            output = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("新建文件操作出错ERROR KEY:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("新建文件操作出错ERROR KEY:" + e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
                inputStream.close();
            } catch (Exception e) {
                logger.error("关闭文件流出错ERROR KEY:" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件路径名称
     * @return true删除成功，false删除失败
     */
    public static boolean deleteFile(String fileName) {
        boolean result = false;
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                if (checkFolder(fileName)) {
                    File file= new File(fileName);
                    result = file.delete();
                }
                result = true;
            }
        } catch (Exception e) {
            logger.error("删除文件操作出错ERROR KEY:" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath      文件夹路径
     * @param isDelFolderPath 是否删除文件夹目录。
     * @return  true删除，false不删除。
     */
    public static void delFolder(String folderPath, boolean isDelFolderPath) {
        try {
            if (StringUtils.isNotEmpty(folderPath)) {
                batchDeleteFiles(folderPath);//删除完里面所有内容
                if (isDelFolderPath) {
                    File delFilePath = new File(folderPath);
                    delFilePath.delete(); //删除空文件夹
                }
            }
        } catch (Exception e) {
            logger.error("删除文件夹操作出错ERROR KEY:" + e.getMessage());
        }
    }

    /**
     * 批量删除文件
     *
     * @param path 文件所在目录
     * @return     true删除成功，false删除失败
     */
    public static boolean batchDeleteFiles(String path) {
        if (StringUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                //删除文件夹里面的文件
                batchDeleteFiles(path + Constants.FILE_SEPARATOR + tempList[i]);
                delFolder(path + Constants.FILE_SEPARATOR + tempList[i], true);//删除空文件夹
            }
        }
        return true;
    }
    /**
     * 获取删除文件的数量
     *
     * @param temp 文件对象
     * @param map  存储集合
     * @return     返回删除文件的数量集合
     */
    private static Map<String, String> getDeleteFileMap(File temp, Map<String, String> map) {
        int susNum = 0, errorNum = 0;
        if (temp.isFile()) {
            boolean falg = temp.delete();
            if (falg) {
                if (StringUtils.isNotEmpty(map.get("susm")))
                    susNum = Integer.parseInt(map.get("susm"));
                if (temp.getName().indexOf(".html") != -1 || temp.getName().indexOf(".shtml") != -1)
                    susNum++;
                map.put("susm", String.valueOf(susNum));
            } else {
                errorNum++;
                if (StringUtils.isNotEmpty(map.get("error")))
                    errorNum = errorNum + Integer.parseInt(map.get("error"));
                map.put("error", String.valueOf(errorNum));
            }
        }
        return map;
    }



}
