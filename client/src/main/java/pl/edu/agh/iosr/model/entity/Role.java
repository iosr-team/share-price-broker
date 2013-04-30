package pl.edu.agh.iosr.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPERUSER = "ROLE_SUPERUSER";
    public static final String ROLE_USER = "ROLE_USER";

    @Id
    @GeneratedValue
    private Long id;

    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}

    public boolean isAdmin(){
        return ROLE_ADMIN.equals(this.name);
    }

    public boolean isSuperuser(){
        return ROLE_SUPERUSER.equals((this.name));
    }
}
