package com.bekkos.eksamen2021;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.PreparedStatement;

@RestController
public class Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private JdbcTemplate db;


    /* Mappinger */

    @PostMapping("/sendData")
    @Transactional
    public void mottaData(PakkeRegistrering pakkeRegistrering, HttpServletResponse response) throws IOException {
        /* INPUT VALIDERING */

        if(!pakkeRegistrering.getFornavn().matches("[a-zæøåA-ZÆØÅ]{0,50}")) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i fornavn.");
            LOGGER.error("Feil i fornavn");
            return;
        }

        if(!pakkeRegistrering.getEtternavn().matches("[a-zæøåA-ZÆØÅ]{0,50}")) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i etternavn.");
            LOGGER.error("Feil i etternavn");
            return;
        }

        if(!pakkeRegistrering.getPostnr().matches("^[0-9]{4}$")) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i postnr.");
            LOGGER.error("Feil i postnr");
            return;
        }

        String sql1 = "INSERT INTO Kunde (fornavn, etternavn, adresse, postnr, telefonnr, epost) VALUES (?,?,?,?,?,?)";
        String sql2 = "INSERT INTO Pakke (KId, Volum, Vekt) VALUES (?,?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        try {
            db.update(con -> {
                PreparedStatement prep = con.prepareStatement(sql1, new String[]{"KId"});
                prep.setString(1, pakkeRegistrering.getFornavn());
                prep.setString(2, pakkeRegistrering.getEtternavn());
                prep.setString(3, pakkeRegistrering.getAdresse());
                prep.setString(4, pakkeRegistrering.getPostnr());
                prep.setString(5, pakkeRegistrering.getTelefonnr());
                prep.setString(6, pakkeRegistrering.getEpost());
                return prep;
            }, id);
            int kid = id.getKey().intValue();
            db.update(sql2, kid, pakkeRegistrering.getPakkevolum(), pakkeRegistrering.getVekt());
            System.out.println("Pakke og kunde lagt til i databasen.");
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen, prøv igjen senere.");
            LOGGER.error("Feil i databasen: " + e);
        }

    }

    @GetMapping("/sjekkPostnr")
    @ResponseBody
    public boolean sjekkPostNr(@RequestParam String postnr) {
        String sql = "SELECT COUNT(*) FROM Poststed WHERE Postnr = ?";
        int finnes = db.queryForObject(sql, Integer.class, postnr);
        if(finnes != 0) {
            return true;
        } else {
            return false;
        }
    }


}
