package toollibrary.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import toollibrary.component.MemberComponent;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    @Autowired
    private MemberComponent memberComponent;

    @PostMapping("/{username}/{password}")
    public ResponseEntity<String> createMember(
            @PathVariable String username, @PathVariable String password) {
        String id = memberComponent.addUser(username, password);
        return id == null ?
            ResponseEntity.status(409).build() :
            ResponseEntity.ok(id);
    }

    @GetMapping("/{username}/{password}")
    public ResponseEntity<String> signin(
            @PathVariable String username, @PathVariable String password) {
        String id = memberComponent.signin(username, password);
        return id == null ?
            ResponseEntity.status(401).build() :
            ResponseEntity.ok(id);
    }
}