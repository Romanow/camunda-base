package ru.romanow.camunda

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import ru.romanow.camunda.config.DatabaseTestConfiguration

@SpringBootTest
@Import(DatabaseTestConfiguration::class)
internal class CamundaApplicationTest {

    @Test
    fun runApp() {
    }
}
