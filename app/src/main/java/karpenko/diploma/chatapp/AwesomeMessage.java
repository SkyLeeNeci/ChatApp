package karpenko.diploma.chatapp;

public class AwesomeMessage {
    String text;
    String name;
    String imageUri;

    public AwesomeMessage(){

    }

    public AwesomeMessage(String text, String name, String imageUri) {
        this.text = text;
        this.name = name;
        this.imageUri = imageUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
