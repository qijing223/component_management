package com.lot.server.part.service.impl;
import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.domain.model.ReturnedDTO;
import com.lot.server.part.mapper.PartMapper;
import com.lot.server.part.service.PartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartMapper partMapper;

    @Override
    public PartDTO createPart(PartDTO dto) {
        partMapper.insertPart(dto);
        return dto;
    }

    @Override
    public PartDTO updatePart(Integer id, PartDTO dto) {
        PartDTO existDto = partMapper.getPartById(id);
        if (existDto == null) {
            return null;
        }

        BeanUtils.copyProperties(dto, existDto);

        partMapper.updatePart(existDto);
        return existDto;
    }

    @Override
    public PartDTO getPartById(Integer id) {
        PartDTO part = partMapper.getPartById(id);
        if (part == null) {
            return null;
        }
        return part;
    }

    public List<PartDTO> getPartsByStatus(String status) {
        return partMapper.selectByStatus(status);
    }

    @Override
    public void deletePartById(Integer id) {
        partMapper.deletePartById(id);
    }

    @Override
    public List<PartDTO> getAllParts() {
        return partMapper.selectAllParts();
    }

    @Override
    public List<PartDTO> getBorrowedById(Integer userId) {
        return partMapper.selectBorrowedById(userId);
    }

    @Override
    public List<ReturnedDTO> getReturnedById(Integer userId) {
        return partMapper.selectReturnedById(userId);
    }

    @Override
    public void updateBorrowedByPartId(Integer userId, Integer partId){
        partMapper.updateBorrowedByPartId(userId, partId);
    }

    @Override
    public void createReturnedByPartId(Integer userId, Integer partId) {
        partMapper.deleteBorrowedByPartId(partId);
        PartDTO partDTO = this.getPartById(partId);
        ReturnedDTO returnedDTO = new ReturnedDTO();
        BeanUtils.copyProperties(partDTO, returnedDTO);
        returnedDTO.setReturnTime(LocalDateTime.now());
        returnedDTO.setBorrowEmployeeId(userId);
        partMapper.insertReturned(returnedDTO);
    }


    @Override
    public List<PartDTO> getPartsByProductId(Integer productId) {
        return partMapper.selectByProductId(productId);
    }
}
