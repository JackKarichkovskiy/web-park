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
 *
 * @author Karichkovskiy Yevhen
 */
@Stored(name = "instructions")
public class Instruction {
    
    @Primary
    @Stored(name = "id", converter = Converters.UUIDConverter)
    private UUID id;
    
    @Stored(name = "title")
    private String title;
    
    @Stored(name = "created_by", converter = Converters.UUIDConverter)
    private UUID createdBy;
    
    @Stored(name = "performed_by", converter = Converters.UUIDConverter)
    private UUID performedBy;
    
    @Stored(name = "status", converter = Converters.InstructionStatusConverter)
    private Status status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UUID performedBy) {
        this.performedBy = performedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public enum Status{
        NEW, IN_PROGRESS, DONE, DONE_VERIFIED;
    }
}
