package com.lot.server.checkin.service;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.checkin.domain.model.CheckInDTO;
import com.lot.server.checkin.domain.model.ItemStatusDTO;

public interface CheckInService {
    ResultTO<Void> checkIn(CheckInDTO checkInDTO);
    ResultTO<Void> checkOut(CheckInDTO checkInDTO);
    ResultTO<ItemStatusDTO> checkStatus(Integer productId, String serialNumber);
}
