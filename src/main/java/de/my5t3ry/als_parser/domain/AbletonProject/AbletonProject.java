package de.my5t3ry.als_parser.domain.AbletonProject;

import de.my5t3ry.als_parser.domain.AbletonProject.device.Device;
import de.my5t3ry.als_parser.domain.AbletonProject.device.DeviceManufacturer;

import javax.persistence.*;
import java.util.List;

/**
 * created by: sascha.bast
 * since: 29.08.17
 */
@Entity
public class AbletonProject {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    protected List<Device> internalDevices;

    @ManyToMany(cascade = CascadeType.PERSIST)
    protected List<Device> externalDevices;

    @ManyToMany(cascade = CascadeType.PERSIST)
    protected List<DeviceManufacturer> manufacturers;

    protected Integer groupTracksCount;
    protected Integer midiTracksCount;
    protected Integer audioTracksCount;

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
}
