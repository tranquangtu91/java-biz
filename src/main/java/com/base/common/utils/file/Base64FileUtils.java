package com.base.common.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.base.common.dto.general_response.GeneralResponse;
import com.base.common.utils.convert.ArrayUtils;

public class Base64FileUtils {
    static public final Map<String, String> mimeType = new HashMap<String, String>() {
        {
            put("aac", "audio/aac");
            put("abw", "application/x-abiword");
            put("ai", "application/postscript");
            put("arc", "application/octet-stream");
            put("avi", "video/x-msvideo");
            put("azw", "application/vnd.amazon.ebook");
            put("bin", "application/octet-stream");
            put("bz", "application/x-bzip");
            put("bz2", "application/x-bzip2");
            put("csh", "application/x-csh");
            put("css", "text/css");
            put("csv", "text/csv");
            put("doc", "application/msword");
            put("dll", "application/octet-stream");
            put("eot", "application/vnd.ms-fontobject");
            put("epub", "application/epub+zip");
            put("gif", "image/gif");
            put("htm", "text/html");
            put("html", "text/html");
            put("ico", "image/x-icon");
            put("ics", "text/calendar");
            put("jar", "application/java-archive");
            put("jpeg", "image/jpeg");
            put("jpg", "image/jpeg");
            put("js", "application/javascript");
            put("json", "application/json");
            put("mid", "audio/midi");
            put("midi", "audio/midi");
            put("mp2", "audio/mpeg");
            put("mp3", "audio/mpeg");
            put("mp4", "video/mp4");
            put("mpa", "video/mpeg");
            put("mpe", "video/mpeg");
            put("mpeg", "video/mpeg");
            put("mpkg", "application/vnd.apple.installer+xml");
            put("odp", "application/vnd.oasis.opendocument.presentation");
            put("ods", "application/vnd.oasis.opendocument.spreadsheet");
            put("odt", "application/vnd.oasis.opendocument.text");
            put("oga", "audio/ogg");
            put("ogv", "video/ogg");
            put("ogx", "application/ogg");
            put("otf", "font/otf");
            put("png", "image/png");
            put("pdf", "application/pdf");
            put("ppt", "application/vnd.ms-powerpoint");
            put("rar", "application/x-rar-compressed");
            put("rtf", "application/rtf");
            put("sh", "application/x-sh");
            put("svg", "image/svg+xml");
            put("swf", "application/x-shockwave-flash");
            put("tar", "application/x-tar");
            put("tif", "image/tiff");
            put("tiff", "image/tiff");
            put("ts", "application/typescript");
            put("ttf", "font/ttf");
            put("txt", "text/plain");
            put("vsd", "application/vnd.visio");
            put("wav", "audio/x-wav");
            put("weba", "audio/webm");
            put("webm", "video/webm");
            put("webp", "image/webp");
            put("woff", "font/woff");
            put("woff2", "font/woff2");
            put("xhtml", "application/xhtml+xml");
            put("xls", "application/vnd.ms-excel");
            put("xlsx", "application/vnd.ms-excel");
            put("xlsx_OLD", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put("xml", "application/xml");
            put("xul", "application/vnd.mozilla.xul+xml");
            put("zip", "application/zip");
            put("3gp", "video/3gpp");
            put("3gp_DOES_NOT_CONTAIN_VIDEO", "audio/3gpp");
            put("3gp2", "video/3gpp2");
            put("3gp2_DOES_NOT_CONTAIN_VIDEO", "audio/3gpp2");
            put("7z", "application/x-7z-compressed");
        }
    };

    public static GeneralResponse toBase64(String filePath, ToBase64Options params) {
        String[] __params = filePath.split("[\\\\\\/]");
        String __fileName = ArrayUtils.getLatestObject(__params);
        String __fileExt = (params == null || params.fileExt == null) ? ArrayUtils.getLatestObject(__fileName.split("\\.")) : params.fileExt;

        Map<String, Object> responseValue = new HashMap<String, Object>() {
            {
                put("fileName", __fileName);
                put("fileData", getBase64String(filePath));
                put("fileExt", __fileExt);
                put("mimeType", Base64FileUtils.mimeType.get(__fileExt));
            }
        };
        GeneralResponse generalResponse = new GeneralResponse() {
            {
                value = responseValue;
            }
        };

        return generalResponse;
    }

    public static GeneralResponse toBase64(String filePath) {
        return toBase64(filePath, null);
    }

    /**
     * Load file & tra noi dung dang Base64 String
     * @param filePath
     * @return
     */
    static String getBase64String(String filePath) { 
        try {
            File file = new File(filePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class ToBase64Options {
        String fileExt;
    }
}
