package artmcoder;

import java.util.List;

public class Post {
    private String title;
    private String detailsLink;
    private String programs;
    private String programsDetailsLink;
    private String text_content;
    private String name_programs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public String getPrograms() {
        return programs;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public String getProgramsDetailsLink() {
        return programsDetailsLink;
    }

    public String getName_programs() {
        return name_programs;
    }

    public void setName_programs(String name_programs) {
        this.name_programs = name_programs;
    }

    public void setProgramsDetailsLink(String programsDetailsLink) {
        this.programsDetailsLink = programsDetailsLink;
    }

    public String getText_content() {
        return text_content;
    }

    @Override
    public String toString() {
        return "Post{" +
                ", detailsLink='" + detailsLink + '\'' +
                ", name_app='" + name_programs + '\'' +
                ", programsDetailsLink='" + programsDetailsLink + '\'' +
                ", text_content='" + text_content + '\'' +
                '}';
    }
    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

}