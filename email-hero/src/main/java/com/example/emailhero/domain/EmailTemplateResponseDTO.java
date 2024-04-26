package com.example.emailhero.domain;

public class EmailTemplateResponseDTO {
    private String template;

    public EmailTemplateResponseDTO() {}

    public EmailTemplateResponseDTO(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
