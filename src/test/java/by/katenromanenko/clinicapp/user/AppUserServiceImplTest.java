package by.katenromanenko.clinicapp.user;

import by.katenromanenko.clinicapp.common.error.NotFoundException;
import by.katenromanenko.clinicapp.specialization.Specialization;
import by.katenromanenko.clinicapp.specialization.SpecializationRepository;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import by.katenromanenko.clinicapp.user.mapper.AppUserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock private AppUserRepository appUserRepository;
    @Mock private AppUserMapper appUserMapper;
    @Mock private SpecializationRepository specializationRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserServiceImpl service;

    private UUID userId;
    private UUID specId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        specId = UUID.randomUUID();
    }

    // -------------------------
    // CREATE
    // -------------------------

    @Test
    void create_whenPasswordBlank_thenThrowIllegalArgumentException() {
        // Arrange
        AppUserDto dto = baseDto();
        AppUser entity = baseEntity();
        entity.setPasswordHash("   "); // blank

        when(appUserMapper.toEntity(dto)).thenReturn(entity);

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(dto)
        );
        assertEquals("passwordHash обязателен", ex.getMessage());

        verify(appUserRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void create_whenPasswordIsRaw_thenEncodeAndSave() {
        // Arrange
        AppUserDto dto = baseDto();
        dto.setSpecializationId(null);

        AppUser entity = baseEntity();
        entity.setUserId(null);                 // проверим генерацию UUID
        entity.setPasswordHash("Qwerty123!");   // raw пароль

        when(appUserMapper.toEntity(dto)).thenReturn(entity);
        when(passwordEncoder.encode("Qwerty123!")).thenReturn("$2b$hashed");
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        // Act
        AppUserDto result = service.create(dto);

        // Assert
        assertNotNull(result);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());

        AppUser saved = captor.getValue();
        assertNotNull(saved.getUserId(), "UUID должен сгенерироваться");
        assertEquals("$2b$hashed", saved.getPasswordHash(), "сырой пароль должен стать bcrypt");
        assertTrue(saved.isActive(), "при create user должен стать active=true");
        assertNotNull(saved.getCreatedAt(), "createdAt должен заполниться");
        assertNull(saved.getUpdatedAt(), "updatedAt при create должен быть null");
        assertNull(saved.getSpecialization(), "если specializationId=null — specialization=null");

        verify(passwordEncoder, times(1)).encode("Qwerty123!");
    }

    @Test
    void create_whenPasswordAlreadyBcrypt_thenDoNotEncode() {
        // Arrange
        AppUserDto dto = baseDto();
        dto.setSpecializationId(null);

        AppUser entity = baseEntity();
        entity.setPasswordHash("$2b$alreadyHashed");

        when(appUserMapper.toEntity(dto)).thenReturn(entity);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        // Act
        AppUserDto result = service.create(dto);

        // Assert
        assertNotNull(result);
        verify(passwordEncoder, never()).encode(anyString());

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        assertEquals("$2b$alreadyHashed", captor.getValue().getPasswordHash());
    }

    @Test
    void create_whenDoctorWithSpecialization_thenSetSpecialization() {
        // Arrange
        AppUserDto dto = baseDto();
        dto.setSpecializationId(specId);

        AppUser entity = baseEntity();
        entity.setPasswordHash("$2b$alreadyHashed");

        Specialization spec = new Specialization();
        spec.setSpecId(specId);

        when(appUserMapper.toEntity(dto)).thenReturn(entity);
        when(specializationRepository.findById(specId)).thenReturn(Optional.of(spec));
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        // Act
        AppUserDto result = service.create(dto);

        // Assert
        assertNotNull(result);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        assertNotNull(captor.getValue().getSpecialization());
        assertEquals(specId, captor.getValue().getSpecialization().getSpecId());
    }

    @Test
    void create_whenSpecializationIdProvidedButNotFound_thenThrowNotFound() {
        // Arrange
        AppUserDto dto = baseDto();
        dto.setSpecializationId(specId);

        AppUser entity = baseEntity();
        entity.setPasswordHash("$2b$alreadyHashed");

        when(appUserMapper.toEntity(dto)).thenReturn(entity);
        when(specializationRepository.findById(specId)).thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.create(dto));
        assertTrue(ex.getMessage().contains("Специализация не найдена"));

        verify(appUserRepository, never()).save(any());
    }

    // -------------------------
    // GET / LIST
    // -------------------------

    @Test
    void getById_whenNotFound_thenThrowNotFound() {
        UUID id = UUID.randomUUID();
        when(appUserRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(id));
        assertTrue(ex.getMessage().contains("Пользователь не найден"));
    }

    @Test
    void getAll_shouldReturnDtoList() {
        List<AppUser> entities = List.of(baseEntity(), baseEntity());
        List<AppUserDto> dtos = List.of(baseDto(), baseDto());

        when(appUserRepository.findAll()).thenReturn(entities);
        when(appUserMapper.toDtoList(entities)).thenReturn(dtos);

        List<AppUserDto> result = service.getAll();

        assertEquals(2, result.size());
        verify(appUserRepository, times(1)).findAll();
        verify(appUserMapper, times(1)).toDtoList(entities);
    }

    // -------------------------
    // UPDATE
    // -------------------------

    @Test
    void update_whenPasswordNotProvided_thenKeepOldPassword() {
        // Arrange
        UUID id = userId;

        AppUser existing = baseEntity();
        existing.setUserId(id);
        existing.setPasswordHash("$2b$old");
        existing.setCreatedAt(LocalDateTime.now().minusDays(1));
        existing.setActive(true);

        AppUserDto dto = baseDto();
        dto.setPasswordHash("   ");
        dto.setSpecializationId(null);

        AppUser mapped = baseEntity();
        mapped.setPasswordHash("   ");

        when(appUserRepository.findById(id)).thenReturn(Optional.of(existing));
        when(appUserMapper.toEntity(dto)).thenReturn(mapped);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        // Act
        AppUserDto result = service.update(id, dto);

        // Assert
        assertNotNull(result);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        AppUser saved = captor.getValue();

        assertEquals(id, saved.getUserId());
        assertEquals("$2b$old", saved.getPasswordHash(), "должен остаться старый хэш");
        assertEquals(existing.getCreatedAt(), saved.getCreatedAt(), "createdAt сохраняем");
        assertNotNull(saved.getUpdatedAt(), "updatedAt проставляем");
        assertTrue(saved.isActive(), "active сохраняем");
        assertEquals(existing.getSpecialization(), saved.getSpecialization(), "специализация сохраняется если specializationId=null");

        verify(passwordEncoder, never()).encode(anyString());
    }

    // -------------------------
    // DELETE
    // -------------------------

    @Test
    void delete_whenNotExists_thenThrowNotFound() {
        UUID id = UUID.randomUUID();
        when(appUserRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.delete(id));
        verify(appUserRepository, never()).deleteById(any());
    }

    @Test
    void delete_whenExists_thenDelete() {
        UUID id = UUID.randomUUID();
        when(appUserRepository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(appUserRepository).deleteById(id);
    }

    // -------------------------
    // ACTIVATE / DEACTIVATE
    // -------------------------

    @Test
    void activate_shouldSetActiveTrue() {
        UUID id = UUID.randomUUID();
        AppUser entity = baseEntity();
        entity.setUserId(id);
        entity.setActive(false);

        AppUserDto dto = baseDto();

        when(appUserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        AppUserDto result = service.activate(id);

        assertNotNull(result);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        assertTrue(captor.getValue().isActive());
        assertNotNull(captor.getValue().getUpdatedAt());
    }

    @Test
    void deactivate_shouldSetActiveFalse() {
        UUID id = UUID.randomUUID();
        AppUser entity = baseEntity();
        entity.setUserId(id);
        entity.setActive(true);

        AppUserDto dto = baseDto();

        when(appUserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(appUserMapper.toDto(any(AppUser.class))).thenReturn(dto);

        AppUserDto result = service.deactivate(id);

        assertNotNull(result);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        assertFalse(captor.getValue().isActive());
        assertNotNull(captor.getValue().getUpdatedAt());
    }

    // -------------------------
    // Helpers
    // -------------------------

    private AppUserDto baseDto() {
        AppUserDto dto = new AppUserDto();
        dto.setId(UUID.randomUUID());
        dto.setLogin("login_" + UUID.randomUUID());
        dto.setPasswordHash("Qwerty123!");
        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setEmail("ivanov@example.com");
        dto.setRole(UserRole.DOCTOR);
        dto.setActive(true);
        return dto;
    }

    private AppUser baseEntity() {
        AppUser u = new AppUser();
        u.setUserId(UUID.randomUUID());
        u.setLogin("login_" + UUID.randomUUID());
        u.setPasswordHash("Qwerty123!");
        u.setFirstName("Ivan");
        u.setLastName("Ivanov");
        u.setEmail("ivanov@example.com");
        u.setRole(UserRole.DOCTOR);
        u.setActive(true);
        return u;
    }
}
