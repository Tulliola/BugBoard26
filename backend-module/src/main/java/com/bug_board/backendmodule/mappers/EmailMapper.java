package com.bug_board.backendmodule.mappers;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.dto.email.EmailToSendDTOFactory;
import com.bug_board.dto.email.IEmailToSendDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public static IEmailToSendDTO mapToWelcomingMail(User addressee, String clearPassword) {
        IEmailToSendDTO emailToSendDTO = EmailToSendDTOFactory.getInstance().getEmailDTO();
        emailToSendDTO.setUsername(addressee.getUsername());
        emailToSendDTO.setAddressee(addressee.getEmail());
        emailToSendDTO.setPassword(clearPassword);

        return emailToSendDTO;
    }
}
