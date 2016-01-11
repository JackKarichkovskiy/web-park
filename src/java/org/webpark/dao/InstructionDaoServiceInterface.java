/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.List;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public interface InstructionDaoServiceInterface {
    
    List<Instruction> getAllNotDoneInstructionsByOwner(String id) throws DAOException;
    
    void addNewInstruction(Instruction instruction, InstructionStep[] steps) throws DAOException;
}
