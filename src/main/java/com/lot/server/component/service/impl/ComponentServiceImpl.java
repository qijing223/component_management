package com.lot.server.component.service.impl;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.common.context.UserContext;
import com.lot.server.component.domain.entity.ComponentStatus;
import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.domain.entity.ComponentDO;
import com.lot.server.component.domain.model.ReturnedDTO;
import com.lot.server.component.mapper.ComponentMapper;
import com.lot.server.component.service.ComponentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentMapper componentMapper;

    @Override
    public ComponentDTO createProduct(ComponentDTO dto) {
        ComponentDO entity = convertToDO(dto);
        componentMapper.insertProduct(entity);
        return convertToDTO(entity);
    }

    @Override
    public ComponentDTO updateProduct(Integer id, ComponentDTO dto) {
        ComponentDO existing = componentMapper.getProductById(id);
        if (existing == null) {
            return null;
        }

        // 更新字段
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus().getValue()); // ComponentStatus 枚举转字符串
        }
        if (dto.getProductName() != null) {
            existing.setProductName(dto.getProductName());
        }
        if (dto.getProductId() != null) {
            existing.setProductId(dto.getProductId());
        }
        if (dto.getCost() != null) {
            existing.setCost(dto.getCost());
        }
        if (dto.getPartName() != null) {
            existing.setPartName(dto.getPartName());
        }
        if (dto.getBorrowedEmployeeId() != null) {
            existing.setBorrowedEmployeeId(dto.getBorrowedEmployeeId());
        }

        componentMapper.updateProduct(existing);
        return convertToDTO(existing);
    }

    @Override
    public ComponentDTO getProductById(Integer id) {
        ComponentDO product = componentMapper.getProductById(id);
        if (product == null) {
            return null;
        }
        return convertToDTO(product);
    }

    public List<ComponentDTO> getProductsByStatus(String status) {
        List<ComponentDO> list = componentMapper.selectByStatus(status);
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Integer id) {
        componentMapper.deleteProductById(id);
    }

    @Override
    public List<ComponentDTO> getAllProducts() {
        return componentMapper.selectAllProducts()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComponentDTO> getBorrowedById(Integer userId) {
        return componentMapper.selectBorrowedById(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReturnedDTO> getReturnedById(Integer userId) {
        return componentMapper.selectReturnedById(userId)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void updateBorrowedByPartId(Integer userId, Integer partId){
        componentMapper.updateBorrowedByPartId(userId, partId);
    }

    @Override
    public void createReturnedByPartId(Integer userId, Integer partId) {
        componentMapper.deleteBorrowedByPartId(partId);
        ComponentDTO componentDTO = this.getProductById(partId);
        ReturnedDTO returnedDTO = new ReturnedDTO();
        returnedDTO.setPartId(partId);
        returnedDTO.setPartName(componentDTO.getPartName());
        returnedDTO.setReturnTime(LocalDateTime.now());
        returnedDTO.setBorrowEmployeeId(userId);
        componentMapper.insertReturned(returnedDTO);
    }


    @Override
    public List<ComponentDTO> getComponentsByProductId(Integer productId) {
        return componentMapper.selectByProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ComponentDO convertToDO(ComponentDTO dto) {
        ComponentDO entity = new ComponentDO();
        entity.setPartId(dto.getPartId());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus().getValue() : null);
        entity.setProductName(dto.getProductName());
        entity.setProductId(dto.getProductId());
        entity.setCost(dto.getCost());
        entity.setPartName(dto.getPartName());
        entity.setBorrowedEmployeeId(UserContext.getUserId());
        return entity;
    }

    private ComponentDTO convertToDTO(ComponentDO entity) {
        if (entity == null) {
            return null;
        }
        ComponentDTO dto = new ComponentDTO();
        dto.setPartId(entity.getPartId());
        dto.setStatus(entity.getStatus() != null ? ComponentStatus.fromValue(entity.getStatus()) : null);
        dto.setProductName(entity.getProductName());
        dto.setProductId(entity.getProductId());
        dto.setCost(entity.getCost());
        dto.setPartName(entity.getPartName());
        dto.setBorrowedEmployeeId(entity.getBorrowedEmployeeId());
        return dto;
    }
}
