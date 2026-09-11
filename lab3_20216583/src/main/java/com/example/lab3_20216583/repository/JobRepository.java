package com.example.lab3_20216583.repository;

import com.example.lab3_20216583.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    @Query("""
            SELECT j
            FROM Job j
            ORDER BY j.jobTitle
            """)
    List<Job> listarJobs();


    @Transactional
    @Modifying
    @Query(
            value = """
                    INSERT INTO jobs
                    (job_id, job_title, min_salary, max_salary)

                    VALUES
                    (:jobId, :jobTitle, :minSalary, :maxSalary)
                    """,
            nativeQuery = true
    )
    int insertarJob(

            @Param("jobId")
            String jobId,

            @Param("jobTitle")
            String jobTitle,

            @Param("minSalary")
            BigDecimal minSalary,

            @Param("maxSalary")
            BigDecimal maxSalary
    );
}
