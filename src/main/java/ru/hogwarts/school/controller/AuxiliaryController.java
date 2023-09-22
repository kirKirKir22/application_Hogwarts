package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/auxiliary")
public class AuxiliaryController {

    @GetMapping
    public Integer streamExperiment() {
        long start = System.currentTimeMillis();

        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel() // не ускоряет
                .reduce(0, (a, b) -> a + b);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start);

        return sum;

    }

}
