package eu.panic.gameminerservice.template.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Base64;

public class GZipTextCompressionUtil {
    public static String compressText(String text) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(text.getBytes());
            gzip.close();
            byte[] compressedData = bos.toByteArray();
            return Base64.getEncoder().encodeToString(compressedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decompressText(String compressedText) {
        try {
            byte[] compressedData = Base64.getDecoder().decode(compressedText);
            ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            gzip.close();
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
