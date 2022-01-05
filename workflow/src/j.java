package com.zl.service.impl;

import com.zl.framework.dao.CommonDalClient;
import com.zl.framework.util.DateUtils;
import com.zl.framework.util.OssUtil;
import com.zl.service.RegService;
import com.zl.service.StableWindowsService;
import com.zl.service.WindowsRecordService;
import com.zl.service.impl.log.LoggerBuilder;
import com.zl.service.impl.svn.SVNKit;
import com.zl.service.impl.util.*;
import com.zl.service.impl.util.FileUtil;
import com.zl.service.impl.util.InputStream2File;
import com.zl.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2019/11/30.
 */
@Service
public class StableWindowsServiceImpl implements StableWindowsService {

    private static final String OSS_BASE_PATH = "http://041001.zhonglunnet.com/";

    @Autowired
    protected CommonDalClient dal;

    @Autowired
    private WindowsRecordService windowsRecordService;

    @Autowired
    private RegService regService;

    private static final String NAMESPACE = "shardName.com.zl.service.impl.StableWindowsServiceImpl.";

    @Override
    public List<Map<String, Object>> getObjectList(Map<String, Object> params) {
        return dal.getDalClient().queryForList(NAMESPACE + "getObjectList", params);
    }

    @Override
    public List<Map<String, Object>> getDirectList(Map<String, Object> params) {

        Map<String, Object> objectMap = getObject(params);
        if (MapUtils.isEmpty(objectMap)) {
            throw new RuntimeException("操作异常！");
        }

        String env = MapUtils.getString(objectMap, "env", "");
        String appCode = MapUtils.getString(objectMap, "appcode", "");
        String appCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/";

        String filePath = appCodePath + "mapconfig.json";

        File file = new File(filePath);
        List<Map<String, Object>> resultList = new ArrayList<>();

        if (file.exists()) {

            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {

                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();

                String jsonString = sb.toString();

                if (StringUtils.isNotEmpty(jsonString)) {
                    Map<String, Object> jsonMap = JSONObject.fromObject(jsonString);
                    if (MapUtils.isNotEmpty(jsonMap)) {
                        for (String key : jsonMap.keySet()) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("code", key);
                            item.put("url", jsonMap.get(key));
                            resultList.add(item);
                        }
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("error");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getH5DirectList(Map<String, Object> params) {
        Map<String, Object> objectMap = getObject(params);
        if (MapUtils.isEmpty(objectMap)) {
            throw new RuntimeException("操作异常！");
        }

        String env = MapUtils.getString(objectMap, "env", "");
        String appCode = MapUtils.getString(objectMap, "appcode", "");
        String appCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/";

        String filePath = appCodePath + "h5mapconfig.json";

        File file = new File(filePath);
        List<Map<String, Object>> resultList = new ArrayList<>();

        if (file.exists()) {

            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {

                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();

                String jsonString = sb.toString();

                if (StringUtils.isNotEmpty(jsonString)) {
                    Map<String, Object> jsonMap = JSONObject.fromObject(jsonString);
                    if (MapUtils.isNotEmpty(jsonMap)) {
                        for (String key : jsonMap.keySet()) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("code", key);
                            item.put("url", jsonMap.get(key));
                            resultList.add(item);
                        }
                    }
                }

            } catch (Exception ex) {
                throw new RuntimeException("error");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public void addDirect(Map<String, Object> params) {
        String configs = MapUtils.getString(params, "configs");
        Map<String, Object> objectMap = getObject(params);
        if (MapUtils.isEmpty(objectMap)) {
            throw new RuntimeException("操作异常！");
        }

        String env = MapUtils.getString(objectMap, "env", "");
        String appCode = MapUtils.getString(objectMap, "appcode", "");
        String appCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/";
        String OSS_OEM_APP_ENV_PATH = "OEM/windows/" + env + "/" + appCode + "/";

        String filePath = appCodePath + "mapconfig.json";

        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                throw new RuntimeException("保存失败，删除文件异常！");
            }
        }

        Map<String, Object> jsonMap = new HashMap<>();
        if (StringUtils.isNotEmpty(configs)) {
            List<Map<String, Object>> configList = JSONArray.fromObject(configs);
            if (CollectionUtils.isNotEmpty(configList)) {
                for (Map<String, Object> configItem : configList) {
                    jsonMap.put(MapUtils.getString(configItem, "code", ""), MapUtils.getString(configItem, "url", ""));
                }
            }
        }

        String newJsonString = JSONObject.fromObject(jsonMap).toString();
        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write(newJsonString);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        uploadOss(newFile.getAbsolutePath(), OSS_OEM_APP_ENV_PATH + env + "/mapconfig.json");
    }

    @Override
    public void addDirectH5(Map<String, Object> params) {
        String configs = MapUtils.getString(params, "configs");
        Map<String, Object> objectMap = getObject(params);
        if (MapUtils.isEmpty(objectMap)) {
            throw new RuntimeException("操作异常！");
        }

        String env = MapUtils.getString(objectMap, "env", "");
        String appCode = MapUtils.getString(objectMap, "appcode", "");
        String appCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/";
        String OSS_OEM_APP_ENV_PATH = "OEM/windows/" + env + "/" + appCode + "/";

        String filePath = appCodePath + "h5mapconfig.json";

        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                throw new RuntimeException("保存失败，删除文件异常！");
            }
        }

        Map<String, Object> jsonMap = new HashMap<>();
        if (StringUtils.isNotEmpty(configs)) {
            List<Map<String, Object>> configList = JSONArray.fromObject(configs);
            if (CollectionUtils.isNotEmpty(configList)) {
                for (Map<String, Object> configItem : configList) {
                    jsonMap.put(MapUtils.getString(configItem, "code", ""), MapUtils.getString(configItem, "url", ""));
                }
            }
        }

        String newJsonString = JSONObject.fromObject(jsonMap).toString();
        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write(newJsonString);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        uploadOss(newFile.getAbsolutePath(), OSS_OEM_APP_ENV_PATH + env + "/h5mapconfig.json");
    }

    @Override
    public List<Map<String, Object>> getConfigUrlList(Map<String, Object> params) {
        //获取发布参数数据
        Map<String, Object> objectMap = getObject(params);
        Map<String, Object> p1 = new HashMap<>();
        p1.put("appid", MapUtils.getString(objectMap, "id"));
        return windowsRecordService.getConfigUrlList(p1);
    }

    @Override
    public List<Map<String, Object>> getH5ConfigUrlList(Map<String, Object> params) {
        //获取发布参数数据
        Map<String, Object> objectMap = getObject(params);
        Map<String, Object> p1 = new HashMap<>();
        p1.put("appid", MapUtils.getString(objectMap, "id"));
        return windowsRecordService.getH5ConfigUrlList(p1);
    }

    @Override
    public Map<String, Object> getObject(Map<String, Object> params) {
        return dal.getDalClient().queryForMap(NAMESPACE + "getObject", params);
    }

    @Override
    public void publish(Map<String, Object> params) {
        System.out.println("开始打包params" + params);
        String id = MapUtils.getString(params, "id");
        Map<String, Object> p1 = new HashMap<>();
        p1.put("id", id);
        //获取发布参数数据
        Map<String, Object> objectMap = getObject(p1);
        objectMap.put("registryip", MapUtils.getString(params, "registryip"));
        String recordId = String.valueOf(windowsRecordService.addObject(objectMap));
        System.out.println("开始打包recordId" + recordId);
        params.put("recordid", recordId);
        String logId = String.valueOf(recordId);
        Logger logger = LoggerBuilder.getLogger("windows", logId);
        try {
            String updateUrl = publishItem(objectMap, logger);
            updatePublishResult(recordId, updateUrl, "1");
        } catch (RuntimeException ex) {
            logger.info(ex.getMessage());
            logger.info("##########failed##########");
            params.put("success", "0");
            windowsRecordService.updateWindowsRecordSuccess(params);
            params.put("registrystatus", "04");
            regService.updateExecutEndObject(params);
            params.put("status", "04");
            updateStableWindowsApp(params);
            return;
        }
        params.put("success", "2");
        params.put("registrystatus", "02");
        params.put("status", "05");
        updateStableWindowsApp(params);
        regService.updateExecutEndObject(params);
        windowsRecordService.updateWindowsRecordSuccess(params);


    }

    private void updateStableWindowsApp(Map<String, Object> params) {
        dal.getDalClient().execute(NAMESPACE + "updateStableWindowsApp", params);
    }

    /**
     * @param id
     * @param updateUrl
     * @param success
     */
    private void updatePublishResult(String id, String updateUrl, String success) {
        Map<String, Object> p1 = new HashMap<>();
        p1.put("id", id);
        p1.put("updateurl", updateUrl);
        p1.put("success", success);
        windowsRecordService.updateRecordResult(p1);
    }

    @Override
    public int addObject(Map<String, Object> params) {
        return dal.getDalClient().execute(NAMESPACE + "addObject", params);
    }

    @Override
    public void updateObject(Map<String, Object> params) {
        dal.getDalClient().execute(NAMESPACE + "updateObject", params);
    }

    @Override
    public void updateLastBase(String id, String recordNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("lastbase", recordNo);
        dal.getDalClient().execute(NAMESPACE + "updateLastBase", params);
    }

    @Override
    public void updateLastSuccess(String id, String recordNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("lastsuccess", recordNo);
        dal.getDalClient().execute(NAMESPACE + "updateLastSuccess", params);
    }

    @Override
    public void deleteObject(Map<String, Object> params) {
        dal.getDalClient().execute(NAMESPACE + "deleteObject", params);
    }


    public String publishItem(Map<String, Object> objectMap, Logger logger) {
        String isIncrement = MapUtils.getString(objectMap, "isincrement", "");
        String lastBase = MapUtils.getString(objectMap, "lastbase", "");
        if (StringUtils.isEmpty(isIncrement)) {
            throw new RuntimeException("请选择发版环境！");
        }
        if (isIncrement.equals("1") && StringUtils.isEmpty(lastBase)) {
            throw new RuntimeException("增量发布必须完成一次完整发布！");
        }

        if (isIncrement.equals("2") && StringUtils.isEmpty(lastBase)) {
            throw new RuntimeException("H5热发布，必须完成一次完整发布！");
        }

        String isIncrementText = "";
        if (isIncrement.equals("0")) {
            isIncrementText = "全量发布";
        } else if (isIncrement.equals("1")) {
            isIncrementText = "增量发布,上次完整版本:" + lastBase;
        } else if (isIncrement.equals("2")) {
            isIncrementText = "H5热发布，上次完整版本:" + lastBase;
        }

        logger.info("##########发布开始，正在执行" + isIncrementText + "##########");

        String deviceCodes = MapUtils.getString(objectMap, "devicecodes");
        String svnPath = MapUtils.getString(objectMap, "svnpath").trim();
        String h5homesvnpath = MapUtils.getString(objectMap, "h5homesvnpath").trim();
        String h5seconsvnpath = MapUtils.getString(objectMap, "h5seconsvnpath").trim();
        String env = MapUtils.getString(objectMap, "env");
        String company = MapUtils.getString(objectMap, "company");
        String productcode = MapUtils.getString(objectMap, "industry");
        String publisher = MapUtils.getString(objectMap, "publisher");
        String website = MapUtils.getString(objectMap, "website");
        String isforce = MapUtils.getString(objectMap, "isforce", "1");
        String isIgnorVersion = MapUtils.getString(objectMap, "isignorversion", "0");
        String lbImageCount = MapUtils.getString(objectMap, "lbimagecount", "3");
        String upgradeLog = MapUtils.getString(objectMap, "upgradelog");
        String appCode = MapUtils.getString(objectMap, "appcode");
        String ossCode = MapUtils.getString(objectMap, "osscode", "");
        if (StringUtils.isEmpty(ossCode)) {
            ossCode = appCode;
        }
        String serverCode = MapUtils.getString(objectMap, "servercode");
        String servertel = MapUtils.getString(objectMap, "servertel");
        String servername = MapUtils.getString(objectMap, "servername");
        String appName = MapUtils.getString(objectMap, "appname");
        String industry = MapUtils.getString(objectMap, "industry", "");
        String createtime = MapUtils.getString(objectMap, "createtimestr", "");
        String registryip = MapUtils.getString(objectMap, "registryip");
        String appVersion = MapUtils.getString(objectMap, "appversion");
        String appVersionCode = MapUtils.getString(objectMap, "appversioncode");
        String appDesc = MapUtils.getString(objectMap, "appdesc");
        String recordNo = DateUtils.formatDate2Str(new Date(), DateUtils.C_TIME_PATTON_DEFAULT1);
        String appCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/";
        String recordPath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/";
        String codePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/zlpos/";
        String nsisCodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/NSIS/";
        String lastRecordPath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + lastBase + "/";
        String h5CodePath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/h5/";
        String h5maintargetPath = h5CodePath + "main/";
        String h5maintargetPathZip = h5CodePath + "main/dist.zip";
        String h5screensvnpath = h5CodePath + "screen/";
        String h5screensvnpathZip = h5CodePath + "screen/dist.zip";
        String codeBinPath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/zlpos/ZlPos/bin/";
        String zipCodeBinPath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/zlpos/ZlPos/";
        String lastRecordCodeBinPath = SystemConfig.OEM_WINDOWS_PATH + "buildcode/" + env + "/" + appCode + "/" + lastBase + "/zlpos/ZlPos/bin/";
        String h5BinPath = SystemConfig.OEM_WINDOWS_PATH + env + "/" + appCode + "/" + recordNo + "/zlpos/ZlPos/bin/Html/";
        String lastRecordH5BinPath = SystemConfig.OEM_WINDOWS_PATH + "buildcode/" + env + "/" + appCode + "/" + lastBase + "/zlpos/ZlPos/bin/Html/";
        String OSS_OEM_APP_ENV_PATH = "OEM/windows/" + env + "/" + appCode + "/";
        String OSS_OEM_BUILD_CODE = "OEM-buildcode/windows/" + appCode + "/" + env + "/";
        logger.info("发布参数" + objectMap);
        String ossH5DistPath = "OEM/windows/" + env + "/" + appCode + "/" + "web_file_config.xml";
        String ossScreenDistPath = "OEM/windows/" + env + "/" + appCode + "/" + "screen_file_config.xml";
        String ossH5DistZipPath = "OEM/windows/" + env + "/" + appCode + "/" + "stablepos_h5.zip";
        String ossScreenDistZipPath = "OEM/windows/" + env + "/" + appCode + "/" + "stablepos_subscreen.zip";
        String h5DistPath = h5maintargetPath + "web_file_config.xml";
        String screenDistPath = h5screensvnpath + "web_file_config.xml";
        String ossurl = "https://zhonglunnet041001.oss-cn-shanghai.aliyuncs.com/";
        if ("2".equals(isIncrement)) {
            downH5Svn(h5homesvnpath, h5maintargetPath, h5maintargetPathZip, h5seconsvnpath, h5screensvnpath, h5screensvnpathZip, logger);

            ReplaceFile.replaceFileStr2(h5DistPath, "zip_url", "<zip_url>" + ossurl + ossH5DistZipPath + "</zip_url>");
            ReplaceFile.replaceFileStr2(h5DistPath, "congfig_url", "<congfig_url>" + ossurl + ossH5DistPath + "</congfig_url>");
            ReplaceFile.replaceFileStr2(h5DistPath, "desc", "<desc>" + upgradeLog + "</desc>");
            ReplaceFile.replaceFileStr2(screenDistPath, "zip_url", "<zip_url>" + ossurl + ossScreenDistZipPath + "</zip_url>");
            ReplaceFile.replaceFileStr2(screenDistPath, "congfig_url", "<congfig_url>" + ossurl + ossScreenDistPath + "</congfig_url>");
            ReplaceFile.replaceFileStr2(screenDistPath, "desc", "<desc>" + upgradeLog + "</desc>");
            //上传h5zip包做热更用
            ZipCompressor h5zc = new ZipCompressor(h5CodePath + "h5dist.zip");
            h5zc.compress(h5maintargetPath);
            //上传压缩包
            uploadOss(h5CodePath + "h5dist.zip", ossH5DistZipPath, logger);
            //上传升级文件
            uploadOss(h5DistPath, ossH5DistPath, logger);

            //上传副屏zip包做热更用
            ZipCompressor screenzc = new ZipCompressor(h5CodePath + "screendist.zip");
            screenzc.compress(h5screensvnpath);
            //上传压缩包
            uploadOss(h5CodePath + "screendist.zip", ossScreenDistZipPath, logger);
            //上传升级文件
            uploadOss(screenDistPath, ossScreenDistPath, logger);
            return "";
        }

        //下载svn代码
        checkOutSvn(logger, svnPath, getExistFolderFile(codePath).getAbsolutePath());
        //C://OEM_WORK/windows/sign/autosign.bat
        deleteDirectory(logger, SystemConfig.OEM_WINDOWS_SIGN_PATH);
        //下载签名工具
        checkOutSvn(logger, "http://svn.cnzhonglunnet.com/svn/zlnet/code/CIProject/CodeSign/", getExistFolderFile(SystemConfig.OEM_WINDOWS_SIGN_PATH).getAbsolutePath());

        //（1）替换logo文件（D: \20191130\ ZlPos\logo.ico）注：固定文件名
        replaceFileByOSSFile(logger, codePath + "ZlPos\\logo.ico", "OEM_RES/" + ossCode + "/windows/logo.ico");
        //（2）修改环境（D: \20191130\ ZlPos\appSettings.json）修改下图中pre
        replaceFileStr2(logger, codePath + "ZlPos\\appSettings.json", "Environment", "\"Environment\" : \"" + env + "\",");
        replaceFileStr2(logger, codePath + "ZlPos\\appSettings.json", "AppCode", "\"AppCode\" : \"" + appCode + "\",");
        replaceFileStr2(logger, codePath + "ZlPos\\appSettings.json", "Productcode", "\"Productcode\" : \"" + productcode + "\",");
        replaceFileStr2(logger, codePath + "ZlPos\\Resources\\R.resx", "servercode", "<value>" + serverCode + "</value>");
        replaceFileStr2(logger, codePath + "ZlPos\\Resources\\R.resx", "servertel", "<value>" + servertel + "</value>");
        replaceFileStr2(logger, codePath + "ZlPos\\Resources\\R.resx", "中仑网络", "<value>" + servername + "</value>");
        //（3）修改版本号（D: \20191130\ZlPos\Properties\AssemblyInfo.cs）
        replaceFileStr2(logger, codePath + "ZlPos\\Properties\\AssemblyInfo.cs", "[assembly: AssemblyFileVersion", "[assembly: AssemblyFileVersion(\"" + appVersion + "\")]");
        //（4）公司名称（D: \20191130\ZlPos\Properties\AssemblyInfo.cs）
        replaceFileStr2(logger, codePath + "ZlPos\\Properties\\AssemblyInfo.cs", "[assembly: AssemblyCompany", "[assembly: AssemblyCompany(\"" + company + "\")]");
        //（5）修改oem配置文件（D: \20191130\ZlPos\Config\OemConfig.json）
        //a.服务商编号 修改下图中000000
        replaceFileStr2(logger, codePath + "ZlPos\\Config\\OemConfig.json", "ServiceCode", "\"ServiceCode\":\"" + serverCode + "\",");
        //b.升级的路径配置 由下图中圈出的连个字段和环境控制，升级的文件信息暂时都存放在该目录下，文件名可以通过一个uuid来命名，一个服务商的升级文件必须固定
        String upgradeRemoteKey = OSS_OEM_APP_ENV_PATH;
        String upgradeRemoteUrl = "http://041001.zhonglunnet.com/" + upgradeRemoteKey;
        replaceFileStr2(logger, codePath + "ZlPos\\Config\\OemConfig.json", "\"UpdateUrl\"", "\"UpdateUrl\":\"" + upgradeRemoteUrl + "\",");
        String upgradeRemoteH5Key = OSS_OEM_APP_ENV_PATH;
        String upgradeRemoteH5Url = "http://041001.zhonglunnet.com/" + upgradeRemoteH5Key;
        replaceFileStr2(logger, codePath + "ZlPos\\Config\\OemConfig.json", "\"H5UpdateUrl\"", "\"H5UpdateUrl\":\"" + upgradeRemoteH5Url + "\",");
        replaceFileStr2(logger, codePath + "ZlPos\\Config\\OemConfig.json", "\"NotifyIconText\"", "\"NotifyIconText\":\"" + appName + "\",");
        replaceFileStr2(logger, codePath + "ZlPos\\Config\\OemConfig.json", "\"TipTitle\"", "\"TipTitle\":\"" + appName + "已运行\",");
        //替换  ZlPos\Resources\picture2.gif
        replaceFileByOSSFile(logger, codePath + "ZlPos\\Resources\\picture2.gif", "OEM_RES/" + ossCode + "/windows/picture2.gif");


        //4.MsBuild项目，调用生成项目 0个错误 finish 判断
        String buildingBatPath = codePath + "ProjectBuild.bat";
        logger.info("执行bat文件");
        //  execCmd(logger, buildingBatPath, codePath, "0 个错误");
        CMDUtil.processCmd(codePath, buildingBatPath, "0 个错误");
        logger.info("执行bat文件结束");

        //增加package_origin.json 文件配置
        copyFileToDirectory(logger, "C://OEM_WORK/sign/package_origin.json", codeBinPath);
        String servertype = "000000".equals(serverCode) ? "0" : "1";
        replaceFileStr2(logger, codeBinPath + "package_origin.json", "servertype", "\"servertype\": \"" + servertype + "\",");
        replaceFileStr2(logger, codeBinPath + "package_origin.json", "servercode", "\"servercode\": \"" + serverCode + "\",");
        replaceFileStr2(logger, codeBinPath + "package_origin.json", "servertel", "\"servertel\": \"" + servertel + "\",");
        replaceFileStr2(logger, codeBinPath + "package_origin.json", "servername", "\"servername\": \"" + servername + "\"");

        //svn checkout h5 和 替换
        //下载h5svn代码
        downH5Svn(h5homesvnpath, h5maintargetPath, h5maintargetPathZip, h5seconsvnpath, h5screensvnpath, h5screensvnpathZip, logger);
        ReplaceFile.replaceFileStr2(h5DistPath, "zip_url", "<zip_url>" + ossurl + ossH5DistZipPath + "</zip_url>");
        ReplaceFile.replaceFileStr2(h5DistPath, "congfig_url", "<congfig_url>" + ossurl + ossH5DistPath + "</congfig_url>");
        ReplaceFile.replaceFileStr2(screenDistPath, "zip_url", "<zip_url>" + ossurl + ossScreenDistZipPath + "</zip_url>");
        ReplaceFile.replaceFileStr2(screenDistPath, "congfig_url", "<congfig_url>" + ossurl + ossScreenDistPath + "</congfig_url>");
        copyDirectoryToDirectory(logger, h5CodePath, h5BinPath, false);

        //替换h5静态文件
        replaceFileByOSSFile(logger, h5BinPath + "main/static/login-logo.png", "OEM_RES/" + ossCode + "/h5/login-logo.png");
        replaceFileByOSSFile(logger, h5BinPath + "main/static/logo.png", "OEM_RES/" + ossCode + "/h5/logo.png");
        replaceFileByOSSFile(logger, h5BinPath + "screen/static/common_ad.jpg", "OEM_RES/" + ossCode + "/h5/common_ad.jpg");
        replaceFileByOSSFile(logger, h5BinPath + "screen/static/common_ad2.jpg", "OEM_RES/" + ossCode + "/h5/common_ad2.jpg");
        String updateUrl = "";
        String pathUrl = "";

        String isaloneupgrade = MapUtils.getString(objectMap, "isaloneupgrade", "0");
        if ("1".equals(isaloneupgrade)) {
            //1.拉取Nsis   http://svn.cnzhonglunnet.com/svn/zlnet/code/CIProject/NSIS
            //下载svn代码
            checkOutSvn(logger, "http://svn.cnzhonglunnet.com/svn/zlnet/code/CIProject/NSIS", nsisCodePath);

            //2. Nsis目录中Define下env.txt version.txt两个文件，环境和版本号，生成2个文件
            String envTxtFilePath = nsisCodePath + "Define/env.txt";
            String versionTxtFilePath = nsisCodePath + "Define/version.txt";
            writeFile(logger, envTxtFilePath, env);
            writeFile(logger, versionTxtFilePath, appVersion);

            //3.logo和oem的ui 协议
            replaceFileByOSSFile(logger, nsisCodePath + "logo.ico", "OEM_RES/" + ossCode + "/windows/logo.ico");
            replaceFileByOSSFile(logger, nsisCodePath + "License.txt", "OEM_RES/" + ossCode + "/windows/License.txt");
            replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "[assembly: AssemblyCompany", "[assembly: AssemblyCompany(\"" + company + "\")]");

            //;发布人
            replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "PRODUCT_PUBLISHER", "!define PRODUCT_PUBLISHER \"" + publisher + "\"");
            //;网址
            replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "PRODUCT_WEB_SITE", "!define PRODUCT_WEB_SITE \"" + website + "\"");
            //;公司名
            replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define COMPANY_NAME", "!define COMPANY_NAME \"" + company + "\"");
            //nsNiuniuSkin::SetControlAttribute $hInstallDlg "licensename" "text" "苏州中仑用户许可协议"
            replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "licensename", "nsNiuniuSkin::SetControlAttribute $hInstallDlg \"licensename\" \"text\" \"" + company + "用户许可协议\"");

            //5.修改commonfunc.nsh
            //CreateShortCut "$DESKTOP\中仑零售.lnk" "$INSTDIR\${EXE_NAME}"
            replaceFileStr2(logger, nsisCodePath + "NSISOrigin/commonfunc.nsh", "CreateShortCut \"$DESKTOP\\中仑零售.lnk\" \"$INSTDIR\\${EXE_NAME}\"", "CreateShortCut \"$DESKTOP\\" + appName + ".lnk\" \"$INSTDIR\\${EXE_NAME}\"");
            replaceFileStr2(logger, nsisCodePath + "NSISOrigin/commonfunc.nsh", "Delete \"$DESKTOP\\中仑零售.lnk\"", "Delete \"$DESKTOP\\" + appName + ".lnk\"");

            //6.将以上生成的bin目录所有文件，复制到NSIS的路径Resources下
            copyDirectoryToDirectory(logger, codePath + "ZlPos/bin/", nsisCodePath + "Resources/", false);
            YIWFileUtil.createTxt(logger,nsisCodePath + "Resources/registercode.txt", String.valueOf(System.currentTimeMillis()));

            //执行signbat文件
            startAutoSign(nsisCodePath + "Resources/", logger);
            startAutoSign(nsisCodePath + "ResourcesWIN7/", logger);
            startAutoSign(nsisCodePath + "ResourcesXP/", logger);

            if ("1".equals(lbImageCount)) {
                downloadOSSFile(logger, recordPath + "skin.zip", "OEM_RES/" + ossCode + "/windows/skin.zip");
                ZipFileUtil.UnZipFiles(logger, recordPath + "skin.zip", recordPath + "skin");
                replaceFileStr2(logger, recordPath + "skin/installingpage.xml", "ImageShow", "<ImageShow images=\"bg3.png\" imagecount=\"1\" loop=\"false\" width=\"570\" height=\"345\" />");
                ZipFiles(logger, new File(recordPath + "skin").listFiles(), new File(nsisCodePath + "skin.zip"), "", false);
            } else {
                //替换本地skin.zip
                replaceFileByOSSFile(logger, nsisCodePath + "skin.zip", "OEM_RES/" + ossCode + "/windows/skin.zip");
            }

            File[] oldFiles = new File(nsisCodePath + "Output").listFiles();

            String nsisBuildBatPath = nsisCodePath + "build-ZlPos4Issue.bat";

            //调用build-ZLPos.bat 执行完成check Total size
            execCmd(logger, nsisBuildBatPath, nsisCodePath, "Total size");

            //writeFile(nsisCodePath + "Output/dfasdfasdfasdf.exe", "121231313");

            File[] newFiles = new File(nsisCodePath + "Output").listFiles();

            File exeFile = getComparedFile(oldFiles, newFiles);
            if (exeFile == null) {
                throw new RuntimeException("安装包生成失败！");
            }
            logger.info("安装包生成成功：" + exeFile.getAbsolutePath());
            //执行签名文件
            startAutoSign(nsisCodePath + "Output/", logger);

            updateUrl = "";

        } else {
            //5.下载之前oem配置的组件到指定目录（D: \20191130\ZlPos\bin）具体位置根据DynamicFormDllPath字段 todo 闪屏界面可替换
            if (isIncrement.equals("1")) {
                OssFileUtil.downloadOSSFile(logger, lastRecordCodeBinPath, lastRecordCodeBinPath + "buildcode.zip", OSS_OEM_BUILD_CODE + lastBase + ".zip");
                ZipFileUtil.UnZipFiles(logger, lastRecordCodeBinPath + "buildcode.zip", lastRecordCodeBinPath);
                //跟lastbase对比差异
                //生成file.xml
                // 对比的增量文件包括目录结构和file.xml生成zip文件，zip文件名随机生成
                File compareXmlFile = CompareFolder.compareFolder(logger, lastRecordCodeBinPath, codeBinPath, getExistFolderFile(recordPath + "increment/").getAbsolutePath(), recordPath + "file.xml");
                //执行signbat文件 签名
                startAutoSign(recordPath + "increment/", logger);

                File[] files = getExistFolderFile(recordPath + "increment/").listFiles();
                File[] zipFiles = ArrayUtils.add(files, compareXmlFile);

                //update.xml和exe生成zip包
                String zipName = UUID.randomUUID().toString() + ".zip";
                String zipFilePath = recordPath + zipName;
                ZipFiles(logger, zipFiles, new File(zipFilePath), "", false);
                String zipOssKey = OSS_OEM_APP_ENV_PATH + zipName;
                uploadOss(zipFilePath, zipOssKey, logger);
                String zipOssPath = "http://041001.zhonglunnet.com/" + zipOssKey;
                pathUrl = zipOssPath;
                //update.xml和exe生成zip包
                String upgradeType = "1";
                String packageKey = getFileMD5(logger, zipFilePath);
                long packageSize = getFileSize(logger, zipFilePath);

                String updateJsonOssKey = OSS_OEM_APP_ENV_PATH + UUID.randomUUID().toString() + ".json";
                String updateJsonOssPath = "http://041001.zhonglunnet.com/" + updateJsonOssKey;

                File mapConfigJsonFile = updateMapConfigJson(logger, appCodePath + "mapconfig.json", deviceCodes, updateJsonOssPath, isIncrement);
                copyFileToDirectory(logger, mapConfigJsonFile.getAbsolutePath(), recordPath);
                File updateJsonFile = createUpdateJson(lastRecordPath + "update.json", recordPath + "update.json", null, zipOssPath, appVersion, upgradeType, packageKey, zipName, packageSize, upgradeLog, isforce, isIgnorVersion);
                uploadOss(updateJsonFile.getAbsolutePath(), updateJsonOssKey, logger);
                uploadOss(mapConfigJsonFile.getAbsolutePath(), OSS_OEM_APP_ENV_PATH + env + "/mapconfig.json", logger);

                updateUrl = updateJsonOssPath;
            }
            // h5 热发布
            else if ("2".equals(isIncrement)) {
                OssFileUtil.downloadOSSFile(logger, lastRecordCodeBinPath, lastRecordCodeBinPath + "buildcode.zip", OSS_OEM_BUILD_CODE + lastBase + ".zip");
                //跟lastsuccess h5 对比差异
                // 对比的增量文件生成zip文件，zip文件名随机生成
                List<File> fileList = CompareH5Folder.compareFolder(logger, lastRecordH5BinPath, h5BinPath, getExistFolderFile(recordPath + "incrementh5/").getAbsolutePath());
                File[] files = new File[fileList.size()];
                if (CollectionUtils.isNotEmpty(fileList)) {
                    fileList.toArray(files);
                }

                File[] zipFiles = files;

                //update.xml和exe生成zip包
                String zipName = UUID.randomUUID().toString() + ".zip";
                String zipFilePath = recordPath + zipName;
                ZipFiles(logger, zipFiles, new File(zipFilePath), new File(recordPath + "incrementh5/").getAbsolutePath(), true);
                String zipOssKey = OSS_OEM_APP_ENV_PATH + zipName;
                uploadOss(zipFilePath, zipOssKey, logger);
                String zipOssPath = "http://041001.zhonglunnet.com/" + zipOssKey;
                pathUrl = zipOssPath;
                //update.xml和exe生成zip包
                String packageKey = getFileMD5(logger, zipFilePath);

                String updateXmlOssKey = OSS_OEM_APP_ENV_PATH + UUID.randomUUID().toString() + ".xml";
                String updateXmlOssPath = "http://041001.zhonglunnet.com/" + updateXmlOssKey;
                String h5Version = MapUtils.getString(getJsonMapFromFile(h5CodePath + "/config.json"), "version");

                File mapConfigJsonFile = updateH5MapConfigJson(logger, appCodePath + "h5mapconfig.json", deviceCodes, updateXmlOssPath, isIncrement);
                copyFileToDirectory(logger, mapConfigJsonFile.getAbsolutePath(), recordPath);
                File updateXmlFile = createH5UpdateXml(recordPath + "updateh5.xml", zipOssPath, packageKey, env, h5Version);
                uploadOss(updateXmlFile.getAbsolutePath(), updateXmlOssKey, logger);
                uploadOss(mapConfigJsonFile.getAbsolutePath(), OSS_OEM_APP_ENV_PATH + env + "/h5mapconfig.json", logger);

                updateUrl = updateXmlOssPath;

            } else if ("0".equals(isIncrement)) {

                //1.拉取Nsis   http://svn.cnzhonglunnet.com/svn/zlnet/code/CIProject/NSIS
                //下载svn代码
                checkOutSvn(logger, "http://svn.cnzhonglunnet.com/svn/zlnet/code/CIProject/NSIS", nsisCodePath);

                //2. Nsis目录中Define下env.txt version.txt两个文件，环境和版本号，生成2个文件
                String envTxtFilePath = nsisCodePath + "Define/env.txt";
                String versionTxtFilePath = nsisCodePath + "Define/version.txt";
                writeFile(logger, envTxtFilePath, env);
                writeFile(logger, versionTxtFilePath, appVersion);

                //3.logo和oem的ui 协议
                replaceFileByOSSFile(logger, nsisCodePath + "logo.ico", "OEM_RES/" + ossCode + "/windows/logo.ico");
                replaceFileByOSSFile(logger, nsisCodePath + "License.txt", "OEM_RES/" + ossCode + "/windows/License.txt");
                replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "[assembly: AssemblyCompany", "[assembly: AssemblyCompany(\"" + company + "\")]");

                //;发布人
                replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "PRODUCT_PUBLISHER", "!define PRODUCT_PUBLISHER \"" + publisher + "\"");
                //;网址
                replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "PRODUCT_WEB_SITE", "!define PRODUCT_WEB_SITE \"" + website + "\"");
                //;公司名
                replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define COMPANY_NAME", "!define COMPANY_NAME \"" + company + "\"");
                //nsNiuniuSkin::SetControlAttribute $hInstallDlg "licensename" "text" "苏州中仑用户许可协议"
                replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "licensename", "nsNiuniuSkin::SetControlAttribute $hInstallDlg \"licensename\" \"text\" \"" + company + "用户许可协议\"");

                //5.修改commonfunc.nsh
                //CreateShortCut "$DESKTOP\中仑零售.lnk" "$INSTDIR\${EXE_NAME}"
                replaceFileStr2(logger, nsisCodePath + "NSISOrigin/commonfunc.nsh", "CreateShortCut \"$DESKTOP\\中仑零售.lnk\" \"$INSTDIR\\${EXE_NAME}\"", "CreateShortCut \"$DESKTOP\\" + appName + ".lnk\" \"$INSTDIR\\${EXE_NAME}\"");
                replaceFileStr2(logger, nsisCodePath + "NSISOrigin/commonfunc.nsh", "Delete \"$DESKTOP\\中仑零售.lnk\"", "Delete \"$DESKTOP\\" + appName + ".lnk\"");


                if (svnPath.contains("stableposv5_win")) {
                    replaceWindowsV5Path(logger, nsisCodePath);
                }

                //6.将以上生成的bin目录所有文件，复制到NSIS的路径Resources下
                copyDirectoryToDirectory(logger, codePath + "ZlPos/bin/", nsisCodePath + "Resources/", false);

                YIWFileUtil.createTxt(logger,nsisCodePath + "Resources/registercode.txt", String.valueOf(System.currentTimeMillis()));

                //增加registercode.txt
                insertTextRegisterCode(logger,nsisCodePath + "Resources/");
                //执行signbat文件
                startAutoSign(nsisCodePath + "Resources/", logger);
                startAutoSign(nsisCodePath + "ResourcesWIN7/", logger);
                startAutoSign(nsisCodePath + "ResourcesXP/", logger);

                if ("1".equals(lbImageCount)) {
                    downloadOSSFile(logger, recordPath + "skin.zip", "OEM_RES/" + ossCode + "/windows/skin.zip");
                    ZipFileUtil.UnZipFiles(logger, recordPath + "skin.zip", recordPath + "skin");
                    replaceFileStr2(logger, recordPath + "skin/installingpage.xml", "ImageShow", "<ImageShow images=\"bg3.png\" imagecount=\"1\" loop=\"false\" width=\"570\" height=\"345\" />");
                    ZipFiles(logger, new File(recordPath + "skin").listFiles(), new File(nsisCodePath + "skin.zip"), "", false);
                } else {
                    //替换本地skin.zip
                    replaceFileByOSSFile(logger, nsisCodePath + "skin.zip", "OEM_RES/" + ossCode + "/windows/skin.zip");
                }

                File[] oldFiles = new File(nsisCodePath + "Output").listFiles();

                String nsisBuildBatPath = nsisCodePath + "build-ZlPos4Issue.bat";

                //调用build-ZLPos.bat 执行完成check Total size
                // execCmd(logger, nsisBuildBatPath, nsisCodePath, "Total size");
                CMDUtil.execCmd(nsisBuildBatPath, nsisCodePath);
                //writeFile(nsisCodePath + "Output/dfasdfasdfasdf.exe", "121231313");

                File[] newFiles = new File(nsisCodePath + "Output").listFiles();

                File exeFile = getComparedFile(oldFiles, newFiles);
                if (exeFile == null) {
                    throw new RuntimeException("安装包生成失败！");
                }
                logger.info("安装包生成成功：" + exeFile.getAbsolutePath());

                //执行签名文件
                startAutoSign(nsisCodePath + "Output/", logger);

                //生成update.xml
                File updateXmlFile = createUpdateXml(logger, recordPath + "update.xml", exeFile.getName());

                File[] files = new File[]{
                        updateXmlFile,
                        exeFile
                };

                //update.xml和exe生成zip包
                String zipName = UUID.randomUUID().toString() + ".zip";
                String zipFilePath = recordPath + zipName;
                ZipFiles(logger, files, new File(zipFilePath), "", false);
                String zipOssKey = OSS_OEM_APP_ENV_PATH + zipName;
                uploadOss(zipFilePath, zipOssKey, logger);

                if ("pro".equals(env)||"prod".equals(env)) {
                    String gwzipOssKey = "gw/" + serverCode + "/" + appCode + "/" + appName + appCode + ".zip";
                    uploadOss(zipFilePath, gwzipOssKey, logger);
                }
                String zipOssPath = "http://041001.zhonglunnet.com/" + zipOssKey;
                pathUrl = zipOssPath;
                String exeFileName = exeFile.getName();
                String upgradeType = "0";
                String packageKey = getFileMD5(logger, zipFilePath);
                long packageSize = getFileSize(logger, zipFilePath);

                String updateJsonOssKey = OSS_OEM_APP_ENV_PATH + UUID.randomUUID().toString() + ".json";
                String updateJsonOssPath = "http://041001.zhonglunnet.com/" + updateJsonOssKey;

                File mapConfigJsonFile = updateMapConfigJson(logger, appCodePath + "mapconfig.json", deviceCodes, updateJsonOssPath, isIncrement);
                copyFileToDirectory(logger, mapConfigJsonFile.getAbsolutePath(), recordPath);
                File updateJsonFile = createUpdateJson(null, recordPath + "update.json", exeFileName, zipOssPath, appVersion, upgradeType, packageKey, zipName, packageSize, upgradeLog, isforce, isIgnorVersion);
                uploadOss(updateJsonFile.getAbsolutePath(), updateJsonOssKey, logger);
                uploadOss(mapConfigJsonFile.getAbsolutePath(), OSS_OEM_APP_ENV_PATH + env + "/mapconfig.json", logger);

                //将全量打包文件上传oss做增量打包用
                //先压缩成zip
                ZipCompressor zc = new ZipCompressor(zipCodeBinPath + "buildcode.zip");
                zc.compress(codeBinPath);

                uploadOss(zipCodeBinPath + "buildcode.zip", OSS_OEM_BUILD_CODE + recordNo + ".zip", logger);

                updateUrl = updateJsonOssPath;
                updateLastBase(MapUtils.getString(objectMap, "id"), recordNo);

            } else {
                //异常
            }
            updateLastSuccess(MapUtils.getString(objectMap, "id"), recordNo);
        }
        CommonUtil.sendDingTalkMessage("win", appName, svnPath, env, isIncrementText, appVersion, pathUrl, registryip, "稳定版", industry, createtime, publisher);
        if (isIncrement.equals("0")) {
            CommonUtil.sendYunXiaoMessage("Windows", appName, "稳定版", svnPath, env.equals("pro") ? env + "d" : env, appVersion, pathUrl, logger, registryip);
        }
        logger.info("##########success##########");

        return updateUrl;
    }

    private void insertTextRegisterCode(Logger logger, String path) {

    }

    private void replaceWindowsV5Path(Logger logger, String nsisCodePath) {
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define PRODUCT_NAME \"CloudPos\"", "!define PRODUCT_NAME \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define InternalName \"CloudPos\"", "!define InternalName \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define SMPROGRAMS_Name   \"CloudPos\"", "!define SMPROGRAMS_Name   \"zl\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define PRODUCT_PATHNAME", "!define PRODUCT_PATHNAME            \"2021zlv5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\makensis.exe\"", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\zlv5.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "InstallDir \"$PROGRAMFILES\\CloudPos\"", "InstallDir \"$PROGRAMFILES\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "RMDir \"$SMPROGRAMS\\CloudPos\"", "RMDir \"$SMPROGRAMS\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Website.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosAutoChange.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\CloudPos.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\CloudPosV5.lnk\"");

        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "!define PRODUCT_NAME \"CloudPos\"", "!define PRODUCT_NAME \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "!define InternalName \"CloudPos\"", "!define InternalName \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "!define SMPROGRAMS_Name   \"CloudPos\"", "!define SMPROGRAMS_Name   \"zl\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "!define PRODUCT_PATHNAME", "!define PRODUCT_PATHNAME            \"2021zlv5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\makensis.exe\"", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\zlv5.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "InstallDir \"$PROGRAMFILES\\CloudPos\"", "InstallDir \"$PROGRAMFILES\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "RMDir \"$SMPROGRAMS\\CloudPos\"", "RMDir \"$SMPROGRAMS\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Website.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosExceptCEF.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\CloudPos.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\CloudPosV5.lnk\"");

        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "!define PRODUCT_NAME \"CloudPos\"", "!define PRODUCT_NAME \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "!define InternalName \"CloudPos\"", "!define InternalName \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "!define SMPROGRAMS_Name   \"CloudPos\"", "!define SMPROGRAMS_Name   \"zl\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "!define PRODUCT_PATHNAME", "!define PRODUCT_PATHNAME            \"2021zlv5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\makensis.exe\"", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\zlv5.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "InstallDir \"$PROGRAMFILES\\CloudPos\"", "InstallDir \"$PROGRAMFILES\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "RMDir \"$SMPROGRAMS\\CloudPos\"", "RMDir \"$SMPROGRAMS\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Website.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosInstallCount.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\CloudPos.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\CloudPosV5.lnk\"");

        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "!define PRODUCT_NAME \"CloudPos\"", "!define PRODUCT_NAME \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "!define InternalName \"CloudPos\"", "!define InternalName \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "!define SMPROGRAMS_Name   \"CloudPos\"", "!define SMPROGRAMS_Name   \"zl\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "!define PRODUCT_PATHNAME", "!define PRODUCT_PATHNAME            \"2021zlv5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\makensis.exe\"", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\zlv5.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "InstallDir \"$PROGRAMFILES\\CloudPos\"", "InstallDir \"$PROGRAMFILES\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "RMDir \"$SMPROGRAMS\\CloudPos\"", "RMDir \"$SMPROGRAMS\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Website.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosWIN7.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\CloudPos.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\CloudPosV5.lnk\"");

        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "!define PRODUCT_NAME \"CloudPos\"", "!define PRODUCT_NAME \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "!define InternalName \"CloudPos\"", "!define InternalName \"CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "!define SMPROGRAMS_Name   \"CloudPos\"", "!define SMPROGRAMS_Name   \"zl\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "!define PRODUCT_PATHNAME", "!define PRODUCT_PATHNAME            \"2021zlv5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\makensis.exe\"", "!define PRODUCT_DIR_REGKEY \"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\zlv5.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "InstallDir \"$PROGRAMFILES\\CloudPos\"", "InstallDir \"$PROGRAMFILES\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\" \"$INSTDIR\\${PRODUCT_NAME}.url\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "CreateShortCut \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"", "CreateShortCut \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\" \"$INSTDIR\\uninst.exe\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "RMDir \"$SMPROGRAMS\\CloudPos\"", "RMDir \"$SMPROGRAMS\\CloudPosV5\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Uninstall.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Uninstall.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\Website.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\Website.lnk\"");
        replaceFileStr2(logger, nsisCodePath + "CloudPosXP.nsi", "Delete \"$SMPROGRAMS\\CloudPos\\CloudPos.lnk\"", "Delete \"$SMPROGRAMS\\CloudPosV5\\CloudPosV5.lnk\"");

    }


    private void downH5Svn(String h5homesvnpath, String h5maintargetPath, String h5maintargetPathZip, String h5seconsvnpath, String h5screensvnpath, String h5screensvnpathZip, Logger logger) {
        FileUtil.downloadFile(h5homesvnpath, h5maintargetPath, h5maintargetPathZip);
        FileUtil.unZip(new File(h5maintargetPathZip), h5maintargetPath);
        FileUtil.deleteFile(logger, h5maintargetPathZip);

        FileUtil.downloadFile(h5seconsvnpath, h5screensvnpath, h5screensvnpathZip);
        FileUtil.unZip(new File(h5screensvnpathZip), h5screensvnpath);
        FileUtil.deleteFile(logger, h5screensvnpathZip);
    }

    public void startAutoSign(String codeBinPath, Logger logger) {
        //复制签名文件到对应目录
        List<String> pathlist = copyDirectoryToDirectory(logger, SystemConfig.OEM_WINDOWS_SIGN_PATH, codeBinPath, false);
        logger.info("开始签名" + codeBinPath);
        //执行签名文件
        CMDUtil.execCmd(codeBinPath + "autosign.bat", codeBinPath);
        logger.info("执行签名完成");
        for (String path : pathlist) {
            deleteFile(logger, codeBinPath + path);
        }
        logger.info("删除签名工具");
    }


    /**
     * @param filePath
     * @return
     */
    private Map<String, Object> getJsonMapFromFile(String filePath) {
        File file = new File(filePath);
        Map<String, Object> jsonMap = null;

        if (file.exists()) {

            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {

                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();

                String jsonString = sb.toString();

                if (StringUtils.isNotEmpty(jsonString)) {
                    jsonMap = JSONObject.fromObject(jsonString);
                }
            } catch (Exception ex) {
                throw new RuntimeException("error");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (MapUtils.isEmpty(jsonMap)) {
            throw new RuntimeException("从文件获取json为空：" + filePath);
        }
        return jsonMap;
    }

    /**
     * @param filePath
     * @param exeFileName
     * @param zipOssPath
     * @param appVersion
     * @param upgradeType
     * @param packageKey
     * @param zipName
     * @param packageSize
     * @param upgradeLog
     * @param isforce
     * @param isIgnorVersion
     * @return
     */
    private File createUpdateJson(String oldPath, String filePath, String exeFileName, String zipOssPath, String appVersion, String upgradeType, String packageKey, String zipName, long packageSize, String upgradeLog, String isforce, String isIgnorVersion) {

        Map<String, Object> jsonMap = null;
        if (StringUtils.isNotEmpty(oldPath)) {
            File file = new File(oldPath);
            if (file.exists()) {

                FileInputStream fileInputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;

                try {

                    fileInputStream = new FileInputStream(file);
                    inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                    bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                        sb.append(" ");
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();

                    String jsonString = sb.toString();

                    if (StringUtils.isNotEmpty(jsonString)) {
                        jsonMap = JSONObject.fromObject(jsonString);
                    }

                } catch (Exception ex) {
                    throw new RuntimeException("error");
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (MapUtils.isEmpty(jsonMap)) {
            jsonMap = new HashMap<>();
        }

        if (StringUtils.isNotEmpty(exeFileName)) {
            jsonMap.put("appname", exeFileName);
        }
        jsonMap.put("url", zipOssPath);
        jsonMap.put("versionno", appVersion);
        jsonMap.put("upgradetype", upgradeType);
        jsonMap.put("packagekey", packageKey);
        jsonMap.put("packagename", zipName);
        jsonMap.put("packagesize", packageSize);
        jsonMap.put("versiondesc", upgradeLog);
        jsonMap.put("isforce", isforce);
        jsonMap.put("isIgnorVersion", isIgnorVersion);

        String newJsonString = JSONObject.fromObject(jsonMap).toString();

        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {
            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write(newJsonString);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newFile;
    }

    /**
     * @param filePath
     * @param zipOssPath
     * @param packageKey
     * @return
     */
    private File createH5UpdateXml(String filePath, String zipOssPath, String packageKey, String env, String h5Version) {

        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write("<Config>");
            writer.write("<md5>" + packageKey + "</md5>");
            writer.write("<downloadurl>" + zipOssPath + "</downloadurl>");
            writer.write("<truncheck>true</truncheck>");
            writer.write("<environment>" + env + "</environment>");
            writer.write("<verison>" + h5Version + "</verison>");

            writer.write("</Config>");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newFile;
    }

    /**
     * @param logger
     * @param filePath
     * @param packageName
     * @return
     */
    private File createUpdateXml(Logger logger, String filePath, String packageName) {
        BufferedWriter writer = null;
        File file = new File(filePath);
        // 创建文件
        try {

            file.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<UpdateInfo>\n");
            writer.write("<AppName>" + packageName + "</AppName>\n");
            writer.write("</UpdateInfo>");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * @param filePath
     * @param deviceCodes
     * @param updateJsonOssPath
     * @return
     */
    private File updateMapConfigJson(Logger logger, String filePath, String deviceCodes, String updateJsonOssPath, String isIncrement) {
        deleteFile(logger, filePath);
        File file = new File(filePath);
        Map<String, Object> jsonMap = null;

        if (file.exists()) {

            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {

                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();

                String jsonString = sb.toString();

                if (StringUtils.isNotEmpty(jsonString)) {
                    jsonMap = JSONObject.fromObject(jsonString);
                }

            } catch (Exception ex) {
                throw new RuntimeException("error");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (MapUtils.isEmpty(jsonMap)) {
            jsonMap = new HashMap<String, Object>();
        }

        if (StringUtils.isNotEmpty(deviceCodes)) {
            String[] deviceCodeArr = deviceCodes.split(",");
            for (String deviceCode : deviceCodeArr) {
                jsonMap.put(deviceCode, updateJsonOssPath);
            }
        } else {
            if (isIncrement.equals("0")) {
                jsonMap.put("Base", updateJsonOssPath);
            }
            jsonMap.put("Default", updateJsonOssPath);
        }

        deleteFile(logger, filePath);

        String newJsonString = JSONObject.fromObject(jsonMap).toString();
        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write(newJsonString);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newFile;
    }

    /**
     * @param filePath
     * @param deviceCodes
     * @param updateJsonOssPath
     * @return
     */
    private File updateH5MapConfigJson(Logger logger, String filePath, String deviceCodes, String updateJsonOssPath, String isIncrement) {
        File file = new File(filePath);
        Map<String, Object> jsonMap = null;

        if (file.exists()) {

            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {

                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();

                String jsonString = sb.toString();

                if (StringUtils.isNotEmpty(jsonString)) {
                    jsonMap = JSONObject.fromObject(jsonString);
                }

            } catch (Exception ex) {
                throw new RuntimeException("error");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (MapUtils.isEmpty(jsonMap)) {
            jsonMap = new HashMap<String, Object>();
        }

        if (StringUtils.isNotEmpty(deviceCodes)) {
            String[] deviceCodeArr = deviceCodes.split(",");
            for (String deviceCode : deviceCodeArr) {
                jsonMap.put(deviceCode, updateJsonOssPath);
            }
        } else {
//            if (isIncrement.equals("0")) {
//                jsonMap.put("Base", updateJsonOssPath);
//            }
            jsonMap.put("Default", updateJsonOssPath);
        }

        deleteFile(logger, filePath);

        String newJsonString = JSONObject.fromObject(jsonMap).toString();
        BufferedWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
            writer.write(newJsonString);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newFile;
    }

    /**
     * @param filePath
     * @return
     */
    private static String getFileMD5(Logger logger, String filePath) {
        try {
            String md5 = DigestUtils.md5Hex(new FileInputStream(filePath));
            logger.info("获取MD5成功:" + md5);
            return md5;
        } catch (IOException e) {
            logger.info("获取MD5失败");
            throw new RuntimeException("error");
        }
    }

    /**
     * @param localPath
     * @param ossPath
     * @param logger
     */
    private void replaceFileByOSSFile(Logger logger, String localPath, String ossPath) {
        File file = new File(localPath);
        if (file.exists()) {
            file.delete();
            logger.info("删除" + localPath);
        } else {
            logger.error("删除" + localPath + "失败，文件不存在！");
        }

        InputStream res3InputStream = OssUtil.getObjectContent(ossPath);
        try {
            InputStream2File.handle(res3InputStream, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("下载" + OSS_BASE_PATH + ossPath + "到" + localPath);
    }


    public static void killProcess() {
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param svnPath
     * @param targetPath
     */
    private static void checkOutSvn(Logger logger, String svnPath, String targetPath) {
        logger.info("检出代码：" + svnPath + "到" + targetPath + "目录开始");
        try {
            File targetFile = new File(targetPath);
            if (targetFile.exists()) {
                FileUtils.cleanDirectory(targetFile);
            } else {
                targetFile.mkdirs();
            }

            String svnUserName = "wgb";
            String svnPassWord = "wgb";

            SVNKit svnKit = new SVNKit();
            svnKit.setTargetPath(targetPath);
            svnKit.setSvnPath(svnPath);
            svnKit.setSvnUserName(svnUserName);
            svnKit.setSvnPassWord(svnPassWord);
            svnKit.setSvnPath(svnPath);
            svnKit.checkOut();
            logger.info("检出代码：" + svnPath + "到" + targetPath + "目录成功");
        } catch (Exception ex) {
            logger.info("检出代码：" + svnPath + "到" + targetPath + "目录失败");
            throw new RuntimeException("error");
        }
    }

    /**
     * @param filePath
     * @param ossKey
     * @param logger
     * @return
     */
    private static boolean uploadOss(String filePath, String ossKey, Logger logger) {
        //上传apk包、升级检测xml
        logger.info("开始上传" + filePath + "文件，文件地址http://041001.zhonglunnet.com/" + ossKey);
        try {
            OssUtil.uploadFile2OSS(new FileInputStream(filePath), ossKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info("上传失败" + filePath + "文件，文件地址http://041001.zhonglunnet.com/" + ossKey);
            return false;
        }
        logger.info("完成上传" + filePath + "文件，文件地址http://041001.zhonglunnet.com/" + ossKey);
        return true;
    }

    /**
     * @param filePath
     * @param ossKey
     * @return
     */
    private static void uploadOss(String filePath, String ossKey) {
        //上传apk包、升级检测xml
        try {
            OssUtil.uploadFile2OSS(new FileInputStream(filePath), ossKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败" + filePath + "文件，文件地址http://041001.zhonglunnet.com/" + ossKey);
        }
    }

    /**
     * 替换指定文件中的指定内容
     *
     * @param filePath  文件路径
     * @param sourceStr 文件需要替换的内容
     * @param targetStr 替换后的内容
     * @return 替换成功返回true，否则返回false
     */
    public static void replaceFileStr2(Logger logger, String filePath, String sourceStr, String targetStr) {

        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        CharArrayWriter tempStream = null;
        try {
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            tempStream = new CharArrayWriter();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String str = "";
                if (line.indexOf(sourceStr) != -1) {
                    str = targetStr;
                } else {
                    str = line;
                }
                // 将该行写入内存
                tempStream.write(str);
                // 添加换行符
                tempStream.append(System.getProperty("line.separator"));
            }

        } catch (Exception ex) {
            //logger.info("替换文件：" + filePath + " " + sourceStr + " 为 " + targetStr + "失败");
            throw new RuntimeException("error");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        BufferedWriter bufferedWriter = null;
        FileOutputStream writerStream = null;
        try {
            writerStream = new FileOutputStream(filePath, false);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            tempStream.writeTo(bufferedWriter);
            logger.info("替换文件：" + filePath + " " + sourceStr + " 为 " + targetStr + "成功");
        } catch (Exception ex) {
            logger.info("替换文件：" + filePath + " " + sourceStr + " 为 " + targetStr + "失败");
            throw new RuntimeException("error");
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writerStream != null) {
                try {
                    writerStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param logger
     * @param filePath
     * @param content
     */
    private static void writeFile(Logger logger, String filePath, String content) {
        FileWriter writer = null;
        File newFile = new File(filePath);
        // 创建文件
        try {

            newFile.createNewFile();
            writer = new FileWriter(newFile);
            writer.write(content);
            writer.flush();
            writer.close();

            logger.info("生成文件成功：" + filePath);

        } catch (IOException e) {
            throw new RuntimeException("error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param oldFiles
     * @param newFiles
     * @return
     */
    private static File getComparedFile(File[] oldFiles, File[] newFiles) {
        for (File newFile : newFiles) {
            boolean exist = false;
            for (File oldFile : oldFiles) {
                if (newFile.getName().equals(oldFile.getName())) {
                    exist = true;
                }
            }
            if (!exist) {
                return newFile;
            }
        }
        return null;
    }

    /**
     * @param srcfile
     * @param zipfile
     */
    private static void ZipFiles(Logger logger, File[] srcfile, File zipfile, String srcBaseFile, boolean containFolder) {

        if (zipfile.exists()) {
            try {
                FileUtils.forceDelete(zipfile);
            } catch (IOException e) {
                throw new RuntimeException("压缩失败，删除已存在文件异常！");
            }
        }

        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(containFolder ? srcfile[i].getAbsolutePath().replace(srcBaseFile + File.separator, "") : srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            logger.info("压缩文件成功：" + zipfile.getAbsolutePath());
        } catch (IOException e) {
            logger.info("压缩文件失败：" + zipfile.getAbsolutePath());
            throw new RuntimeException("error");
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param filePath
     * @return
     */
    private static long getFileSize(Logger logger, String filePath) {
        try {
            long fileSize = new FileInputStream(new File(filePath)).available();
            logger.info("获取文件大小成功：" + fileSize);
            return fileSize;
        } catch (IOException e) {
            logger.info("获取文件大小失败");
            throw new RuntimeException("error");
        }
    }

    /**
     * @param folderPath
     * @return
     */
    private static File getExistFolderFile(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * @param filePath
     * @return
     */
    private static void deleteFile(Logger logger, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
                logger.info("删除文件" + filePath + "成功");
            } catch (IOException e) {
                logger.info("删除文件" + filePath + "异常");
                throw new RuntimeException("error");
            }
        } else {
            logger.info("删除文件" + filePath + "失败，文件不存在");
        }
    }

    /**
     * @param directoryPath
     * @return
     */
    private static void deleteDirectory(Logger logger, String directoryPath) {
        File file = new File(directoryPath);
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
                logger.info("删除目录" + directoryPath + "成功");
            } catch (IOException e) {
                logger.info("删除目录" + directoryPath + "异常");
                throw new RuntimeException("error");
            }
        } else {
            logger.info("删除目录" + directoryPath + "失败，文件不存在");
        }
    }

    /**
     * @param logger
     * @param fromPath
     * @param toPath
     */
    private static List<String> copyDirectoryToDirectory(Logger logger, String fromPath, String toPath, boolean containFromDirectory) {
        List<String> pathlist = new ArrayList<>();
        if (containFromDirectory) {
            try {
                FileUtils.copyDirectoryToDirectory(new File(fromPath), getExistFolderFile(toPath));
                logger.info("复制目录" + fromPath + "到目录" + toPath + "成功");
            } catch (IOException e) {
                logger.info("复制目录" + fromPath + "到目录" + toPath + "失败");
                throw new RuntimeException("error");
            }
        } else {
            File file = new File(fromPath);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files.length > 0) {
                    for (File item : files) {
                        pathlist.add(item.getName());
                        if (item.isDirectory()) {
                            try {
                                FileUtils.copyDirectoryToDirectory(item, getExistFolderFile(toPath));
                            } catch (IOException e) {
                                logger.info("复制目录" + fromPath + "到目录" + toPath + "失败");
                                throw new RuntimeException("error");
                            }
                        } else {
                            try {
                                FileUtils.copyFileToDirectory(item, getExistFolderFile(toPath));
                            } catch (IOException e) {
                                logger.info("复制目录" + fromPath + "到目录" + toPath + "失败");
                                throw new RuntimeException("error");
                            }
                        }
                    }
                }
            }
        }
        return pathlist;
    }

    /**
     * @param logger
     * @param srcFilePath
     * @param destDir
     */
    private void copyFileToDirectory(Logger logger, String srcFilePath, String destDir) {
        try {
            FileUtils.copyFileToDirectory(new File(srcFilePath), new File(destDir));
            logger.info("复制文件" + srcFilePath + "到目录" + destDir + "成功");
        } catch (IOException e) {
            logger.info("复制文件" + srcFilePath + "到目录" + destDir + "成功");
            throw new RuntimeException("error");
        }
    }

    /**
     * @param bat
     * @param batDirectory
     */
    private static void execCmd(Logger logger, String bat, String batDirectory, String checkString) {
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(bat, new String[]{"path=C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Community\\MSBuild\\15.0\\Bin\\"}, new File(batDirectory));
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (StringUtils.isNotEmpty(line)) {
                    logger.info(line);
                }
                sb.append(line + "\n");
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException("error");
            }
            //logger.info("sb:" + sb.toString());
            logger.info("callCmd execute finished");
            System.out.println("执行bat日志" + sb.toString());
            boolean success = (sb.toString().indexOf(checkString) != -1);
            if (!success) {
                throw new RuntimeException("exec cmd " + bat + "执行失败！");
            }
        } catch (IOException e) {
            throw new RuntimeException("error");
        }
    }

    private static void downloadOSSFile(Logger logger, String localPath, String ossPath) {

        File file = new File(localPath);
        if (file.exists()) {
            file.delete();
            logger.info("删除" + localPath);
        }

        InputStream res3InputStream = OssUtil.getObjectContent(ossPath);
        try {
            InputStream2File.handle(res3InputStream, localPath);
            logger.info("下载" + OSS_BASE_PATH + ossPath + "到" + localPath + "成功");
        } catch (IOException e) {
            logger.info("下载" + OSS_BASE_PATH + ossPath + "到" + localPath + "失败");
            throw new RuntimeException("error");
        }
    }


}
