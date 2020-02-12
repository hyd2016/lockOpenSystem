package com.learn.lockopensystem.model

object ErrorCode {
    public fun getErrorInfo(code: Int): String {
        val s = when (code) {
            0 -> return "请求成功"
            101 -> return "手机号无效"
            102 -> return "密码无效"
            103 -> return "用户被禁用"
            104 -> return "用户名或密码错误"
            105 -> return "令牌无效"
            106 -> return "短信验证码无效"
            107 -> return "昵称无效"
            108 -> return "该手机号已被注册"
            109 -> return "该手机号未被注册"
            110 -> return "锁编号无效"
            111 -> return "锁密码无效"
            112 -> return "锁已经被绑定"
            113 -> return "锁不在该用户名下"
            114 -> return "锁名称无效"
            else -> "短信平台错误"
        }
        return s
    }
}