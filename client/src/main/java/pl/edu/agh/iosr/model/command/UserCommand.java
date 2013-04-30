package pl.edu.agh.iosr.model.command;

import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

import java.util.List;
import java.util.Map;

public class UserCommand {
    private Long id;

    private String login;

    private String name;

    private String surname;

    private String password;

    private String email;

    private Boolean enabled;

    private String tenantName;

    private String roleName;

    private Map<String,String> tenantMap;

    private Map<String,String> roleMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map<String, String> getTenantMap() {
        return tenantMap;
    }

    public void setTenantMap(Map<String, String> tenantMap) {
        this.tenantMap = tenantMap;
    }

    public Map<String, String> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, String> roleMap) {
        this.roleMap = roleMap;
    }

    public void setUser(UserEntity userEntity){
        this.name = userEntity.getName();
        this.id = userEntity.getId();
        this.surname = userEntity.getSurname();
        this.login = userEntity.getLogin();
        this.password = userEntity.getPassword();
        this.enabled = userEntity.isEnabled();
        this.email = userEntity.getEmail();
    }

    public UserEntity getUser(){
        UserEntity userEntity = new UserEntity();

        userEntity.setName(this.getName());
        userEntity.setId(this.getId());
        userEntity.setSurname(this.getSurname());
        userEntity.setLogin(this.getLogin());
        userEntity.setPassword(this.getPassword());
        userEntity.setEnabled(this.isEnabled());
        userEntity.setEmail(this.getEmail());

        return userEntity;
    }

    @Override
    public String toString(){
        return "login="+login+","
                + "password="+password+","
                + "name="+name+","
                + "surname="+surname+","
                + "email="+email+","
                + "enabled="+enabled+","
                + "tenantName="+tenantName+","
                + "roleName="+roleName+",";
    }
}
