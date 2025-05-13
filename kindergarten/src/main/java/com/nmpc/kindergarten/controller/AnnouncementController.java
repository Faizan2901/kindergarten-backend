package com.nmpc.kindergarten.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nmpc.kindergarten.model.Announcement;
import com.nmpc.kindergarten.service.AnnouncementService;

@RestController
@RequestMapping("/api/announcement")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {

	@Autowired
	private AnnouncementService announcementService;

	@GetMapping
	public List<Announcement> getAllAnnouncements() {
		return announcementService.getAllAnnouncements();
	}

	@PostMapping
	public Announcement saveAnnouncement(@RequestBody Announcement announcement) {
		return announcementService.saveAnnouncement(announcement);
	}

	@DeleteMapping("/{id}")
	public void deleteAnnouncement(@PathVariable Long id) {
		announcementService.deleteAnnouncement(id);
	}

}
