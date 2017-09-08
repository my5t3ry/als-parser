package de.my5t3ry.als_parser;

import de.my5t3ry.als_parser.domain.AbletonProject.AbletonFileFactory;
import de.my5t3ry.als_parser.domain.AbletonProject.AbletonProject;
import de.my5t3ry.als_parser.utils.GZipFile;
import org.apache.commons.io.FileUtils;
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

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
public class AbletonFileParser {

    private AbletonFileFactory abletonFileFactory = new AbletonFileFactory();

    public List<AbletonProject> parseDirectory(final File directory) {
        final List<AbletonProject> result = new ArrayList<>();
        final String[] validExtension = {"als"};
        FileUtils.listFiles(directory, validExtension, true).forEach(curFile -> result.add(parse((File) curFile)));
        return result;
    }

    public AbletonProject parse(final File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File is directory, pleas use parseDirectoryInstead()");
        }
        final GZipFile gZipFile = new GZipFile(file);
        gZipFile.decompress(new File(System.getProperty("java.io.tmpdir") + file.getName()));
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + file.getName())));
            return abletonFileFactory.build(document, file.getName());
        } catch (IOException e) {
            throw new IllegalStateException("Could not read File:'" + file.getAbsolutePath() + "'", e);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Could not read File:'" + file.getAbsolutePath() + "'", e);
        } catch (SAXException e) {
            throw new IllegalStateException("Could not read File:'" + file.getAbsolutePath() + "'", e);
        }
    }
}
