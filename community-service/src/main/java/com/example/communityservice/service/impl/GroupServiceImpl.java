package com.example.communityservice.service.impl;

import com.example.communityservice.entity.Group;
import com.example.communityservice.exception.InvalidRequestException;
import com.example.communityservice.repository.GroupRepo;
import com.example.communityservice.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private GroupRepo groupRepo;
    @Override
    public void createGroup(Group group) {
        groupRepo.save(group);
        return;
    }

    @Override
    public void updateGroup(Group group, long id) {
          Optional<Group> group1=groupRepo.findById(id);
          if(group1.isEmpty())
          {
              throw new InvalidRequestException("Invalid Group Id");
          }
          group1.get().setGroupName(group.getGroupName());
          groupRepo.save(group1.get());
          return;
    }

    @Override
    public void deleteGroup(long id) {
        Optional<Group> group1=groupRepo.findById(id);
        if(group1.isEmpty())
        {
            throw new InvalidRequestException("Invalid Group Id");
        }
        groupRepo.deleteById(id);
    }

    @Override
    public List<Group> seeAllGroups() {
        return groupRepo.findAll();
    }
}
