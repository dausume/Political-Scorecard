package com.asc.politicalscorecard.databases.daos;

import com.asc.politicalscorecard.json.dtos.PlanetDTO;
import com.asc.politicalscorecard.objects.Planet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.simple.JdbcClient;

import org.springframework.stereotype.Repository;

@Repository
public class PlanetDAO extends AbstractDAO<PlanetDTO> {
    private static final Logger logger = Logger.getLogger(PlanetDAO.class.getName());

    private JdbcClient jdbcClient;

    @Autowired
    public PlanetDAO(@Qualifier("locationJdbcClient") JdbcClient jdbcClient) 
    {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public boolean create(PlanetDTO dto) {
        System.out.println("In create Planet in PlanetDAO");
        String query = "INSERT INTO planet (id, planet_name) VALUES (?, ?)";
        try {
            jdbcClient.sql(query)
                      .params(List.of(dto.getId(), dto.getPlanetName()))
                      .update();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating planet: ", e);
            return false;
        }
    }

    @Override
    public PlanetDTO read(String id) {
        System.out.println("Reading Planet object with id : " + id);
        String query = "SELECT * FROM planet WHERE id = ?";
        try {
            // reimplement using jdbcClient
            PlanetDTO dto = jdbcClient.sql(query)
                                     .params(List.of(id))
                                     .query(PlanetDTO.class)
                                     .single();
            System.out.println("Got DTO List");
            System.out.println(dto);
            logger.log(Level.SEVERE, "Error: Query returned multiple Planets, should only be returning one.");
            return null;
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error reading planet: ", e);
            return null;
        }
    }

    @Override
    public List<PlanetDTO> readAll() {
        String query = "SELECT * FROM planet";
        try {
            //List<PlanetDTO> allPlanets = jdbcTemplate.query(query, new PlanetRowMapper());
            List<PlanetDTO> allPlanets = jdbcClient.sql(query)
                                                   .query(PlanetDTO.class)
                                                   .list();
            return allPlanets;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading all planets: ", e);
            return null;
        }
    }

    @Override
    public boolean update(PlanetDTO dto) {
        String query = "UPDATE planet SET planet_name = ? WHERE id = ?";
        try {
            //jdbcTemplate.update(query, dto.getPlanetName(), dto.getId());
            jdbcClient.sql(query)
                      .params(List.of(dto.getPlanetName(), dto.getId()))
                      .update();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating planet: ", e);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM planet WHERE id = ?";
        try {
            //jdbcTemplate.update(query, id);
            jdbcClient.sql(query)
                      .params(List.of(id))
                      .update();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting planet: ", e);
            return false;
        }
    }
}
