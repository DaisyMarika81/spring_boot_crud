package com.crud.school.school_crud.repositories;

import com.crud.school.school_crud.entities.LopHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LopHocRepository extends JpaRepository<LopHoc, Integer> {

    LopHoc findByMaLop (String maLop);

}
