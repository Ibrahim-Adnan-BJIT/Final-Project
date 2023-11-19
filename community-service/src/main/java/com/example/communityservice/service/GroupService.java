package com.example.communityservice.service;

import com.example.communityservice.entity.Group;

import java.util.List;

public interface GroupService {

    void createGroup(Group group);

    void updateGroup(Group group,long id);

    void deleteGroup(long id);

    List<Group> seeAllGroups();
}
