package com.Reparafacil.ReparafacilV2.config;

import com.Reparafacil.ReparafacilV2.model.*;
import com.Reparafacil.ReparafacilV2.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository userRepo,
            TecnicoRepository tecnicoRepo,
            ClienteRepository clienteRepo,
            ServicioRepository servicioRepo,
            GarantiaRepository garantiaRepo,
            AgendaRepository agendaRepo, // <--- ¡AQUÍ ESTÁ LA HERRAMIENTA NUEVA!
            PasswordEncoder encoder) {

        return args -> {
            // ==========================================
            // 1. USUARIOS DEL SISTEMA (LOGIN)
            // ==========================================
            
            // Crear ADMIN si no existe
            if (userRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                userRepo.save(admin);
                System.out.println("--> Usuario ADMIN creado: admin / admin123");
            }

            // Crear USUARIO TÉCNICO (Login)
            if (userRepo.findByUsername("tecnico1").isEmpty()) {
                Usuario userTec = new Usuario();
                userTec.setUsername("tecnico1");
                userTec.setPassword(encoder.encode("123456"));
                userTec.setRol(Rol.TECNICO);
                userRepo.save(userTec);
                System.out.println("--> Usuario TECNICO (Login) creado: tecnico1 / 123456");
            }

            // Crear USUARIO CLIENTE (Login)
            if (userRepo.findByUsername("cliente1").isEmpty()) {
                Usuario userCliente = new Usuario();
                userCliente.setUsername("cliente1");
                userCliente.setPassword(encoder.encode("123456"));
                userCliente.setRol(Rol.CLIENTE);
                userRepo.save(userCliente);
                System.out.println("--> Usuario CLIENTE (Login) creado: cliente1 / 123456");
            }

            // ==========================================
            // 2. PERFILES PÚBLICOS (CATÁLOGOS)
            // ==========================================

            // Perfil Técnico 1
            if (tecnicoRepo.findByEmail("carlos.perez@reparafacil.com").isEmpty()) {
                Tecnico t1 = new Tecnico();
                t1.setNombre("Carlos");
                t1.setApellido("Pérez");
                t1.setEmail("carlos.perez@reparafacil.com");
                t1.setTelefono("987654321");
                t1.setEspecialidad("Electrodomésticos");
                t1.setDisponible(true);
                tecnicoRepo.save(t1);
                System.out.println("--> Perfil de Técnico creado: Carlos Pérez");
            }

            // Perfil Técnico 2
            if (tecnicoRepo.findByEmail("ana.rojas@reparafacil.com").isEmpty()) {
                Tecnico t2 = new Tecnico();
                t2.setNombre("Ana");
                t2.setApellido("Rojas");
                t2.setEmail("ana.rojas@reparafacil.com");
                t2.setTelefono("912345678");
                t2.setEspecialidad("Refrigeración Industrial");
                t2.setDisponible(true);
                tecnicoRepo.save(t2);
                System.out.println("--> Perfil de Técnico creado: Ana Rojas");
            }

            // ==========================================
            // 3. DATOS DE PRUEBA PARA GARANTÍAS Y SERVICIOS
            // ==========================================
            
            if (clienteRepo.findByEmail("cliente.garantia@test.com").isEmpty()) {
                // A) Crear Cliente de prueba
                Cliente cliente = new Cliente();
                cliente.setNombre("Mario");
                cliente.setApellido("Bros");
                cliente.setEmail("cliente.garantia@test.com");
                cliente.setTelefono("555-1234");
                cliente.setDireccion("Mushroom Kingdom 1");
                clienteRepo.save(cliente);

                // B) Crear un Servicio YA COMPLETADO
                Servicio servicio = new Servicio();
                servicio.setDescripcionProblema("Reparación de tubería principal");
                servicio.setDiagnostico("Tubería obstruida por planta carnívora");
                servicio.setSolucion("Limpieza profunda y reemplazo de codo");
                servicio.setEstado(EstadoServicio.COMPLETADO);
                servicio.setFechaSolicitud(LocalDateTime.now().minusDays(10)); // Solicitado hace 10 días
                servicio.setCliente(cliente);
                servicioRepo.save(servicio);

                // C) Crear la Garantía asociada a ese servicio
                Garantia garantia = new Garantia();
                garantia.setFechaInicio(LocalDate.now().minusDays(5)); // Empezó hace 5 días
                garantia.setFechaFin(LocalDate.now().plusDays(85));    // Termina en 85 días (Vigente)
                garantia.setDetalles("Cobertura total sobre fugas y mano de obra.");
                garantia.setServicio(servicio); 
                garantiaRepo.save(garantia);

                System.out.println("--> DATOS DE PRUEBA CREADOS:");
                System.out.println("    Cliente: Mario Bros");
                System.out.println("    Servicio ID: " + servicio.getId());
                System.out.println("    Garantía ID (Usar en el buscador): " + garantia.getId());
            }

            // ==========================================
            // 4. DATOS DE PRUEBA PARA AGENDA (NUEVO)
            // ==========================================
            
            // Verificamos si ya existe alguna agenda para no duplicar
            if (servicioRepo.count() > 0 && tecnicoRepo.count() > 0 && agendaRepo.count() == 0) {
               // Vamos a crear una Cita de prueba para el técnico "Carlos Pérez"
               Tecnico tecnico = tecnicoRepo.findByEmail("carlos.perez@reparafacil.com").orElse(null);
               
               // Creamos un servicio rápido para agendarlo
               if (tecnico != null) {
                   // Buscamos un cliente (Mario o creamos uno)
                   Cliente cliente = clienteRepo.findAll().stream().findFirst().orElse(null);
                   
                   if (cliente != null) {
                       // 1. Crear Servicio para la cita
                       Servicio servicioAgenda = new Servicio();
                       servicioAgenda.setDescripcionProblema("Mantenimiento de Aire Acondicionado");
                       servicioAgenda.setEstado(EstadoServicio.ASIGNADO);
                       servicioAgenda.setCliente(cliente);
                       servicioAgenda.setTecnico(tecnico);
                       
                       // ====================================================================================
                       // IMPORTANTE: COMENTAMOS ESTA LÍNEA PARA EVITAR "detached entity passed to persist"
                       // Al no guardar manualmente aquí, Hibernate guardará el servicio automáticamente 
                       // cuando guardemos la Agenda, gracias al CascadeType.ALL en la entidad Agenda.
                       // ====================================================================================
                       // servicioRepo.save(servicioAgenda); 

                       // 2. Crear Agenda (La Cita)
                       Agenda cita = new Agenda();
                       cita.setServicio(servicioAgenda);
                       cita.setTecnico(tecnico);
                       // Cita para mañana a las 10:00 AM
                       cita.setFechaHoraInicio(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)); 
                       // Termina mañana a las 12:00 PM
                       cita.setFechaHoraFin(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0));    
                       cita.setEstado(EstadoAgenda.RESERVADO);
                       
                       // Guardamos usando la herramienta que pedimos arriba
                       agendaRepo.save(cita); 
                       
                       System.out.println("--> Cita de Agenda creada para: " + tecnico.getNombre());
                   }
               }
            }
        };
    }
}