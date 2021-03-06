package de.master.lernprogramm.frontend;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.domain.service.AufgabeService;
import de.master.lernprogramm.domain.service.ProfilService;
import de.master.lernprogramm.service.UserService;
import de.master.lernprogramm.web.api.dtos.AufgabeUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabentagMitSelectUiDto;
import de.master.lernprogramm.web.api.dtos.AufgabenbewertungUiDto;
import de.master.lernprogramm.web.api.dtos.InlineResponse200UiDto;
import de.master.lernprogramm.web.api.dtos.ProfilUiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private final ProfilService profilService;

    public FrontendDelegateImpl(UserService userService, AufgabeService aufgabeService, FrontendMapper frontendMapper, ProfilService profilService) {
        this.userService = userService;
        this.aufgabeService = aufgabeService;
        this.frontendMapper = frontendMapper;
        this.profilService = profilService;
    }

    @Override
    public ResponseEntity<ProfilUiDto> getProfil() {
        log.trace("GetProfil");
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        log.trace("Optional User: {}", optionalUser);
        return optionalUser.map(user -> ResponseEntity
            .ok(frontendMapper.toUiDto(profilService.getProfilById(user.getId().toString()))))
            .orElseGet(() -> ResponseEntity.status(204).build());
    }

    @Override
    public ResponseEntity<AufgabeUiDto> getAufgabeById(Integer aufgabeId) {
        Aufgabe aufgabe = aufgabeService.getAufgabeById(aufgabeId);
        log.info("Aufgabe f??r Aufgabe id '{}': {}", aufgabeId, aufgabe);
        if (aufgabe == null) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(frontendMapper.toUiDto(aufgabe));
    }

    @Override
    public ResponseEntity<List<AufgabentagMitSelectUiDto>> getExistingTagList() {
        return ResponseEntity.ok(Arrays.asList(new AufgabentagMitSelectUiDto().tag("TestTAg")));
    }

    @Override
    public ResponseEntity<List<AufgabeUiDto>> getAufgabenByUserId(String userId) {
        Profil profil = profilService.getProfilById(getUserId().toString());
        if (profil != null) {
            ResponseEntity.ok(frontendMapper.toAufgabeUiDtoList(profil.getAufgabeList()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<String> getRandomNextAufgabe(List<AufgabentagMitSelectUiDto> selectedTagsList) {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        if (!optionalUser.isPresent()) {
            return null;
        }
        String randomNextAufgabeId = aufgabeService.getRandomNextAufgabeForUser(optionalUser.get());
        if (randomNextAufgabeId == null) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok(randomNextAufgabeId);
    }

    @Override
    public ResponseEntity<AufgabeUiDto> saveAufgabe(AufgabeUiDto aufgabeUiDto) {
        Integer userId = getUserId();
        return ResponseEntity.ok(frontendMapper.toUiDto(aufgabeService
            .saveAufgabe(frontendMapper.toDomain(aufgabeUiDto.autorId(userId)))));
    }

    @Override
    public ResponseEntity<InlineResponse200UiDto> aufgabenAbschluss(AufgabeUiDto aufgabeUiDto) {
        Integer userId = getUserId();
        double ergebnis = aufgabeService.berechneErgebnis(frontendMapper.toDomain(aufgabeUiDto));
        profilService.setAufgabeVonProfilAbgeschlossen(userId, aufgabeUiDto.getAufgabeId(), ergebnis);
        return ResponseEntity.ok(new InlineResponse200UiDto().ergenisUser(BigDecimal.valueOf(ergebnis)));
    }

    @Override
    public ResponseEntity<Boolean> setAufgabenbewertung(AufgabenbewertungUiDto aufgabenbewertungUiDto) {
        try {
            aufgabeService.bewerteAufgabe(aufgabenbewertungUiDto.getAufgabeId(), aufgabenbewertungUiDto.getBewertung());
            ResponseEntity.ok(true);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return ResponseEntity.ok(false);
    }

    @Override
    public ResponseEntity<ProfilUiDto> saveProfil(ProfilUiDto profilUiDto) {
        Integer userId = getUserId();
        if (userId != null) {
            log.info("UserId: {}, SaveProfil: {}", userId, profilUiDto);
            Profil profil = profilService.saveProfil(frontendMapper.toDomain(profilUiDto));
            if (profil != null) {
                return ResponseEntity.ok(frontendMapper.toUiDto(profil));
            }
        }
        return ResponseEntity.notFound().build();
    }

    private Integer getUserId() {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        if (optionalUser.isPresent()) {
            return optionalUser.get().getId().intValue();
        }
        return null;
    }
}
