package net.engineeringdigest.journalApp.Entity;

public class JournalEntry {
    private Long id;
    private String name;
    private String content;

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getname(){
        return name;
    }

    public void setname(String name){
        this.name = name;
    }

    public String getcontent(){
        return content;
    }

    public void setcontent(String content){
        this.content = content;
    }

}
