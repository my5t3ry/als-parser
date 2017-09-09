package de.my5t3ry.als_parser;

import de.my5t3ry.als_parser.domain.AbletonProject.AbletonProjectFactory;
import de.my5t3ry.als_parser.domain.AbletonProject.AbletonProject;
import de.my5t3ry.als_parser.domain.AbletonProject.DeprecatedAbletonProject;
import de.my5t3ry.als_parser.utils.GZipFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
public class AbletonFileParser {

    private Logger logger = LoggerFactory.getLogger(AbletonFileParser.class);
    private AbletonProjectFactory abletonProjectFactory = new AbletonProjectFactory();

    public List<AbletonProject> parseDirectory(final File directory) {
        final List<AbletonProject> result = new ArrayList<>();
        final String[] validExtension = {"als"};
        FileUtils.listFiles(directory, validExtension, true).forEach(curFile -> result.add(parse((File) curFile)));
        return result;
    }

    public AbletonProject parse(final File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File is directory, please use parseDirectoryInstead(File file)");
        }
        final GZipFile gZipFile = new GZipFile(file);
        try {
            gZipFile.decompress(new File(System.getProperty("java.io.tmpdir") + file.getName()));
            return abletonProjectFactory.build(new File(System.getProperty("java.io.tmpdir") + file.getName()), file);
        } catch (IOException e) {
            logger.debug("Could not read file, maybe deprecated Ableton version:'" + file.getAbsolutePath() + "'  ");
            logger.debug(e.getMessage());
            return new DeprecatedAbletonProject();
        }
    }
}