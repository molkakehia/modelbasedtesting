/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.Testcase;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface TestCaseService {
    void createTestCase(Testcase var);
        
    List<Testcase> findAll();

    Testcase findByid(Integer id);

    void updateTestCase(Testcase var);

    void deleteTestCase(Testcase var);
}
