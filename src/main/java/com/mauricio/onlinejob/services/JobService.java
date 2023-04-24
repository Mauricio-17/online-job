package com.mauricio.onlinejob.services;

import com.mauricio.onlinejob.dto.JobDto;
import com.mauricio.onlinejob.dto.JobDtoResponse;
import com.mauricio.onlinejob.entities.Job;
import com.mauricio.onlinejob.entities.Company;
import com.mauricio.onlinejob.entities.JobType;
import com.mauricio.onlinejob.repositories.JobRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public JobDtoResponse getJobs(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return null;
    }

    public ResponseEntity<JobDto> saveJob(@Valid @RequestBody JobDto jobDto){
        Job job = dtoToEntity.apply(jobDto);
        jobRepository.save(job);
        JobDto dtoResponse = entityToDto.apply(job);
        return ResponseEntity.status(201).body(dtoResponse);
    }


    private final Function<Job, JobDto> entityToDto = emp ->
            new JobDto(
                    emp.getId(),
                    emp.getTitle(),
                    emp.getSalary(),
                    emp.getJobType().toString(),
                    emp.getLocation(),
                    emp.getCompany().getId(),
                    emp.getCreatedAt().format(dateTimeFormatter),
                    emp.getUpdatedAt().format(dateTimeFormatter)
            );

    private final Function<JobDto, Job> dtoToEntity = emp -> {
        Company company = Company.builder().id(emp.companyId()).build();
        return Job.builder()
                .title(emp.title())
                .salary(emp.salary())
                .jobType(JobType.valueOf(emp.jobType()))
                .location(emp.location())
                .company(company)
                .build();
    };


}
