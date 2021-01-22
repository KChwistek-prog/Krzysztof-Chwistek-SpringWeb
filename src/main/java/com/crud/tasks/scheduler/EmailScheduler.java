package com.crud.tasks.scheduler;

import com.crud.tasks.TaskRepository;
import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.services.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;
    private final TaskRepository taskRepository;

    public static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail(){

        long size = taskRepository.count();
        String task;

        if(size>1){
            task = " tasks.";
        } else {
            task = " task.";
        }

        simpleEmailService.send(
                Mail.builder()
                        .mailTo(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .message("Currently in database you got: " + size + task)
                        .toCc(null)
                .build()
        );
    }
}