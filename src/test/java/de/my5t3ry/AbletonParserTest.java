package de.my5t3ry;

import de.my5t3ry.als_parser.AbletonFileParser;
import de.my5t3ry.als_parser.domain.AbletonProject.AbletonProject;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AbletonParserTest {

    final AbletonFileParser cut = new AbletonFileParser();

    @Test
    public void shouldWork() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("20170428.als").getFile());
        final AbletonProject abletonProject = cut.parse(file);
        assertEquals(abletonProject.getTotalTracks(), new Integer(25));
        assertEquals(abletonProject.getInternalDevices().size(), 13);
        assertEquals(abletonProject.getExternalDevices().size(), 8);
        assertEquals(abletonProject.getManufacturers().size(), 4);
        assertEquals(abletonProject.getMidiTracksCount(), new Integer(22));
        assertEquals(abletonProject.getAudioTracksCount(), new Integer(0));
    }
}
