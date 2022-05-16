package karpenko.diploma.chatapp;

public class User {

    private String name;
    private String email;
    private String id;
    private int userAvatarMockUpResource;

    public User(){}

    public User(String name, String email, String id, int userAvatarMockUpResource) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.userAvatarMockUpResource = userAvatarMockUpResource;
    }

    public String getName() {
        return name;
    }

    public int getUserAvatarMockUpResource() {
        return userAvatarMockUpResource;
    }

    public void setUserAvatarMockUpResource(int userAvatarMockUpResource) {
        this.userAvatarMockUpResource = userAvatarMockUpResource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
