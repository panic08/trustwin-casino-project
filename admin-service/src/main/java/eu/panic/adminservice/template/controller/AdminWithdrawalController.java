package eu.panic.adminservice.template.controller;

import eu.panic.adminservice.template.entity.Withdrawal;
import eu.panic.adminservice.template.enums.WithdrawalStatus;
import eu.panic.adminservice.template.service.implement.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/withdrawal")
public class AdminWithdrawalController {
    public AdminWithdrawalController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    private final AdminServiceImpl adminService;

    @GetMapping("/getAllByWithdrawalStatus")
    private List<Withdrawal> getAllWithdrawals(
            @RequestHeader String jwtToken,
            @RequestParam WithdrawalStatus withdrawalStatus
            ){
        return adminService.getAllWithdrawalsByWithdrawalStatus(jwtToken, withdrawalStatus);
    }

    @PutMapping("/updateWithdrawalStatus")
    private void updateWithdrawalStatusById(
            @RequestHeader String jwtToken,
            @RequestParam WithdrawalStatus withdrawalStatus,
            @RequestParam long id
    ){
        adminService.updateWithdrawalStatusById(jwtToken, withdrawalStatus, id);
    }
}
