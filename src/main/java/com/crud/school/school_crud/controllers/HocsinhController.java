package com.crud.school.school_crud.controllers;

import com.crud.school.school_crud.dto.CUHocSinhDTO;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.services.HocSinhService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hocsinh")
public class HocsinhController {
    @Autowired
    private HocSinhService hocsinhService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllHocsinh() {
        return hocsinhService.getAllHocsinh();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getHocsinhById(@PathVariable int id) {
        return hocsinhService.getHocsinhById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createHocsinh(@RequestBody @Valid CUHocSinhDTO createHocSinhDTO) {
        return hocsinhService.saveHocsinh(createHocSinhDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateHocsinh(@PathVariable Integer id, @RequestBody @Valid CUHocSinhDTO updatedHocsinhDTO) {
        System.out.println(id);
        return hocsinhService.updateHocSinh(id, updatedHocsinhDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteHocsinh(@PathVariable Integer id) {
        return hocsinhService.deleteHocsinh(id);
    }
}
