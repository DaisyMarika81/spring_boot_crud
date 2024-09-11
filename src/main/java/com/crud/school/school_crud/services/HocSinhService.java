package com.crud.school.school_crud.services;

import com.crud.school.school_crud.entities.HocSinh;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.HocSinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HocSinhService {
    @Autowired
    private HocSinhRepository hocsinhRepository;

    public ResponseEntity<ResponseObject> getAllHocsinh() {
        List<HocSinh> listHocSinh = hocsinhRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "", listHocSinh)
        );
    }

    public ResponseEntity<ResponseObject> getHocsinhById(Integer id) {
        Optional<HocSinh> foundHocSinh = hocsinhRepository.findById(id);
        return foundHocSinh.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "", foundHocSinh)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(404, "Hoc Sinh not found!", "")
                );
    }

    public ResponseEntity<ResponseObject> saveHocsinh(HocSinh hocsinh) {
        HocSinh existedHocSinh = hocsinhRepository.findByMaHs(hocsinh.getMaHs());
        System.out.println(existedHocSinh);
        if (existedHocSinh != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(400, "Hoc sinh existed, please try again!", "")
            );
        }
        hocsinhRepository.save(hocsinh);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(201, "Hoc sinh created successfully!", "")
        );
    }

    public ResponseEntity<ResponseObject> updateHocSinh(Integer id, HocSinh updatedHocSinh) {
        Optional<HocSinh> existingHocSinhOptional = hocsinhRepository.findById(id);

        if (updatedHocSinh.getMaHs() != null && !updatedHocSinh.getMaHs().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(400, "Can not perform update, please try again!", "")
            );

        if (existingHocSinhOptional.isPresent()) {
            HocSinh existingHocSinh = getHocSinh(updatedHocSinh, existingHocSinhOptional);

            hocsinhRepository.save(existingHocSinh);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Hoc Sinh updated successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Hoc Sinh not found!", "")
            );
        }
    }

    public ResponseEntity<ResponseObject> deleteHocsinh(Integer id) {
        if (hocsinhRepository.existsById(id)) {
            hocsinhRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseObject(204, "Hoc Sinh deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Hoc Sinh not found!", "")
            );
        }
    }

    private static HocSinh getHocSinh(HocSinh updatedHocSinh, Optional<HocSinh> existingHocSinhOptional) {
        HocSinh existingHocSinh = existingHocSinhOptional.get();

        if (updatedHocSinh.getTenHs() != null && !updatedHocSinh.getTenHs().isEmpty())
            existingHocSinh.setTenHs(updatedHocSinh.getTenHs());
        if (updatedHocSinh.getGhichu() != null && !updatedHocSinh.getGhichu().isEmpty())
            existingHocSinh.setGhichu(updatedHocSinh.getGhichu());
        if (updatedHocSinh.getLophoc() != null)
            existingHocSinh.setLophoc(updatedHocSinh.getLophoc());
        return existingHocSinh;
    }
}
