package com.lot.server.part.service;
import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.domain.model.ReturnedDTO;

import java.util.List;

public interface PartService {
    PartDTO createPart(PartDTO dto);
    PartDTO updatePart(Integer id, PartDTO dto);
    PartDTO getPartById(Integer id);
    List<PartDTO> getPartsByStatus(String status);
    void deletePartById(Integer id);
    List<PartDTO> getAllParts();

    List<PartDTO> getBorrowedById(Integer userId);

    List<ReturnedDTO> getReturnedById(Integer id);

    void updateBorrowedByPartId(Integer userId, Integer partId);
    void createReturnedByPartId(Integer userId, Integer partId);
    List<PartDTO> getPartsByProductId(Integer productId);
}

