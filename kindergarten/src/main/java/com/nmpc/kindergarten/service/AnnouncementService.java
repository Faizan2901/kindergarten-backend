package com.nmpc.kindergarten.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.model.Announcement;
import com.nmpc.kindergarten.repository.AnnouncementRepository;

@Service
public class AnnouncementService {

	@Autowired
	private AnnouncementRepository announcementRepository;
	
	public List<Announcement> getAllAnnouncements(){
		return announcementRepository.findAll();
	}
	
	public Announcement saveAnnouncement(Announcement announcement) {
		return announcementRepository.save(announcement);
	}
	
	public void deleteAnnouncement(Long id) {
		announcementRepository.deleteById(id);
	}
	
}
