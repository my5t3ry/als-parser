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
public class Device implements Comparable<Device> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    private final String name;
    private Integer count;

    public Device(final String name) {
        this.name = name;
        this.count = 1;
    }

    public void  addDevice(){
        count++;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
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

    @Override
    public int compareTo(final Device o) {
        return this.count.compareTo(o.count);
    }
}
