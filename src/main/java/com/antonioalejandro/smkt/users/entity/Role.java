/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Role.
 */
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5901144055586256656L;

	/** The id. */
	@JsonProperty(value = "id", required = true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The name. */
	@JsonProperty(value = "name", required = true)
	@Column(nullable = false, unique = true)
	private String name;

	/** The scopes. */
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Scope.class)
	private Set<Scope> scopes;

}
