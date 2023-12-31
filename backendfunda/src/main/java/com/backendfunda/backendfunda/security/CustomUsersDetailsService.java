package com.backendfunda.backendfunda.security;
import com.backendfunda.backendfunda.model.Roles;
import com.backendfunda.backendfunda.model.Usuarios;
import com.backendfunda.backendfunda.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService{

    @Autowired
    private UsuariosRepository usuariosRepo;

    @Autowired
    public CustomUsersDetailsService(UsuariosRepository usuariosRepository) {
        this.usuariosRepo = usuariosRepo;
    }
    //Método para traernos una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Roles> roles)
    // Mapea los nombres de los roles a autoridades (SimpleGrantedAuthority)
    {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
    //Método para traernos un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Busca al usuario por su correo en la base de datos
        Usuarios usuarios = usuariosRepo.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Retorna un UserDetails (detalles del usuario) usando la información obtenida de la base de datos
        return new User(usuarios.getCorreo(), usuarios.getPassword(), mapToAuthorities(usuarios.getRoles()));
    }
}
