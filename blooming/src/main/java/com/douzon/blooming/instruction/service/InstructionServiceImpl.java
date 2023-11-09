package com.douzon.blooming.instruction.service;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.instruction.dto.request.InsertInstructionDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.exception.NotFoundInstructionException;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import com.douzon.blooming.product_instruction.exception.UnsupportedProductStatusException;
import com.douzon.blooming.product_instruction.repo.ProductInstructionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class InstructionServiceImpl implements InstructionService {
    private final InstructionRepository instructionRepository;
    private final ProductInstructionRepository productInstructionRepository;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addInstruction(RequestInstructionDto dto) {
//        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();

        InsertInstructionDto insertDto = new InsertInstructionDto(2000006L, dto.getCustomerNo(),
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
                    .append(",")
                    .append(product.getAmount())
                    .append("),");
        });
        stringBuffer.setLength(stringBuffer.length() - 1);
        stringBuffer.append(";");

        String insertQuery = stringBuffer.toString();
        productInstructionRepository.insert(insertQuery);
    }

    @Override
    public GetInstructionDto findInstruction(String instructionNo) {
        GetInstructionDto dto = instructionRepository.findInstruction(instructionNo)
            .orElseThrow(NotFoundInstructionException::new);
        dto.setProducts(productInstructionRepository.getProductList(instructionNo));
        return dto;
    }

    @Override
    public List<ResponseProductInstructionDto> findInstructionDetail(String instructionNo) {
        return productInstructionRepository.getProductList(instructionNo);
    }

    @Override
    public GetInstructionListDto findInstructions(InstructionSearchDto searchDto) {
        int start = (searchDto.getPage() - 1) * searchDto.getPageSize();
        List<ListInstructionDto> instructionList = instructionRepository.findInstructions(searchDto, start, searchDto.getPageSize());
        int searchInstructionCount = instructionRepository.getCountInstructions(searchDto);

        boolean hasNextPage = (start + searchDto.getPageSize()) < searchInstructionCount;
        boolean hasPreviousPage = start > 0;

        return new GetInstructionListDto(instructionList, searchDto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateInstruction(String instructionNo, UpdateInstructionDto dto) {
        if (instructionRepository.updateInstruction(instructionNo, dto) <= 0) {
            throw new NotFoundInstructionException();
        }
        dto.getProducts().forEach(product -> {
            switch (product.getStatus()) {
                case "added":
                    productInstructionRepository.insertProduct(instructionNo, product);
                    break;
                case "updated":
                    productInstructionRepository.updateProduct(instructionNo, product);
                    break;
                case "deleted":
                    productInstructionRepository.deleteProduct(instructionNo, product);
                    break;
                default:
                    throw new UnsupportedProductStatusException();
            }
        });
    }

    @Override
    public void deleteInstruction(String instructionNo) {
        if (instructionRepository.deleteInstruction(instructionNo) <= 0) {
            throw new NotFoundInstructionException();
        }
    }


}
