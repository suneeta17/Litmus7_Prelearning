package com.litmus7.inventoryfeedphase3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.litmus7.inventoryfeedphase3.constants.SQLConstants;
import com.litmus7.inventoryfeedphase3.dto.Product;
import com.litmus7.inventoryfeedphase3.exception.InventoryDaoException;
import com.litmus7.inventoryfeedphase3.util.DBConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryFeedDao {

    private static final Logger logger = LogManager.getLogger(InventoryFeedDao.class);

    public boolean addInventoryFeeds(List<Product> products) throws InventoryDaoException {
        logger.debug("Entering addInventoryFeeds with {} products", products.size());

        try (Connection connection = DBConnection.getConnection()) {
            logger.info("Database connection established successfully");
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.INSERT_PRODUCT)) {
                for (Product product : products) {
                    pstmt.setInt(1, product.getSku());
                    pstmt.setString(2, product.getProductName());
                    pstmt.setInt(3, product.getQuantity());
                    pstmt.setDouble(4, product.getPrice());
                    pstmt.addBatch();
                    logger.debug("Prepared insert for Product [SKU={}, Name={}, Qty={}, Price={}]", 
                                 product.getSku(), product.getProductName(), 
                                 product.getQuantity(), product.getPrice());
                }

                int[] batchResult = pstmt.executeBatch();
                connection.commit();
                logger.info("Batch executed successfully. Inserted {} records into DB.", batchResult.length);

            } catch (SQLException e) {
                logger.error("Error while inserting products into DB. Performing rollback.", e);
                connection.rollback();
                throw new InventoryDaoException("DB error: Failed to insert record", e);
            } finally {
                connection.setAutoCommit(true);
                logger.debug("Auto-commit reset to true");
            }
        } catch (SQLException e) {
            logger.error("Failed to connect to database", e);
            throw new InventoryDaoException("DB error: Failed to connect to DB", e);
        }

        logger.debug("Exiting addInventoryFeeds method");
        return true;
    }
}
