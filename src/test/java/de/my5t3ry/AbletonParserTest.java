package de.my5t3ry;

import de.my5t3ry.als_parser.AbletonFileParser;
import de.my5t3ry.als_parser.domain.abletonproject.AbletonProject;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
public class AbletonParserTest {

    final AbletonFileParser cut = new AbletonFileParser();

    @Test
    public void parseFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("20200126.als").getFile());
        final AbletonProject abletonProject = cut.parse(file);
        assertEquals(abletonProject.getTotalTracks(), new Integer(25));
        assertEquals(abletonProject.getInternalDevices().size(), 13);
        assertEquals(abletonProject.getExternalDevices().size(), 8);
        assertEquals(abletonProject.getManufacturers().size(), 4);
        assertEquals(abletonProject.getMidiTracksCount(), new Integer(22));
        assertEquals(abletonProject.getAudioTracksCount(), new Integer(0));
    }

    @Test
    public void parseDirectory() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testDir").getFile());
        final List<AbletonProject> result = cut.parseDirectory(file);
        assertEquals(result.size(), 2);
    }
}
