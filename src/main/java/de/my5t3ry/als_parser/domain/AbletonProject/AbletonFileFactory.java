package de.my5t3ry.als_parser.domain.AbletonProject;

import de.my5t3ry.als_parser.domain.AbletonProject.device.Device;
import de.my5t3ry.als_parser.domain.AbletonProject.device.DeviceManufacturer;
import de.my5t3ry.als_parser.utils.device_name_extractor.ExternalDeviceNameExtractor;
import de.my5t3ry.als_parser.utils.device_name_extractor.IExtractDeviceNames;
import de.my5t3ry.als_parser.utils.device_name_extractor.InternalDeviceNameExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by: sascha.bast
 * since: 29.08.17
 */
public class AbletonFileFactory {

    Logger logger = LoggerFactory.getLogger(AbletonFileFactory.class);


    private final XPath xPath = XPathFactory.newInstance().newXPath();
    private final String INTERNAL_DEVICES_PATH = ".//LiveSet//Tracks//DeviceChain//Devices[1]/*";
    private final String EXTERNAL_VST = ".//LiveSet//Tracks//DeviceChain//Devices//PluginDevice//PluginDesc//VstPluginInfo//PlugName";
    private final String EXTERNAL_AU = ".//LiveSet//Tracks//DeviceChain//Devices//AuPluginDevice//PluginDesc//AuPluginInfo/Name";
    private final String MANUFACTURER = ".//LiveSet//Tracks//DeviceChain//Devices//AuPluginDevice//PluginDesc//AuPluginInfo/Manufacturer";
    private final String GROUP_TRACKS = "count(.//LiveSet//Tracks//GroupTrack)";
    private final String MIDI_TRACKS = "count(.//LiveSet//Tracks//MidiTrack)";
    private final String AUDIO_TRACKS = "count(.//LiveSet//Tracks//AudioTrack)";

    public final AbletonProject build(final File decompressedFile,final File file) {
        final AbletonProject result = new AbletonProject();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(decompressedFile);
            result.name = decompressedFile.getName();
            result.internalDevices = getDevices(doc, INTERNAL_DEVICES_PATH, new InternalDeviceNameExtractor());
            final List<Device> externalDevice = getDevices(doc, EXTERNAL_VST, new ExternalDeviceNameExtractor());
            externalDevice.addAll(getDevices(doc, EXTERNAL_AU, new ExternalDeviceNameExtractor()));
            result.externalDevices = externalDevice;
            result.manufacturers = getManufacturers(doc);
            result.groupTracksCount = getTrackCount(doc, GROUP_TRACKS);
            result.midiTracksCount = getTrackCount(doc, MIDI_TRACKS);
            result.audioTracksCount = getTrackCount(doc, AUDIO_TRACKS);
            result.creationFileTime = getCreationTimeStamp(file);
        } catch (IOException e) {
            return printErrorLog(e, decompressedFile);
        } catch (ParserConfigurationException e) {
            return printErrorLog(e, decompressedFile);
        } catch (SAXException e) {
            return printErrorLog(e, decompressedFile);
        }
        return result;
    }

    private FileTime getCreationTimeStamp(final File doc) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(doc.toPath(), BasicFileAttributes.class);
        return attr.creationTime();

    }

    private Integer getTrackCount(final Document doc, final String path) {
        try {
            return ((Double) xPath.compile(path).evaluate(doc, XPathConstants.NUMBER)).intValue();
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Could not read Ableton File", e);
        }
    }

    private List<DeviceManufacturer> getManufacturers(final Document doc) {
        final Set<DeviceManufacturer> result = new HashSet<>();
        try {
            final NodeList nodeList = (NodeList) xPath.compile(MANUFACTURER).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                final String manufacturerName = nodeList.item(i).getAttributes().item(0).getNodeValue();
                result.add(new DeviceManufacturer(manufacturerName));
            }
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Could not read Ableton File", e);
        }
        return new ArrayList<>(result);
    }

    public List<Device> getDevices(final Document doc, final String path, final IExtractDeviceNames deviceNameExtractor) {
        final List<Device> result = new ArrayList();
        try {
            final NodeList nodeList = (NodeList) xPath.compile(path).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                final String deviceName = deviceNameExtractor.extractName(nodeList.item(i));
                final Device device = new Device(deviceName);
                if (result.contains(device)) {
                    result.get(result.indexOf(device)).addDevice();
                } else {
                    result.add(device);
                }
            }
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Could not read Ableton File", e);
        }
        return result;
    }


    private AbletonProject printErrorLog(final Exception e, final File file) {
        logger.debug("Could not read file, maybe deprecated Ableton version:'" + file.getAbsolutePath() + "'  ");
        logger.debug(e.getMessage());
        return new DeprecatedAbletonProject();
    }
}
