package com.example.market.service;

import com.example.market.entity.ItemEntity;
import com.example.market.exception.ItemNotFoundException;
import com.example.market.exception.ValidationFailedException;
import com.example.market.model.ItemModel;
import com.example.market.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ItemService {
    @Autowired
    private ItemRepo itemRepo;
    public void addItem(Request request) throws ValidationFailedException {
        try {
            ArrayList<ItemEntity> items = request.getItems();
            String date = request.getUpdateDate();
            Date date_ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
            checkItems(items);
            for (int i = 0; i < items.size(); i++) {
                ItemEntity item = items.get(i);
                item.setDate(date_);
                itemRepo.save(item);
                newDate(items.get(i), date_);
            }
        } catch (ParseException e) {
            throw new ValidationFailedException();
        } catch (Exception e) {
            throw new ValidationFailedException();
        }
    }
    private void newDate(ItemEntity item, Date date){
        if(item.checkParent()) {
            ItemEntity parent_item = itemRepo.findById(item.getParentId()).get();
            parent_item.setDate(date);
            itemRepo.save(parent_item);
            newDate(parent_item, date);
        }
    }
    private void checkItems(ArrayList<ItemEntity> items) throws ValidationFailedException{
        Set<String> uniqueID = new HashSet<>();
        for(int i = 0; i < items.size(); i++){
            ItemEntity item = items.get(i);

            if(item.checkParent()) {
                String parent_item = item.getParentId();
                if(itemRepo.existsById(parent_item)) {
                    if (!itemRepo.findById(parent_item).get().getType().equals("CATEGORY"))
                        throw new ValidationFailedException();
                }
                else{
                    for(int j = 0; j < items.size(); j++)
                        if(item.getId().equals(parent_item))
                            if(!item.getType().equals("CATEGORY"))
                                throw new ValidationFailedException();
                }
            }

            if (!item.checkName())
                throw new ValidationFailedException();

            if(!item.getType().equals("CATEGORY")) {
                if (!item.checkPrice())
                    throw new ValidationFailedException();
                if(item.getPrice() < 0)
                    throw new ValidationFailedException();
            }
            else{
                if(item.checkPrice())
                    throw new ValidationFailedException();
            }

            if(uniqueID.contains(item.getId()))
                throw new ValidationFailedException();
            else
                uniqueID.add(item.getId());
        }
    }
    public void deleteItem(String id) throws ItemNotFoundException{
        if(!itemRepo.existsById(id))
            throw new ItemNotFoundException();
        itemRepo.deleteById(id);

        ArrayList<ItemEntity> items = new ArrayList<>();
        itemRepo.findAll().forEach(items::add);
        LinkedList<String> queue = new LinkedList<>();
        queue.addLast(id);
        while (!queue.isEmpty()){
            id = queue.pollFirst();
            for(int i = 0; i < items.size(); i++) {
                ItemEntity item = items.get(i);
                if (item.checkParent()){
                    if (item.getParentId().equals(id)) {
                        String id_ = item.getId();
                        queue.addLast(id_);
                        itemRepo.deleteById(id_);
                    }
                }
            }
        }
    }
    public ItemModel getItem(String id) throws ItemNotFoundException{
        if(!itemRepo.existsById(id))
            throw new ItemNotFoundException();

        ItemEntity item = itemRepo.findById(id).get();
        ArrayList<ItemEntity> items = new ArrayList<>();
        itemRepo.findAll().forEach(items::add);

        return ItemModel.toModel(item, items);
    }
}
