package com.douzon.blooming.instruction.service;

import com.douzon.blooming.customer.repo.CustomerRepository;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import com.douzon.blooming.instruction.dto.InsertDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionListDto;
import com.douzon.blooming.instruction.exception.InstructionNotFoundException;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.product.repo.ProductRepository;
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
public class InstructionServiceImpl implements InstructionService {
    private final InstructionRepository repository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final ProductInstructionRepository productInstructionRepository;

    @Override
    public void addInstruction(RequestInstructionDto dto) {
        InsertDto insertDto = new InsertDto(employeeRepository.findEmployeeNoByName(dto.getEmployeeName()),
                customerRepository.findCustomerNoByName(dto.getCustomerName()),
                dto.getProducts(), dto.getInstructionDate(), dto.getExpirationDate(), dto.getProgressStatus());
        repository.insertInstruction(insertDto);
        String instructionNo = repository.getInstructionNo();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO product_instruction VALUES ");
        dto.getProducts().forEach(product -> {
            stringBuffer.append("(")
                    .append(productRepository.getProductNoByCode(product.getProductCode()))
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
    public ResponseInstructionDto getInstruction(String instructionNo) {
        Optional<ResponseInstructionDto> getInstruction = repository.findByInstructionNo(instructionNo);
        if (getInstruction.isPresent()) {
            ResponseInstructionDto dto = getInstruction.get();
            dto.setProductList(productInstructionRepository.getProductList(instructionNo));
            return dto;
        } else {
            throw new InstructionNotFoundException();
        }
    }

    @Override
    public ResponseInstructionListDto getInstructionList(SearchDto searchDto) {
        int start = (searchDto.getPage() - 1) * searchDto.getPageSize();
        List<ResponseInstructionDto> instructionList = repository.findInstructionList(searchDto, start, searchDto.getPageSize());
        int searchEmployeeCount = repository.getCountInstructions(searchDto);

        boolean hasNextPage = start + searchDto.getPage() < searchEmployeeCount;
        boolean hasPreviousPage = start > 0;

        return new ResponseInstructionListDto(instructionList, searchDto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateInstruction(String instructionNo, RequestInstructionDto dto) {
        repository.updateInstruction(instructionNo, dto);
    }

    @Override
    public RequestInstructionDto removeInstruction(String instructionNo) {
        repository.deleteInstruction(instructionNo);
        return null;
    }
}
