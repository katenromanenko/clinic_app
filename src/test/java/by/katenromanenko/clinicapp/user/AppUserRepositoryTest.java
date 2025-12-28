package by.katenromanenko.clinicapp.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void save_andFindByLogin_shouldWork() {
        // Arrange
        AppUser user = new AppUser();
        user.setUserId(UUID.randomUUID());
        user.setLogin("test_login");
        user.setPasswordHash("$2b$hash");
        user.setRole(UserRole.DOCTOR);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setEmail("ivanov@example.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);

        // Act
        appUserRepository.save(user);
        Optional<AppUser> found = appUserRepository.findByLogin("test_login");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("test_login", found.get().getLogin());
    }

    @Test
    void existsByLogin_shouldReturnTrue_whenExists() {
        // Arrange
        AppUser user = new AppUser();
        user.setUserId(UUID.randomUUID());
        user.setLogin("exists_login");
        user.setPasswordHash("$2b$hash");
        user.setRole(UserRole.PATIENT);
        user.setFirstName("Petr");
        user.setLastName("Petrov");
        user.setEmail("petrov@example.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);

        appUserRepository.save(user);

        // Act
        boolean exists = appUserRepository.existsByLogin("exists_login");

        // Assert
        assertTrue(exists);
    }

    @Test
    void loginMustBeUnique_shouldThrowOnDuplicate() {
        // Arrange
        AppUser u1 = new AppUser();
        u1.setUserId(UUID.randomUUID());
        u1.setLogin("unique_login");
        u1.setPasswordHash("$2b$hash");
        u1.setRole(UserRole.ADMIN);
        u1.setFirstName("A");
        u1.setLastName("A");
        u1.setEmail("a@example.com");
        u1.setCreatedAt(LocalDateTime.now());
        u1.setActive(true);

        AppUser u2 = new AppUser();
        u2.setUserId(UUID.randomUUID());
        u2.setLogin("unique_login"); // тот же login
        u2.setPasswordHash("$2b$hash");
        u2.setRole(UserRole.ADMIN);
        u2.setFirstName("B");
        u2.setLastName("B");
        u2.setEmail("b@example.com");
        u2.setCreatedAt(LocalDateTime.now());
        u2.setActive(true);

        appUserRepository.save(u1);

        // Act + Assert
        assertThrows(DataIntegrityViolationException.class, () -> appUserRepository.saveAndFlush(u2));
    }
}
