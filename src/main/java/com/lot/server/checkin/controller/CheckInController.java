package com.lot.server.checkin.controller;

import com.lot.server.checkin.domain.model.CheckInDTO;
import com.lot.server.checkin.service.CheckInService;
import com.lot.server.common.bean.ResultTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/checkin")
    public ResultTO<Void> checkIn(@RequestBody CheckInDTO checkInDTO) {
        return checkInService.checkIn(checkInDTO);
    }

    @PostMapping("/checkout")
    public ResultTO<Void> checkOut(@RequestBody CheckInDTO checkInDTO) {
        return checkInService.checkOut(checkInDTO);
    }
}
