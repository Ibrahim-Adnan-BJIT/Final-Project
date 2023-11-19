package com.example.communityservice.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;
    private String groupName;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Post>posts=new ArrayList<>();
}
