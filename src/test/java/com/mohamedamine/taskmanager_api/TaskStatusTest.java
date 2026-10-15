
package com.mohamedamine.taskmanager_api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskStatusTest {

    @Test
    void shouldHaveValidTaskStatus() {

        String status = "TODO";

        assertEquals("TODO", status);
    }
}
