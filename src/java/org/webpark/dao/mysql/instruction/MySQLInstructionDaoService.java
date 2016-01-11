/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql.instruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import org.webpark.dao.DaoConnection;
import org.webpark.dao.InstructionDaoServiceInterface;
import static org.webpark.dao.annotation.utils.DAOUtils.convertFieldToString;
import org.webpark.dao.annotation.utils.converters.Converters;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.MySQLDaoConfiguration;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLInstructionDaoService implements InstructionDaoServiceInterface {

    private final static MySQLDaoConfiguration DAO_CONF = MySQLDaoConfiguration.getInstance();

    private final static DaoConnection CONN_HOLDER = DaoConnection.getInstance();

    private MySQLInstructionDaoService() {
    }

    public static MySQLInstructionDaoService getInstance() {
        return MySQLInstructionDaoServiceHolder.INSTANCE;
    }

    @Override
    public List<Instruction> getAllNotDoneInstructionsByOwner(String id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addNewInstruction(Instruction instruction, InstructionStep[] steps) throws DAOException {
        checkNotNull(instruction);
        checkNotNull(steps);

        String addInstruction = DAO_CONF.getProperty(Queries.ADD_INSTRUCTION);
        String addStep = DAO_CONF.getProperty(Queries.ADD_STEP);
        if (addInstruction == null || addStep == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        Logger.getLogger(MySQLInstructionDaoService.class).info(addInstruction);

        //Executing the query
        Connection connection = null;
        try {
            connection = CONN_HOLDER.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try {
            PreparedStatement addInstructionStmt = connection.prepareStatement(addInstruction);
            addInstructionStmt.setString(1, instruction.getId().toString());
            addInstructionStmt.setString(2, instruction.getTitle());
            addInstructionStmt.setString(3, instruction.getCreatedBy().toString());
            addInstructionStmt.setString(4, instruction.getPerformedBy().toString());
            addInstructionStmt.setString(5, instruction.getStatus().toString());
            addInstructionStmt.execute();

            PreparedStatement addStepStmt = connection.prepareStatement(addStep);
            Logger.getLogger(MySQLInstructionDaoService.class).info(addStep);
            for (InstructionStep step : steps) {
                addStepStmt.setString(1, step.getId().toString());
                addStepStmt.setString(2, step.getPlantId().toString());
                addStepStmt.setString(3, step.getInstructionId().toString());
                addStepStmt.setString(4, step.getTask());
                addStepStmt.setString(5, step.getReport());
                addStepStmt.setString(6, step.getStatus().toString());
                addStepStmt.execute();
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new DAOException(ex1);
            }
            throw new DAOException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        }
    }

    private interface Queries {

        String ADD_INSTRUCTION = "instruction.add_instruction";

        String ADD_STEP = "instruction.add_step";
    }

    private static class MySQLInstructionDaoServiceHolder {

        private static final MySQLInstructionDaoService INSTANCE = new MySQLInstructionDaoService();
    }
}
