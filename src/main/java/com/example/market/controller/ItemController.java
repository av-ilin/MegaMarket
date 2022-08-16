package com.example.market.controller;

import com.example.market.exception.ItemNotFoundException;
import com.example.market.exception.ValidationFailedException;
import com.example.market.model.ItemModel;
import com.example.market.service.ItemService;
import com.example.market.service.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/imports")
    public ResponseEntity addItem(@RequestBody Request request){
        try {
            itemService.addItem(request);
            return ResponseEntity.ok(null);
        }catch (ValidationFailedException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Validation Failed");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteItem(@PathVariable String id){
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok(null);
        } catch (ItemNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Validation Failed");
        }
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity getItem(@PathVariable String id){
        try {
            return ResponseEntity.ok(itemService.getItem(id));
        } catch (ItemNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Validation Failed");
        }
    }
}
