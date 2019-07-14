package com.shuomarket.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * created by Shawn.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
