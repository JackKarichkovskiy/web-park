/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.List;
import org.webpark.dao.entities.Instruction;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public interface InstructionDaoServiceInterface {
    
    List<Instruction> getAllNotDoneInstructionsByOwner(String id);
    
}
