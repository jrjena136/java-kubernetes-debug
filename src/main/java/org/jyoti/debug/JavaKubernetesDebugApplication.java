package org.jyoti.debug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sample")
@SpringBootApplication
public class JavaKubernetesDebugApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaKubernetesDebugApplication.class, args);
    }

    @GetMapping("/hello")
    public String greet(@RequestParam("name") String name) {
        return "Welcome " + name + "!!!";
    }
}
