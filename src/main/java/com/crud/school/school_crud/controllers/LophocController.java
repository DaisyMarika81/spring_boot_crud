package com.crud.school.school_crud.controllers;

import com.crud.school.school_crud.dto.CULopHocDTO;
import com.crud.school.school_crud.entities.LopHoc;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.services.LopHocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lophoc")
public class LophocController {
    @Autowired
    private LopHocService lophocService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllLophoc() {
        return lophocService.getAllLophoc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getLophocById(@PathVariable int id) {
        return lophocService.getLophocById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createLophoc(@RequestBody @Valid CULopHocDTO createKopHocDTO) {
        return lophocService.saveLophoc(createKopHocDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateLophoc(@PathVariable Integer id, @RequestBody @Valid CULopHocDTO updatedLopHocDTO) {
        return lophocService.updateLopHoc(id, updatedLopHocDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteLophoc(@PathVariable Integer id) {
        return lophocService.deleteLophoc(id);
    }
}