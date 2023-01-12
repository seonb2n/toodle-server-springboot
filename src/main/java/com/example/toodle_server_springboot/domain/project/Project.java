package com.example.toodle_server_springboot.domain.project;

import com.example.toodle_server_springboot.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;


}
