package de.my5t3ry.als_parser.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * created by: sascha.bast
 * since: 29.08.17
 */
public class GZipFile {
    private final File input;


    public GZipFile(final File input) {
        this.input = input;
    }

    public void decompress(final File output) throws IOException {
        byte[] buffer = new byte[1024];
            GZIPInputStream gzip =
                    new GZIPInputStream(new FileInputStream(input));
            FileOutputStream out =
                    new FileOutputStream(output);
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            gzip.close();
            out.close();

    }
}
