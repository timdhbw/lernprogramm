package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.service.AufgabeService;
import de.master.lernprogramm.service.UserService;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author tiedet (PiAL Consult GmbH) 2021.
 */
@Service
@Slf4j
public class FrontendDelegateImpl implements de.master.lernprogramm.web.api.FrontendApiDelegate {

    private final UserService userService;

    private final AufgabeService aufgabeService;

    private final FrontendMapper frontendMapper;

    public FrontendDelegateImpl(UserService userService, AufgabeService aufgabeService, FrontendMapper frontendMapper) {
        this.userService = userService;
        this.aufgabeService = aufgabeService;
        this.frontendMapper = frontendMapper;
    }

    @Override
    public ResponseEntity<ProfilUiDto> getProfil() {
        log.info("GetProfil");
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        log.info("Optional User: {}", optionalUser);
        return optionalUser.map(user -> ResponseEntity.ok(frontendMapper.toUiDto(user)))
            .orElseGet(() -> ResponseEntity.status(204).build());
    }

    @Override
    public ResponseEntity<AufgabeUiDto> getAufgabeById(Integer aufgabeId) {
        Aufgabe aufgabe = aufgabeService.getAufgabeById(aufgabeId);
        if (aufgabe == null) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(frontendMapper.toUiDto(aufgabe));
    }
}
