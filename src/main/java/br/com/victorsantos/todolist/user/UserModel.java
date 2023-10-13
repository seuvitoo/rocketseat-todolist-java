package br.com.victorsantos.todolist.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @Column(name = "usuario", unique = true)
    private String username;

    @Column(name = "senha")
    private String password;

    @Column(name = "primeiro_nome")
    private String nome;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String nome) {
        this.nome = nome;
    }
}
