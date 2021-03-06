/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql.instruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.DaoConnection;
import org.webpark.dao.InstructionDaoServiceInterface;
import org.webpark.dao.annotation.utils.DAOUtils;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.MySQLDaoConfiguration;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 * Class that realizes InstructionDao for MySQL database.
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLInstructionDaoService implements InstructionDaoServiceInterface {

    /**
     * Config file with sql queries for mysql database.
     */
    private final static MySQLDaoConfiguration DAO_CONF = MySQLDaoConfiguration.getInstance();

    /**
     * Pool connections holder object.
     */
    private final static DaoConnection CONN_HOLDER = DaoConnection.getInstance();

    /**
     * Class of Instruction entity.
     */
    private final static Class<Instruction> INSTRUCTION_CLASS = Instruction.class;

    private MySQLInstructionDaoService() {
    }

    public static MySQLInstructionDaoService getInstance() {
        return MySQLInstructionDaoServiceHolder.INSTANCE;
    }

    @Override
    public List<Instruction> getAllNotConfirmedInstructionsByOwner(String id) throws DAOException {
        checkNotNull(id);

        String getOwnerInstructions = DAO_CONF.getProperty(Queries.GET_NOT_CONFIRMED_INSTRUCTIONS_BY_OWNER);
        if (getOwnerInstructions == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        String convertedId = DAOUtils.convertFieldToString(id);
        String getOwnerInstructionsQuery = String.format(getOwnerInstructions, convertedId);

        Logger.getLogger(MySQLInstructionDaoService.class).info(getOwnerInstructionsQuery);

        return AppDaoFactory.getInstance().getCRUDDao().select(INSTRUCTION_CLASS, getOwnerInstructionsQuery);
    }

    @Override
    public List<Instruction> getAllNotDoneInstructionsByForester(String id) throws DAOException {
        checkNotNull(id);

        String getForesterInstructions = DAO_CONF.getProperty(Queries.GET_FORESTER_INSTRUCTIONS);
        if (getForesterInstructions == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        String convertedId = DAOUtils.convertFieldToString(id);
        String getForesterInstructionsQuery = String.format(getForesterInstructions, convertedId);

        Logger.getLogger(MySQLInstructionDaoService.class).info(getForesterInstructionsQuery);

        return AppDaoFactory.getInstance().getCRUDDao().select(INSTRUCTION_CLASS, getForesterInstructionsQuery);
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
        Logger.getLogger(MySQLInstructionDaoService.class).info(addStep);

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

    @Override
    public void updateInstructionStatuses(Instruction instruction, InstructionStep[] steps) throws DAOException {
        checkNotNull(instruction);
        checkNotNull(steps);

        String updateInstruction = DAO_CONF.getProperty(Queries.UPDATE_INSTRUCTION_STATUS);
        String updateStep = DAO_CONF.getProperty(Queries.UPDATE_INSTRUCTION_STEP_STATUS);
        if (updateInstruction == null || updateStep == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        Logger.getLogger(MySQLInstructionDaoService.class).info(updateInstruction);
        Logger.getLogger(MySQLInstructionDaoService.class).info(updateStep);

        //Executing the query
        Connection connection = null;
        try {
            connection = CONN_HOLDER.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        try {
            PreparedStatement updateInstructionStmt = connection.prepareStatement(updateInstruction);
            updateInstructionStmt.setString(1, instruction.getStatus().toString());
            updateInstructionStmt.setString(2, instruction.getId().toString());
            updateInstructionStmt.executeUpdate();

            PreparedStatement updateStepStmt = connection.prepareStatement(updateStep);
            for (InstructionStep step : steps) {
                updateStepStmt.setString(1, step.getReport());
                updateStepStmt.setString(2, step.getStatus().toString());
                updateStepStmt.setString(3, step.getId().toString());
                updateStepStmt.executeUpdate();
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

    @Override
    public void updateNotConfirmedInstructionStatuses(Instruction instruction, InstructionStep[] steps) throws DAOException {
        checkNotNull(instruction);
        checkNotNull(steps);

        String updateInstruction = DAO_CONF.getProperty(Queries.UPDATE_INSTRUCTION_STATUS);
        String updateStep = DAO_CONF.getProperty(Queries.UPDATE_NOT_CONFIRMED_INSTRUCTION_STEP_STATUS);
        if (updateInstruction == null || updateStep == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        Logger.getLogger(MySQLInstructionDaoService.class).info(updateInstruction);
        Logger.getLogger(MySQLInstructionDaoService.class).info(updateStep);

        //Executing the query
        Connection connection = null;
        try {
            connection = CONN_HOLDER.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        try {
            PreparedStatement updateInstructionStmt = connection.prepareStatement(updateInstruction);
            updateInstructionStmt.setString(1, instruction.getStatus().toString());
            updateInstructionStmt.setString(2, instruction.getId().toString());
            updateInstructionStmt.executeUpdate();

            PreparedStatement updateStepStmt = connection.prepareStatement(updateStep);
            for (InstructionStep step : steps) {
                updateStepStmt.setString(1, step.getTask());
                updateStepStmt.setString(2, step.getStatus().toString());
                updateStepStmt.setString(3, step.getId().toString());
                updateStepStmt.executeUpdate();
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

    @Override
    public Map<String, List<Object>> getAllStepsInInstruction(String id) throws DAOException {
        checkNotNull(id);

        String getInstructionSteps = DAO_CONF.getProperty(Queries.GET_INSTRUCTION_STEPS);
        if (getInstructionSteps == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }

        String convertedId = DAOUtils.convertFieldToString(id);
        String getInstructionStepsQuery = String.format(getInstructionSteps, convertedId);

        Logger.getLogger(MySQLInstructionDaoService.class).info(getInstructionStepsQuery);

        Connection connection = null;
        try {
            connection = CONN_HOLDER.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        Map<String, List<Object>> resultFields = new HashMap<>();
        initGetAllStepsInInstructionResult(resultFields);

        ResultSet rs;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(getInstructionStepsQuery);
            while (rs.next()) {
                resultFields.get(GetAllStepsInInstructionResultTags.STEP_ID).add(rs.getString(GetAllStepsInInstructionResultTags.STEP_ID));
                resultFields.get(GetAllStepsInInstructionResultTags.PLANT_ID).add(rs.getString(GetAllStepsInInstructionResultTags.PLANT_ID));
                resultFields.get(GetAllStepsInInstructionResultTags.INSTRUCTION_ID).add(rs.getString(GetAllStepsInInstructionResultTags.INSTRUCTION_ID));
                resultFields.get(GetAllStepsInInstructionResultTags.STEP_TASK).add(rs.getString(GetAllStepsInInstructionResultTags.STEP_TASK));
                resultFields.get(GetAllStepsInInstructionResultTags.STEP_REPORT).add(rs.getString(GetAllStepsInInstructionResultTags.STEP_REPORT));
                resultFields.get(GetAllStepsInInstructionResultTags.STEP_STATUS).add(rs.getString(GetAllStepsInInstructionResultTags.STEP_STATUS));
                resultFields.get(GetAllStepsInInstructionResultTags.PLANT_NAME).add(rs.getString(GetAllStepsInInstructionResultTags.PLANT_NAME));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        }

        return resultFields;
    }

    /**
     * Method that created empty lists for GetAllStepsInInstructionResult
     * result.
     *
     * @param result - result where will be stored data from database answer
     */
    private void initGetAllStepsInInstructionResult(Map<String, List<Object>> result) {
        result.put(GetAllStepsInInstructionResultTags.STEP_ID, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.PLANT_ID, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.INSTRUCTION_ID, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.STEP_TASK, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.STEP_REPORT, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.STEP_STATUS, new ArrayList<>());
        result.put(GetAllStepsInInstructionResultTags.PLANT_NAME, new ArrayList<>());
    }

    /**
     * Tags - col names for result table from database
     */
    public interface GetAllStepsInInstructionResultTags {

        String STEP_ID = "id";

        String PLANT_ID = "plant_id";

        String INSTRUCTION_ID = "instruction_id";

        String STEP_TASK = "task";

        String STEP_REPORT = "report";

        String STEP_STATUS = "status";

        String PLANT_NAME = "plant_name";
    }

    /**
     * Tags for sql queries that stored in mysql dao config file.
     */
    private interface Queries {

        String ADD_INSTRUCTION = "instruction.add_instruction";

        String ADD_STEP = "instruction.add_step";

        String GET_NOT_CONFIRMED_INSTRUCTIONS_BY_OWNER = "instruction.get_not_confirmed_instructions";

        String GET_FORESTER_INSTRUCTIONS = "instruction.get_forester_instructions";

        String GET_INSTRUCTION_STEPS = "instruction.get_instruction_steps";

        String UPDATE_INSTRUCTION_STATUS = "instruction.update_instruction_status";

        String UPDATE_INSTRUCTION_STEP_STATUS = "instruction.update_instruction_step_status";

        String UPDATE_NOT_CONFIRMED_INSTRUCTION_STEP_STATUS = "instruction.update_not_confirmed_instruction_step_status";
    }

    private static class MySQLInstructionDaoServiceHolder {

        private static final MySQLInstructionDaoService INSTANCE = new MySQLInstructionDaoService();
    }
}
