package de.my5t3ry.als_parser.domain.abletonproject.device;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * created by: sascha.bast
 * since: 30.08.17
 */
@Entity
@NoArgsConstructor
public class DeviceManufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    private  String name;

    public DeviceManufacturer(final String manufacturerName) {
        this.name = manufacturerName;
    }

    public String getName() {
        return name;
    }
}