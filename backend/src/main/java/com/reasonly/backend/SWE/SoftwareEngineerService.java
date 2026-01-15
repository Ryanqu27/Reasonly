package com.reasonly.backend.SWE;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public List<SoftwareEngineer> getSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException(""));
    }

    public void insertSoftwareEngineer(SoftwareEngineer newEngineer) {
        softwareEngineerRepository.save(newEngineer);
    }

    public void deleteSoftwareEngineer(Integer id) {
        softwareEngineerRepository.deleteById(id);
    }

    public void updateSoftwareEngineer(Integer id, SoftwareEngineer updatedEngineer) {
        SoftwareEngineer existingEngineer = softwareEngineerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Software engineer not found with id: " + id));
        existingEngineer.setName(updatedEngineer.getName());
        existingEngineer.setTechStack(updatedEngineer.getTechStack());
        existingEngineer.setQuestionType(updatedEngineer.getQuestionType());
        softwareEngineerRepository.save(existingEngineer);
    }
}
