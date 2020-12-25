/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class User.
 */
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8280727779861053395L;

	/** The id. */
	@JsonProperty
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The name. */
	@JsonProperty
	@Column(nullable = false)
	private String name;

	/** The lastname. */
	@JsonProperty(required = false)
	@Column(nullable = true)
	private String lastname;

	/** The username. */
	@JsonProperty
	@Column(nullable = false, unique = true)
	private String username;

	/** The email. */
	@JsonProperty
	@Column(nullable = false, unique = true)
	private String email;

	/** The password. */
	@JsonProperty
	@Column(nullable = false)
	private String password;

	/** The role. */
	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Role role;

	/**
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	@JsonGetter("role")
	public String getRoleName() {
		return role.getName();
	}

}
