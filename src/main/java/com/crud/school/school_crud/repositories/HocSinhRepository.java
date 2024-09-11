package com.crud.school.school_crud.repositories;

import com.crud.school.school_crud.entities.HocSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HocSinhRepository extends JpaRepository<HocSinh, Integer> {

    HocSinh findByMaHs(String maHs);

}
