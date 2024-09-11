package com.crud.school.school_crud.services;

import com.crud.school.school_crud.dto.CUHocSinhDTO;
import com.crud.school.school_crud.entities.HocSinh;
import com.crud.school.school_crud.entities.LopHoc;
import com.crud.school.school_crud.entities.ResponseObject;
import com.crud.school.school_crud.repositories.HocSinhRepository;
import com.crud.school.school_crud.repositories.LopHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HocSinhService {
    @Autowired
    private HocSinhRepository hocsinhRepository;
    @Autowired
    private LopHocRepository lopHocRepository;

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

    public ResponseEntity<ResponseObject> saveHocsinh(CUHocSinhDTO createHocSinhDTO) {
        HocSinh existedHocSinh = hocsinhRepository.findByMaHs(createHocSinhDTO.getMaHs());
        if (existedHocSinh != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Hoc sinh existed, please try again!", "")
            );
        }

        LopHoc existedLopHoc = lopHocRepository.findByMaLop(createHocSinhDTO.getMaLop());

        if (existedLopHoc == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Lop hoc not existed, please try again!", "")
            );
        }

        HocSinh savedHocSinh = new HocSinh
                (createHocSinhDTO.getMaHs(), createHocSinhDTO.getTenHs(), existedLopHoc, createHocSinhDTO.getGhichu());

        hocsinhRepository.save(savedHocSinh);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(201, "Hoc sinh created successfully!", "")
        );
    }

    public ResponseEntity<ResponseObject> updateHocSinh(Integer id, CUHocSinhDTO updatedHocSinhDTO) {
        Optional<HocSinh> existingHocSinhOptional = hocsinhRepository.findById(id);

        if (existingHocSinhOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Hoc Sinh not found!", "")
            );
        }

        HocSinh existingHocSinh = existingHocSinhOptional.get();

        HocSinh existedHocSinhByMaHs = hocsinhRepository.findByMaHs(updatedHocSinhDTO.getMaHs());

        if (existedHocSinhByMaHs != null && !Objects.equals(existingHocSinh.getId(), existedHocSinhByMaHs.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "maHs already exists for another Hoc Sinh!", "")
            );
        }

        LopHoc existedLopHoc = lopHocRepository.findByMaLop(updatedHocSinhDTO.getMaLop());

        if (existedLopHoc == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Lop hoc not found, please try again!", "")
            );
        }

        HocSinh updatedHocSinh = new HocSinh
                (updatedHocSinhDTO.getMaHs(), updatedHocSinhDTO.getTenHs(), existedLopHoc, updatedHocSinhDTO.getGhichu());

        HocSinh updatedSaveHocSinh = getHocSinh(updatedHocSinh, existingHocSinhOptional);

        hocsinhRepository.save(updatedSaveHocSinh);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Hoc Sinh updated successfully!", "")
        );

    }

    public ResponseEntity<ResponseObject> deleteHocsinh(Integer id) {
        if (hocsinhRepository.existsById(id)) {
            hocsinhRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Hoc Sinh deleted successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Hoc Sinh not found!", "")
            );
        }
    }

    private static HocSinh getHocSinh(HocSinh updatedHocSinh, Optional<HocSinh> existingHocSinhOptional) {
        HocSinh existingHocSinh = existingHocSinhOptional.get();

        if (updatedHocSinh.getMaHs() != null && !updatedHocSinh.getMaHs().isEmpty())
            existingHocSinh.setMaHs(updatedHocSinh.getMaHs());
        if (updatedHocSinh.getTenHs() != null && !updatedHocSinh.getTenHs().isEmpty())
            existingHocSinh.setTenHs(updatedHocSinh.getTenHs());
        if (updatedHocSinh.getGhichu() != null && !updatedHocSinh.getGhichu().isEmpty())
            existingHocSinh.setGhichu(updatedHocSinh.getGhichu());
        if (updatedHocSinh.getLophoc() != null)
            existingHocSinh.setLophoc(updatedHocSinh.getLophoc());
        return existingHocSinh;
    }
}
