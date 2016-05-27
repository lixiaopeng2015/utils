package main.java.com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/20 10:55
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class ShellUtils {

    private Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    public String executeShell(List<String> params,String shellFilePath,String shellFile){
        Process process = null;
        String paramArray = "";
        List<String> processList = new ArrayList<String>();
        String fileUrl = shellFilePath + shellFile;
        logger.info("sh file path is +++++++++++++++++++++++++++++++" + fileUrl);
        for (String s : params) {
            paramArray += s + " ";
        }

        paramArray = paramArray.substring(0,paramArray.length()-1);

        String command = "/bin/sh" + fileUrl + " " +paramArray;
        File shFile = new File(fileUrl);
        if (!shFile.exists()) {
            logger.info(fileUrl +" not exists !");
            return fileUrl + " not exists !";
        }
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while((line = bufferedReader.readLine())!=null) {
                processList.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            logger.error("execute " + shellFile + "encountered an exception \t" + e.getMessage());
            return "Execute " + shellFile +" error!" + "\t"+e.getMessage();
        }
        String result ="";
        if (null!= processList && processList.size()>0) {
            result = processList.get(0);
        }
        logger.info(shellFile + " execute successful !" + result);
        return shellFile + "execute successful !" + result;
    }

}
