package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.dto.request.EcopointSuggestionDto;
import com.example.ecomapbackend.service.EcopointSuggestionService;
import com.example.ecomapbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EcopointSuggestionServiceImpl implements EcopointSuggestionService {

    private static final String EMAIL_SUBJECT = "Предложение c добавлением новой эко точки!";
    @Value("${mail.from}")
    private String fromMail;

    @Value("${mail.to}")
    private String toMail;
    private final EmailService emailService;

    @Override
    public void suggest(EcopointSuggestionDto dto) {
        emailService.sendSimpleEmail(fromMail, toMail, EMAIL_SUBJECT, this.createMassageTemplate(dto));
    }


    private String createMassageTemplate(EcopointSuggestionDto dto) {
        var isOrganisation = Boolean.TRUE.equals(dto.getIsOrganization()) ? "Да" : "Нет";
        return String.format("""
                        Имя: %s
                        Название эко точки: %s
                        Адресс: %s
                        Описание:
                        %s
                        Почта: %s
                        Сайт: %s
                        Запрос от организации: %s""",
                dto.getFirstName(),
                dto.getEcopointName(),
                dto.getAddress(),
                dto.getDescription(),
                dto.getEmail(),
                dto.getSite(),
                isOrganisation);
    }
}
