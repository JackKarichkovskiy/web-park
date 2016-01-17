/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.List;
import java.util.Map;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.exception.DAOException;

/**
 * Interface that defines methods for specific database work with instructions
 * and its steps.
 *
 * @author Karichkovskiy Yevhen
 */
public interface InstructionDaoServiceInterface {

    /**
     * Returns list of instructions that weren't confirmed by owner.
     *
     * @param id - owner id
     * @return list of instructions
     * @throws DAOException - if some database problems
     */
    List<Instruction> getAllNotConfirmedInstructionsByOwner(String id) throws DAOException;

    /**
     * Returns list of instructions not complete by forester.
     *
     * @param id - forester id
     * @return list of instructions
     * @throws DAOException - if some database problems
     */
    List<Instruction> getAllNotDoneInstructionsByForester(String id) throws DAOException;

    /**
     * Returns map in which the key(name of col in ResultSet) is related to list
     * of corresponding row values.
     *
     * @param id - instruction id
     * @return map with steps and corresponding name of plant
     * @throws DAOException - if some database problems
     */
    Map<String, List<Object>> getAllStepsInInstruction(String id) throws DAOException;

    /**
     * Adds new instruction to database.
     *
     * @param instruction - new instruction
     * @param steps - corresponding steps
     * @throws DAOException - if some database problems
     */
    void addNewInstruction(Instruction instruction, InstructionStep[] steps) throws DAOException;

    /**
     * Update instruction and its steps statuses by forester.
     *
     * @param instruction - instruction
     * @param steps - instruction steps
     * @throws DAOException - if some database problems
     */
    void updateInstructionStatuses(Instruction instruction, InstructionStep[] steps) throws DAOException;

    /**
     * Update not confirmed instruction and its steps statuses by owner.
     *
     * @param instruction - instruction
     * @param steps - instruction steps
     * @throws DAOException - if some database problems
     */
    void updateNotConfirmedInstructionStatuses(Instruction instruction, InstructionStep[] steps) throws DAOException;
}
