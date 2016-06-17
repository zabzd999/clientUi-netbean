/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author ljs
 */
public class QueueUtils {

    public static LinkedBlockingDeque<JtableMessageDto> sendTable1 = new LinkedBlockingDeque<JtableMessageDto>();
    public static LinkedBlockingDeque<JtableMessageDto> sendSignTable = new LinkedBlockingDeque<JtableMessageDto>();
//    public static LinkedBlockingDeque<JtableMessageDto> sendErrorTable2 = new LinkedBlockingDeque<JtableMessageDto>();
    public static LinkedBlockingDeque<JtableMessageDto> receCusTable3 = new LinkedBlockingDeque<JtableMessageDto>();
    public static LinkedBlockingDeque<JtableMessageDto> receCiqTable4 = new LinkedBlockingDeque<JtableMessageDto>();
    
}
