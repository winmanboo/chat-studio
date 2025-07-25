package com.winmanboo.chatstudio.service;

import com.winmanboo.chatstudio.domain.RegisterDTO;

public interface AuthService {

  void register(RegisterDTO registerDTO);

  void generateMailCaptcha(String email);
}
