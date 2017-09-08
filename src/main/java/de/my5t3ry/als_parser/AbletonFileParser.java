package de.my5t3ry.als_parser;

import de.my5t3ry.als_parser.domain.AbletonProject.AbletonFileFactory;
import de.my5t3ry.als_parser.domain.AbletonProject.AbletonProject;
import de.my5t3ry.als_parser.utils.GZipFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
public class AbletonFileParser {

    Logger logger = LoggerFactory.getLogger(AbletonFileParser.class);

    private AbletonFileFactory abletonFileFactory = new AbletonFileFactory();

    public List<AbletonProject> parseDirectory(final File directory) {
        final List<AbletonProject> result = new ArrayList<>();
        final String[] validExtension = {"als"};
        FileUtils.listFiles(directory, validExtension, true).forEach(curFile -> result.add(parse((File) curFile)));
        return result
                .stream()
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    public AbletonProject parse(final File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File is directory, pleas use parseDirectoryInstead()");
        }
        final GZipFile gZipFile = new GZipFile(file);
        try {
            gZipFile.decompress(new File(System.getProperty("java.io.tmpdir") + file.getName()));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + file.getName())));
            return abletonFileFactory.build(document, file.getName());
        } catch (IOException e) {
            return printErrorLog(e, file);
        } catch (ParserConfigurationException e) {
            return printErrorLog(e, file);
        } catch (SAXException e) {
            return printErrorLog(e, file);
        }
    }

    private AbletonProject printErrorLog(final Exception e, final File file) {
        logger.debug("Could not read file, maybe deprecated Ableton version:'" + file.getAbsolutePath() + "'  ");
        logger.debug(e.getMessage());
        return null;
    }
}
