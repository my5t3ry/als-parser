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
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    final String name;

    Integer count;

    public Device(final String name) {
        this.name = name;
        this.count = 1;
    }

    public void  addDevice(){
        count++;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Device device = (Device) o;

        return name != null ? name.equals(device.name) : device.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
