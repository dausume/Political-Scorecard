package com.asc.politicalscorecard.databases.daos;

import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.json.dtos.PlanetDTO;
import com.asc.politicalscorecard.objects.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;
    private PlanetDAO planetDAO;

    @Autowired
    public NationDAO(JdbcTemplate jdbcTemplate, PlanetDAO planetDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.planetDAO = planetDAO;
    }

    @Override
    public boolean create(NationDTO dto) {
        System.out.println("In create Nation in NationDAO");
        String query = "INSERT INTO nation (id, nation_name, home_planet) VALUES (?, ?, ?)";
        System.out.println("Home Planet Id: " + dto.getHomePlanetId());
        try {
            jdbcTemplate.update(query, dto.getId(), dto.getNationName(), dto.getHomePlanetId());
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
            List<NationDTO> dtoList = jdbcTemplate.query(query, new NationRowMapper(planetDAO), id);
            
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
        System.out.println("In readAll for Nation in DAO");
        String query = "SELECT * FROM nation";
        try {
            return jdbcTemplate.query(query, new NationRowMapper(planetDAO));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading all nations: ", e);
            return null;
        }
    }

    @Override
    public boolean update(NationDTO dto) {
        System.out.println("In DAO for update, showing dto.");
        System.out.println(dto);
        String query = "UPDATE nation SET nation_name = ?, home_planet = ? WHERE id = ?";
        System.out.println("Home Planet Id: " + dto.getHomePlanetId());
        try {
            int rowsAffected = jdbcTemplate.update(query, dto.getNationName(), dto.getHomePlanetId(), dto.getId());
            if(rowsAffected > 0)
            {
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating nation: ", e);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM nation WHERE id = ?";
        try {
            jdbcTemplate.update(query, id);
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
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println("MetaData");
            System.out.println(metaData);
            int totalColumns = metaData.getColumnCount();
            System.out.println("Row:");
            String columnNameHolder = null;
            Object columnValueHolder = null;
            for (int i = 1; i <= totalColumns; i++) {
                columnNameHolder = metaData.getColumnName(i);
                columnValueHolder = rs.getObject(i);
                System.out.println(columnNameHolder + ": " + columnValueHolder);
            }
            System.out.println("Inside NationDAO mapRow function.");
            String id = rs.getString("id");
            String nationName = rs.getString("nation_name");
            System.out.println("nation_name: " + nationName);
            System.out.println("Trying to get home_planet in string format");
            String homePlanetId = rs.getString("home_planet");
            System.out.println("homePlanetId: " + homePlanetId);
            System.out.println("Assigned all fields, showing result set. ");
            System.out.println(rs);
            Planet homePlanet = null;
            PlanetDTO homePlanetDTO = null;
            if(homePlanetId != null)
            {
                System.out.println("Homeplanet id defined attempting read via planetDAO. ");
                try{
                    homePlanetDTO = planetDAO.read(homePlanetId);
                }
                catch(Exception exception)
                {
                    System.out.println("Error: No Planet found with ID (Nation's Homeworld Id): " + exception);
                }
                if (homePlanetDTO != null) {
                    homePlanet = homePlanetDTO.toEntity();
                } else {
                    System.out.println("Error: No Planet found with ID " + homePlanetId);
                }
            }
            else
            {
                System.out.println("Home Planet Id was null");
            }
            return new NationDTO(id, nationName, homePlanet);
        }
    }
}
