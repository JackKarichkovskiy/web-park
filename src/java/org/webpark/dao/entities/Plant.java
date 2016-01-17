/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.entities;

import java.util.UUID;
import org.webpark.dao.annotation.Primary;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.converters.Converters;

/**
 * Entity that represents plant in Park.
 *
 * @author Karichkovskiy Yevhen
 */
@Stored(name = "plants")
public class Plant {

    /**
     * ID of plant.
     */
    @Primary
    @Stored(name = "id", converter = Converters.UUIDConverter)
    private UUID id;

    /**
     * Name of plant.
     */
    @Stored(name = "name")
    private String name;

    /**
     * Color of plant.
     */
    @Stored(name = "color")
    private String color;

    /**
     * Origin of plant.
     */
    @Stored(name = "origin")
    private String origin;

    /**
     * Number of sector where the plant is located.
     */
    @Stored(name = "sector", converter = Converters.IntConverter)
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
