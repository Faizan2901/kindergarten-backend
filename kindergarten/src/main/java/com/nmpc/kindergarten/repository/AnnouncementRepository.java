package com.nmpc.kindergarten.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmpc.kindergarten.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{

}
