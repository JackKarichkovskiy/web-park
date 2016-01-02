/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.entities;

import java.util.UUID;
import org.webpark.dao.annotation.Primary;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.converters.IntConverter;
import org.webpark.dao.annotation.utils.converters.UUIDConverter;

/**
 *
 * @author Karichkovskiy Yevhen
 */
@Stored(name = "plants")
public class Plant {

    @Primary
    @Stored(name = "id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "name")
    private String name;

    @Stored(name = "color")
    private String color;

    @Stored(name = "origin")
    private String origin;

    @Stored(name = "sector", converter = IntConverter.class)
    private int sector;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Plant{" + "id=" + id + ", name=" + name + ", color=" + color + ", origin=" + origin + ", sector=" + sector + '}';
    }
    
}
