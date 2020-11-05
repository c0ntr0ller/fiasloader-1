package ru.progmatik.main.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.fias.AddrObj;
import ru.fias.FiasObject;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 *  компонент предназначен для занесения объектов типа AddrObj в базу данных
 */
@Component
public class DAOBatchInsert {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${batchsize:1000}")
    private int BATCH_SIZE;



    public void insertFiasObjArray(List<FiasObject> fiasList, Connection connection) throws IllegalAccessException, InstantiationException, ClassNotFoundException{
        int count = 0;

        try(Statement statement = connection.createStatement()){

            connection.setAutoCommit(false);

            for (FiasObject fiasObject : fiasList) {
                statement.addBatch(fiasObject.getInsertStatement());
                count++;

                if (count%BATCH_SIZE == 0) {
                    statement.executeBatch();
                    connection.commit();
                    count = 0;
                }
            }

            if(count > 0){
                statement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            logger.error("Error inserting Object", e);
            e.printStackTrace();
        }
    }
}

