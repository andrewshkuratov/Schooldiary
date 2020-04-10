package it_school.sumdu.edu.schooldiary;

public class DiaryProvider {

    private String subject;
    private String task;

    public DiaryProvider(String subject, String task){
        this.setSubject(subject);
        this.setTask(task);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
