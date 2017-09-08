package de.my5t3ry.als_parser.domain.AbletonProject.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
@Entity
public class DeviceManufacturer {
    private final String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public DeviceManufacturer(final String manufacturerName) {
        this.name = manufacturerName;
    }
}
