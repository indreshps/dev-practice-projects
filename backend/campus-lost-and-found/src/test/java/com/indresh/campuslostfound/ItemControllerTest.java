package com.indresh.campuslostfound;

import com.indresh.campuslostfound.exception.GlobalExceptionHandler;
import com.indresh.campuslostfound.exception.ResourceNotFoundException;
import com.indresh.campuslostfound.service.ItemService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

@WebMvcTest
@Import(GlobalExceptionHandler.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    void getAllItemsShouldReturnOk() throws Exception {

        when(itemService.searchItems(null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }

    @Test
    void createItemWithInvalidNameShouldReturnBadRequest()
            throws Exception {

        String invalidItemJson = """
                {
                    "name": "",
                    "category": "Accessories",
                    "color": "Black",
                    "location": "College Library",
                    "description": "Test item",
                    "status": "FOUND"
                }
                """;

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidItemJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Validation Failed"));
    }

    @Test
    void getMissingItemShouldReturnNotFound() throws Exception {

        when(itemService.getItemById(999L))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Item not found with id: 999"
                        )
                );

        mockMvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}