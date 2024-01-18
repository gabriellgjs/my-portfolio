package com.myportfolio.developer.models;

import com.myportfolio.skill.models.Skill;
import com.myportfolio.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "developers")
@Table(name = "developers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Developer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String name;
  private String email;

  @Column(name = "github_url")
  private String githubURL;
  @Column(name = "linkedin_url")
  private String linkedinURL;
  @Column(name = "about_us")
  private String aboutUs;
  @Column(name = "whatsapp_phone")
  private String whatsappPhone;
  private String curriculum = "";

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @ManyToMany
  @JoinTable(name = "developers_skills",
    joinColumns = @JoinColumn(name ="developer_id"),
  inverseJoinColumns = @JoinColumn(name = "skill_id")
  )
  private Set<Skill> skills;
}
