package com.indresh.campuslostfound.service;

import com.indresh.campuslostfound.exception.ResourceNotFoundException;
import com.indresh.campuslostfound.model.Item;
import com.indresh.campuslostfound.repository.ItemRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id));
    }

    public Item updateItem(Long id, Item updatedItem) {
        Item existingItem = getItemById(id);

        existingItem.setName(updatedItem.getName());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setColor(updatedItem.getColor());
        existingItem.setLocation(updatedItem.getLocation());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setStatus(updatedItem.getStatus());

        return itemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        Item existingItem = getItemById(id);
        itemRepository.delete(existingItem);
    }

    public List<Item> searchItems(
            String status,
            String name,
            String category,
            String location) {

        Specification<Item> spec = (root, query, cb) -> cb.conjunction();

        if (status != null && !status.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.upper(root.get("status")), status.toUpperCase()));
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("name")),
                            "%" + name.toLowerCase() + "%"
                    ));
        }

        if (category != null && !category.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(
                            cb.lower(root.get("category")),
                            category.toLowerCase()
                    ));
        }

        if (location != null && !location.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("location")),
                            "%" + location.toLowerCase() + "%"
                    ));
        }

        return itemRepository.findAll(spec);
    }
}