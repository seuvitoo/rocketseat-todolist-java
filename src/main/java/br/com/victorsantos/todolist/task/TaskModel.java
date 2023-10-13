package br.com.victorsantos.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String titulo;

    private String descricao;

    private String status;

    private String prioridade;

    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitulo(String titulo) throws Exception {
        if (titulo.length() > 50) {
            throw new Exception("Titulo não pode conter mais de 50 caracteres");
        }
        this.titulo = titulo;
    }

    private LocalDateTime updatedAt;

}
