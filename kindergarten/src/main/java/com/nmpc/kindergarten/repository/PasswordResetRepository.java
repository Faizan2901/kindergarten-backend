package com.nmpc.kindergarten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmpc.kindergarten.model.PasswordResetToken;
import com.nmpc.kindergarten.model.User;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findByToken(String token);

	public void deleteByTokenAndUser(String token, User user);

}
