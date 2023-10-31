package com.douzon.blooming.instruction.service;

import com.douzon.blooming.instruction.dto.request.InsertInstructionDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.exception.InstructionNotFoundException;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.product_instruction.repo.ProductInstructionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
public class InstructionServiceImpl implements InstructionService{
    private final InstructionRepository instructionRepository;
    private final ProductInstructionRepository productInstructionRepository;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addInstruction(RequestInstructionDto dto) {
        // 추후 토큰을 통해 가져옴
        InsertInstructionDto insertDto = new InsertInstructionDto(dto.getEmployeeNo(), dto.getCustomerNo(),
                dto.getInstructionDate(), dto.getExpirationDate(), dto.getProgressStatus());
        instructionRepository.insertInstruction(insertDto);
        String instructionNo = instructionRepository.getInstructionNo();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO product_instruction VALUES ");
        dto.getProducts().forEach(product -> {
            stringBuffer.append("(")
                    .append(product.getProductNo())
                    .append(", ")
                    .append("'")
                    .append(instructionNo)
                    .append("', ")
                    .append(product.getAmount())
                    .append("),");
        });
        stringBuffer.setLength(stringBuffer.length() - 1);
        stringBuffer.append(";");

        String insertQuery = stringBuffer.toString();
        log.error(insertQuery);
        productInstructionRepository.insert(insertQuery);
    }

    @Override
    public GetInstructionDto getInstruction(String instructionNo) {
        Optional<GetInstructionDto> getInstruction = instructionRepository.findByInstructionNo(instructionNo);
//        log.error(getInstruction.get().toString());
        if (getInstruction.isPresent()) {
            GetInstructionDto dto = getInstruction.get();
            dto.setProducts(productInstructionRepository.getProductList(instructionNo));
            return dto;
        } else {
            throw new InstructionNotFoundException();
        }
    }

    @Override
    public GetInstructionListDto getInstructionList(SearchDto searchDto) {
        int start = (searchDto.getPage() - 1) * searchDto.getPageSize();
        List<ListInstructionDto> instructionList = instructionRepository.findInstructionList(searchDto, start, searchDto.getPageSize());
        int searchInstructionCount = instructionRepository.getCountInstructions(searchDto);

        boolean hasNextPage = start + searchDto.getPage() < searchInstructionCount;
        boolean hasPreviousPage = start > 0;

        return new GetInstructionListDto(instructionList, searchDto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateInstruction(String instructionNo, UpdateInstructionDto dto) {
        instructionRepository.updateInstruction(instructionNo, dto);
        dto.getProducts().forEach(product -> {
            switch (product.getStatus()){
                case "added" :
                    productInstructionRepository.insertProduct(instructionNo, product);
                    break;
                case "updated" :
                    productInstructionRepository.updateProduct(instructionNo, product);
                    break;
                case "deleted" :
                    productInstructionRepository.deleteProduct(instructionNo, product);
                    break;
            }
        });
    }

    @Override
    public void deleteInstruction(String instructionNo) {
        instructionRepository.deleteInstruction(instructionNo);
    }
}
