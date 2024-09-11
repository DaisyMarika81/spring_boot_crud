package com.crud.school.school_crud.controllers;

import com.crud.school.school_crud.entities.LopHoc;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.services.LopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lophoc")
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
    public ResponseEntity<ResponseObject> createLophoc(@RequestBody LopHoc lophoc) {
        return lophocService.saveLophoc(lophoc);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateLophoc(@PathVariable Integer id, @RequestBody LopHoc updatedLopHoc) {
        return lophocService.updateLopHoc(id, updatedLopHoc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteLophoc(@PathVariable Integer id) {
        return lophocService.deleteLophoc(id);
    }
}