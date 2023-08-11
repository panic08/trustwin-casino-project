package eu.panic.adminservice.template.controller;

import eu.panic.adminservice.template.service.implement.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
    public AdminAuthController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    private final AdminServiceImpl adminService;
    @PostMapping("/block")
    private void handleBlockUserById(
            @RequestHeader String jwtToken,
            @RequestParam long id
    ){
        adminService.handleBlockUserById(jwtToken, id);
    }

    @PostMapping("/unblock")
    private void handleUnblockUserById(
            @RequestHeader String jwtToken,
            @RequestParam long id
    ){
        adminService.handleUnblockUserById(jwtToken, id);
    }

}
