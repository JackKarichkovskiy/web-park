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
@Stored(name = "instruction_steps")
public class InstructionStep {
    
    @Primary
    @Stored(name = "id", converter = Converters.UUIDConverter)
    private UUID id;
    
    @Stored(name = "plant_id", converter = Converters.UUIDConverter)
    private UUID plantId;
    
    @Stored(name = "instruction_id", converter = Converters.UUIDConverter)
    private UUID instructionId;
    
    @Stored(name = "task")
    private String task;
    
    @Stored(name = "report")
    private String report;
    
    @Stored(name = "status")
    private Status status;

    public InstructionStep() {
    }

    public InstructionStep(UUID id, UUID plantId, UUID instructionId, String task, String report, Status status) {
        this.id = id;
        this.plantId = plantId;
        this.instructionId = instructionId;
        this.task = task;
        this.report = report;
        this.status = status;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlantId() {
        return plantId;
    }

    public void setPlantId(UUID plantId) {
        this.plantId = plantId;
    }

    public UUID getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(UUID instructionId) {
        this.instructionId = instructionId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public enum Status {
        NEW, IN_PROGRESS, DONE, DONE_VERIFIED;
    }    
}
