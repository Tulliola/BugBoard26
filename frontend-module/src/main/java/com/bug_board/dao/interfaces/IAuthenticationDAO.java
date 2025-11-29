package com.bug_board.dao.interfaces;

import com.bug_board.dto.UserAuthenticationDTO;

public interface IAuthenticationDAO {
    public abstract String authenticate(UserAuthenticationDTO dto);
}
