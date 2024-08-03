package com.asc.politicalscorecard.services;

import com.asc.politicalscorecard.databases.daos.NationDAO;
import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.objects.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NationService {

    private final NationDAO nationDAO;

    @Autowired
    public NationService(NationDAO nationDAO) {
        this.nationDAO = nationDAO;
    }

    public boolean createNation(NationDTO nationDTO) {
        return nationDAO.create(nationDTO);
    }

    public NationDTO getNationById(String id) {
        NationDTO nation = nationDAO.read(id);
        return nation != null ? new NationDTO(nation.getId(), nation.getNationName(), nation.getHomePlanet()) : null;
    }

    public List<NationDTO> getAllNations() {
        List<NationDTO> nations = nationDAO.readAll();
        return nations.stream()
                .map(nation -> new NationDTO(nation.getId(), nation.getNationName(), nation.getHomePlanet()))
                .collect(Collectors.toList());
    }

    public boolean updateNation(NationDTO nationDTO) {
        System.out.println("In update Nation");
        return nationDAO.update(nationDTO);
    }

    public boolean deleteNation(String id) {
        return nationDAO.delete(id);
    }
}
