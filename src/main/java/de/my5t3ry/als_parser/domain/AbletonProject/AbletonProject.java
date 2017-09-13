package de.my5t3ry.als_parser.domain.AbletonProject;

import de.my5t3ry.als_parser.domain.AbletonProject.device.Device;
import de.my5t3ry.als_parser.domain.AbletonProject.device.DeviceManufacturer;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by: sascha.bast
 * since: 29.08.17
 */
@Entity
public class AbletonProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id;
    String name;
    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Device> internalDevices;
    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Device> externalDevices;
    @ManyToMany(cascade = CascadeType.PERSIST)
    List<DeviceManufacturer> manufacturers;
    Integer groupTracksCount;
    Integer midiTracksCount;
    Integer audioTracksCount;
    String creationFileTime;



    public Integer getTotalTracks() {
        return groupTracksCount + midiTracksCount + audioTracksCount;
    }

    public List<Device> getInternalDevices() {
        return internalDevices;
    }

    public List<Device> getExternalDevices() {
        return externalDevices;
    }

    public List<DeviceManufacturer> getManufacturers() {
        return manufacturers;
    }

    public Integer getGroupTracksCount() {
        return groupTracksCount;
    }

    public Integer getMidiTracksCount() {
        return midiTracksCount;
    }

    public Integer getAudioTracksCount() {
        return audioTracksCount;
    }

    public Integer getTotalDeviceCount() {
        return internalDevices.stream().collect(Collectors.summingInt(d -> d.getCount())) + externalDevices.stream().collect(Collectors.summingInt(d -> d.getCount()));
    }



    public String getCreationFileTime() {
        return creationFileTime;
    }
}
