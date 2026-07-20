package br.com.ifba.prg04deskflow.chamado.controller;

import br.com.ifba.prg04deskflow.chamado.dto.DashboardDTO;
import br.com.ifba.prg04deskflow.chamado.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard(Authentication authentication) {
        String email = authentication.getName();
        String perfil = authentication.getAuthorities().iterator().next().getAuthority();

        DashboardDTO dto = switch (perfil) {
            case "ROLE_ADMIN"        -> dashboardService.getDashboardAdmin();
            case "ROLE_TECNICO"      -> dashboardService.getDashboardTecnico(email);
            default                  -> dashboardService.getDashboardUsuario(email);
        };

        return ResponseEntity.ok(dto);
    }
}