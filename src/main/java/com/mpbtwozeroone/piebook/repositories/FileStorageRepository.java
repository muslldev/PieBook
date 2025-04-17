package com.mpbtwozeroone.piebook.repositories;

import com.mpbtwozeroone.piebook.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
}
