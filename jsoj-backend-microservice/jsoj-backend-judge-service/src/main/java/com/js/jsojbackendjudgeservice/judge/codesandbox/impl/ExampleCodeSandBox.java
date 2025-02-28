package com.js.jsojbackendjudgeservice.judge.codesandbox.impl;


import com.js.jsojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.js.jsojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.js.jsojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.js.jsojbackendmodel.codesandbox.JudgeInfo;
import com.js.jsojbackendmodel.enums.JudgeInfoMessageEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 示例代码沙箱
 *
 * @author JianShang
 * @version 1.0.0
 * @time 2024-10-24 11:16:35
 */
@Slf4j
public class ExampleCodeSandBox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(JudgeInfoMessageEnum.ACCEPTED.getText());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
