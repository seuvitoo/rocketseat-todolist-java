package br.com.victorsantos.todolist.task;

import br.com.victorsantos.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("userId");
        taskModel.setUserId((UUID) idUser);

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getCreatedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Data de criação não pode ser maior que a data atual");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // deve retornar uma lista de tarefas para um usuário
    @GetMapping("/")
    public ResponseEntity list(HttpServletRequest request) {
        var idUser = request.getAttribute("userId");
        List<TaskModel> tasks = this.taskRepository.findByUserId(idUser);
        return ResponseEntity.ok(tasks);
    }

    // deve atualizar uma tarefa de um usuário
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        var idUser = request.getAttribute("userId");

        if (!task.getUserId().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario nao tem permissao para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);

    }

    // deve deletar uma tarefa de um usuário
//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@PathVariable("id") UUID id, HttpServletRequest request) {
//       // verificar se a tarefa existe
//        var task = this.taskRepository.findById(id).orElse(null);
//        if (task == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        this.taskRepository.deleteById(id);
//        return ResponseEntity.ok().build();
//    }

}