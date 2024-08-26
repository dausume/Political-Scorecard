package com.asc.politicalscorecard.databases.daos;

import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.objects.Planet;
import com.asc.politicalscorecard.json.dtos.PlanetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class NationDAO extends AbstractDAO<NationDTO> {

    private static final Logger logger = Logger.getLogger(NationDAO.class.getName());
    private final JdbcClient jdbcClient;
    private final PlanetDAO planetDAO;

    @Autowired
    public NationDAO(@Qualifier("contextJdbcClient") JdbcClient jdbcClient, PlanetDAO planetDAO) {
        this.jdbcClient = jdbcClient;
        this.planetDAO = planetDAO;
    }

    @Override
    public boolean create(NationDTO dto) {
        String query = "INSERT INTO nation (id, nation_name, home_planet) VALUES (?, ?, ?)";
        try {
            jdbcClient.sql(query)
                      .params(List.of(dto.getId(), dto.getNationName(), dto.getHomePlanetId()))
                      .update();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating nation: ", e);
            return false;
        }
    }

    @Override
    public NationDTO read(String id) {
        String query = "SELECT * FROM nation WHERE id = ?";
        try {
            List<NationDTO> dtoList = jdbcClient.sql(query)
                                                .params(List.of(id))
                                                .query(NationDTO.class)
                                                .list();
            if (dtoList.size() == 1) {
                return dtoList.get(0);
            } else {
                logger.log(Level.SEVERE, "Error: Query returned multiple Nations or none, should only be returning one.");
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading nation: ", e);
            return null;
        }
    }

    @Override
    public List<NationDTO> readAll() {
        String query = "SELECT * FROM nation";
        try {
            return jdbcClient.sql(query)
                             .query(NationDTO.class)
                             .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading all nations: ", e);
            return null;
        }
    }

    @Override
    public boolean update(NationDTO dto) {
        String query = "UPDATE nation SET nation_name = ?, home_planet = ? WHERE id = ?";
        try {
            int rowsAffected = jdbcClient.sql(query)
                                        .params(List.of(dto.getNationName(), dto.getHomePlanetId(), dto.getId()))
                                        .update();
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating nation: ", e);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM nation WHERE id = ?";
        try {
            jdbcClient.sql(query)
                      .params(List.of(id))
                      .update();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting nation: ", e);
            return false;
        }
    }

    private static class NationRowMapper implements RowMapper<NationDTO> {
        private final PlanetDAO planetDAO;

        public NationRowMapper(PlanetDAO planetDAO) {
            this.planetDAO = planetDAO;
        }

        @Override
        public NationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            String id = rs.getString("id");
            String nationName = rs.getString("nation_name");
            String homePlanetId = rs.getString("home_planet");
            Planet homePlanet = null;

            if (homePlanetId != null) {
                PlanetDTO homePlanetDTO = planetDAO.read(homePlanetId);
                if (homePlanetDTO != null) {
                    homePlanet = homePlanetDTO.toEntity();
                } else {
                    logger.log(Level.WARNING, "No Planet found with ID {0}", homePlanetId);
                }
            }

            return new NationDTO(id, nationName, homePlanet);
        }
    }
}
