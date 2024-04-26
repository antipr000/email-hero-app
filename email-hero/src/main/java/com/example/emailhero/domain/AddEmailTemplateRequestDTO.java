package com.example.emailhero.domain;

public class AddEmailTemplateRequestDTO {
    private String template;

    public AddEmailTemplateRequestDTO() {}

    public AddEmailTemplateRequestDTO(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
