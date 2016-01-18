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
 * Entity that represents instruction to forester.
 *
 * @author Karichkovskiy Yevhen
 */
@Stored(name = "instructions")
public class Instruction {

    /**
     * ID of instruction.
     */
    @Primary
    @Stored(name = "id", converter = Converters.UUIDConverter)
    private UUID id;

    /**
     * Title of instruction.
     */
    @Stored(name = "title")
    private String title;

    /**
     * ID of user that created the instruction.
     */
    @Stored(name = "created_by", converter = Converters.UUIDConverter)
    private UUID createdBy;

    /**
     * ID of user that execute the instruction.
     */
    @Stored(name = "performed_by", converter = Converters.UUIDConverter)
    private UUID performedBy;

    /**
     * Status of instruction.
     */
    @Stored(name = "status", converter = Converters.InstructionStatusConverter)
    private Status status;

    public Instruction() {
    }

    public Instruction(UUID id, String title, UUID createdBy, UUID performedBy, Status status) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.performedBy = performedBy;
        this.status = status;
    }

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

    /**
     * Enumeration that stores possible statuses of instruction.
     */
    public enum Status {

        NEW("instr.status.new"),
        IN_PROGRESS("instr.status.in_progress"),
        DONE("instr.status.done"),
        DONE_VERIFIED("instr.status.done_verified");

        private final String localeKey;

        private Status(String localeKey) {
            this.localeKey = localeKey;
        }

        public String getLocaleKey() {
            return localeKey;
        }
    }
}
